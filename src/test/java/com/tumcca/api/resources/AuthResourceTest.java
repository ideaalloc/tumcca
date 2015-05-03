package com.tumcca.api.resources;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tumcca.api.model.SignIn;
import com.tumcca.api.model.UserInfo;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.jackson.Jackson;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-20
 */
public class AuthResourceTest {
    static final ObjectMapper JSON_MAPPER = Jackson.newObjectMapper();

    @Test
    public void signIn() throws Exception {
        JerseyClientConfiguration configuration = new JerseyClientConfiguration();
        configuration.setChunkedEncodingEnabled(false);
        postRequest(configuration);
    }

    private void postRequest(JerseyClientConfiguration configuration) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Client jersey = new JerseyClientBuilder(new MetricRegistry())
                .using(executor, JSON_MAPPER)
                .using(configuration)
                .build("jersey-test");
        jersey.property(ClientProperties.CONNECT_TIMEOUT, 5000);
        jersey.property(ClientProperties.READ_TIMEOUT, 5000);
        Response response = jersey.target("http://127.0.0.1:9080" + "/api/sign-in")
                .request()
                .buildPost(Entity.entity(new SignIn("wuwenchuan", "888888"), APPLICATION_JSON))
                .invoke();

        assertThat(response.getHeaderString(HttpHeaders.CONTENT_TYPE)).isEqualTo(APPLICATION_JSON);
        UserInfo responseMessage = response.readEntity(UserInfo.class);
        assertThat(responseMessage.getShopName())
                .isEqualTo("汉西东风雪铁龙");
        System.out.println(String.format("Credentials: %s", responseMessage.getCredentials()));

        executor.shutdown();
        jersey.close();
    }
}
