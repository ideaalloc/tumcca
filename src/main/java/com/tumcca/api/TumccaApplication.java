package com.tumcca.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tumcca.api.db.AdminSessionsDAO;
import com.tumcca.api.auth.QicaimaAuthenticator;
import com.tumcca.api.auth.admin.AdminAuthenticator;
import com.tumcca.api.model.admin.ReviewMessage;
import com.tumcca.api.resources.AuthResource;
import com.tumcca.api.resources.CarRegistrationResource;
import com.tumcca.api.resources.CommonResource;
import com.tumcca.api.resources.admin.AdminResource;
import com.tumcca.api.resources.admin.DataResource;
import com.tumcca.api.resources.admin.ErrorResource;
import com.tumcca.api.resources.admin.TestResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.ChainedAuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.atmosphere.wasync.*;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.eclipse.jetty.servlet.FilterHolder;
import org.skife.jdbi.v2.DBI;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

public class TumccaApplication extends Application<TumccaConfiguration> {
    public static void main(String[] args) throws Exception {
        new TumccaApplication().run(args);
    }

    @Override
    public String getName() {
        return "tumcca";
    }

    @Override
    public void initialize(Bootstrap<TumccaConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new AssetsBundle("/assets", "/assets", null, "assets"));
        bootstrap.addBundle(new AssetsBundle("/libs/bower_components", "/libs", null, "libs"));
        bootstrap.addBundle(new AssetsBundle("/libs/wijmo", "/wijmo", null, "wijmo"));
        bootstrap.addBundle(new AssetsBundle("/apps", "/apps", null, "apps"));
        bootstrap.addBundle(new ViewBundle<TumccaConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(TumccaConfiguration configuration) {
                return configuration.getViewRendererConfiguration();
            }
        });
        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(TumccaConfiguration configuration, Environment environment) {
        DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();

        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());
        final String uploadPath = configuration.getUploadPath();
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        final DBIFactory factory = new DBIFactory();
        DBI dbi = factory.build(environment, dataSourceFactory, "mysql");

        ChainedAuthFactory chainedFactory = new ChainedAuthFactory(
                new OAuthFactory<>(new QicaimaAuthenticator(dbi),
                        "realm",
                        String.class),
                new OAuthFactory<>(new AdminAuthenticator(dbi),
                        "realm",
                        String.class));
        environment.jersey().register(AuthFactory.binder(chainedFactory));

        environment.getApplicationContext().addFilter(new FilterHolder(new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
            }

            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
                Cookie[] cookies = httpRequest.getCookies();
                boolean successfully = false;
                if (cookies != null) {
                    Optional<String> credentials = Arrays.asList(cookies).stream().
                            filter(cookie -> "Authorization".equals(cookie.getName())).
                            map(cookie -> cookie.getValue()).findFirst();
                    if (credentials.isPresent()) {
                        try (final AdminSessionsDAO adminSessionsDAO = dbi.open(AdminSessionsDAO.class)) {
                            Boolean status = adminSessionsDAO.findStatusBySessionId(com.google.common.base.Optional.of(credentials.get()));
                            if (status != null && status) {
                                successfully = true;
                            }
                        }
                    }
                }

                boolean allowRedirect = true;
                String requestURI = httpRequest.getRequestURI();
                if (!"/admin/".contains(requestURI)) {
                    allowRedirect = false;
                }

                if (successfully) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    if (allowRedirect) {
                        ((HttpServletResponse) servletResponse).sendRedirect("/auth/sign-in");
                    } else {
                        ((HttpServletResponse) servletResponse).sendRedirect("/error/401");
                    }
                }
            }

            @Override
            public void destroy() {
            }
        }), "/admin/*", EnumSet.of(DispatcherType.REQUEST));

        AtmosphereServlet servlet = new AtmosphereServlet();
        servlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.tumcca.api.resources.websocket");
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");

        ServletRegistration.Dynamic servletHolder = environment.servlets().addServlet("review", servlet);
        servletHolder.addMapping("/ws/review/*");

        final ObjectMapper mapper = new ObjectMapper();

        final AtmosphereClient reviewClient = ClientFactory.getDefault().newClient(AtmosphereClient.class);
        final RequestBuilder reviewRequest = reviewClient.newRequestBuilder()
                .method(Request.METHOD.GET)
                .uri(configuration.getWsReviewUri())
                .trackMessageLength(true)
                .encoder(new Encoder<ReviewMessage, String>() {
                    @Override
                    public String encode(ReviewMessage data) {
                        try {
                            return mapper.writeValueAsString(data);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .decoder(new Decoder<String, ReviewMessage>() {
                    @Override
                    public ReviewMessage decode(Event type, String data) {

                        data = data.trim();

                        // Padding from Atmosphere, skip
                        if (data.length() == 0) {
                            return null;
                        }

                        if (type.equals(Event.MESSAGE)) {
                            try {
                                return mapper.readValue(data, ReviewMessage.class);
                            } catch (IOException e) {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                })
                .transport(Request.TRANSPORT.WEBSOCKET)
                .transport(Request.TRANSPORT.SSE)
                .transport(Request.TRANSPORT.LONG_POLLING);

        environment.jersey().register(new ErrorResource());
        environment.jersey().register(new AuthResource(dbi));
        environment.jersey().register(new CarRegistrationResource(uploadPath, dbi, reviewClient, reviewRequest));
        environment.jersey().register(new AdminResource(dbi));
        environment.jersey().register(new DataResource(dbi));
        environment.jersey().register(new com.tumcca.api.resources.admin.AuthResource(dbi));
        environment.jersey().register(new CommonResource(dbi));
        environment.jersey().register(new TestResource());
    }
}
