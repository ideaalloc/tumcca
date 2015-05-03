package com.tumcca.api.resources.admin;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.tumcca.api.db.AdminSessionsDAO;
import com.tumcca.api.db.AdminsDAO;
import com.tumcca.api.admin.SignInView;
import com.tumcca.api.model.admin.Admin;
import com.tumcca.api.model.admin.SignIn;
import com.tumcca.api.model.admin.SignUp;
import com.tumcca.api.util.PasswordHash;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);

    final DBI dbi;

    public AuthResource(DBI dbi) {
        this.dbi = dbi;
    }

    @GET
    @Path("/sign-in")
    @Produces(MediaType.TEXT_HTML)
    public SignInView signIn() {
        return new SignInView();
    }

    @POST
    @Path("/sign-in")
    public Response signIn(@Valid SignIn signIn) {
        LOGGER.info("Admin: {} tries to sign in", signIn.getUsername());
        try (final AdminsDAO adminsDAO = dbi.open(AdminsDAO.class);
             final AdminSessionsDAO adminSessionsDAO = dbi.open(AdminSessionsDAO.class)) {
            final Admin admin = adminsDAO.findByUsername(Optional.of(signIn.getUsername()));
            if (admin == null) {
                return Response.status(403).entity("No such user").build();
            }

            if (!PasswordHash.check(signIn.getPassword(), adminsDAO.findPasswordByUsername(Optional.of(signIn.getUsername())))) {
                return Response.status(403).entity("User or password incorrect").build();
            }

            String credentials = adminSessionsDAO.findSessionIdByUsername(Optional.of(admin.getUsername()));
            String sessionId = UUID.randomUUID().toString();
            if (StringUtils.isNotEmpty(credentials)) {
                adminSessionsDAO.delete(Optional.of(credentials));
            }
            adminSessionsDAO.insert(Optional.of(sessionId), Optional.of(admin.getUsername()), Optional.of(true), Optional.of(0L));
            return Response.ok(ImmutableMap.of("sessionId", sessionId)).build();
        }
    }

    @POST
    @Path("/sign-up")
    public Response signUp(@Valid SignUp signUp) {
        LOGGER.info("Signing up....");
        try (final AdminsDAO adminsDAO = dbi.open(AdminsDAO.class)) {
            adminsDAO.insert(Optional.of(signUp.getUsername()), Optional.of(PasswordHash.hash(signUp.getPassword())), Optional.of(true));
        } catch (Exception e) {
            Throwable t = e;
            Throwable prev = null;
            for (; t != null; t = t.getCause()) {
                prev = t;
            }
            return Response.status(500).entity(signUp.getUsername() + " failed to register as: " + prev.getMessage()).build();
        }

        return Response.ok(ImmutableMap.of("username", signUp.getUsername())).build();
    }

    @POST
    @Path("/sign-out")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signOut(@Auth String principal) {
        LOGGER.info("Principal {} is going to sign out....", principal);
        try (final AdminSessionsDAO adminSessionsDAO = dbi.open(AdminSessionsDAO.class)) {
            adminSessionsDAO.update(Optional.of(principal), Optional.of(false));
        }
        return Response.ok(1).build();
    }

    @POST
    @Path("/username")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsername(@Auth String principal) {
        return Response.ok(principal).build();
    }
}
