package com.tumcca.api.resources;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.tumcca.api.db.ShopUserSessionsDAO;
import com.tumcca.api.db.ShopUsersDAO;
import com.tumcca.api.model.AccountInfo;
import com.tumcca.api.model.SignIn;
import com.tumcca.api.model.UserInfo;
import com.tumcca.api.model.UserSecret;
import com.tumcca.api.util.PasswordHash;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
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
 * @since 2015-03-12
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);

    final DBI dbi;

    public AuthResource(DBI dbi) {
        this.dbi = dbi;
    }

    @POST
    @Path("/sign-in")
    public Response signIn(@Valid SignIn signIn) {
        String username = signIn.getUsername();
        LOGGER.info("User: {} tries to sign in", username);

        try (final ShopUsersDAO shopUsersDAO = dbi.open(ShopUsersDAO.class);
             final ShopUserSessionsDAO shopUserSessionsDAO = dbi.open(ShopUserSessionsDAO.class)) {
            final UserInfo userInfo = shopUsersDAO.findByUsername(Optional.of(username));
            if (userInfo == null) {
                return Response.status(403).entity("No such user").build();
            }

            UserSecret userSecret = shopUsersDAO.findPasswordByUsername(Optional.of(username));
            if (!PasswordHash.check(signIn.getPassword(), userSecret.getPassword())) {
                return Response.status(403).entity("User or password incorrect").build();
            }

            UserInfo uinfo;
            String sessionId = UUID.randomUUID().toString();
            if (StringUtils.isNotEmpty(userInfo.getCredentials())) {
                shopUserSessionsDAO.delete(Optional.of(userInfo.getCredentials()));
            }
            shopUserSessionsDAO.insert(Optional.of(sessionId), Optional.of(userSecret.getUserId()), Optional.of(true));
            uinfo = new UserInfo(sessionId, userInfo.getCredit(), userInfo.getShopId(), userInfo.getShopName());

            return Response.ok(uinfo).build();
        }
    }

    @POST
    @Path("/sign-out")
    public Response signOut(@Auth String principal) {
        LOGGER.info("Principal {} is going to sign out....", principal);
        try (final ShopUserSessionsDAO shopUserSessionsDAO = dbi.open(ShopUserSessionsDAO.class)) {
            shopUserSessionsDAO.update(Optional.of(Long.parseLong(principal)), Optional.of(false));
        }
        return Response.ok(1).build();
    }

    @POST
    @Path("/sign-up")
    public Response signUp(@Valid AccountInfo accountInfo) {
        LOGGER.info("Signing up....");
        try (final ShopUsersDAO shopUsersDAO = dbi.open(ShopUsersDAO.class)) {
            Long userId = shopUsersDAO.insert(Optional.of(accountInfo.getShopId()), Optional.of(accountInfo.getUsername()),
                    Optional.of(PasswordHash.hash(accountInfo.getPassword())),
                    Optional.of(accountInfo.getCredit()));
            return Response.ok(ImmutableMap.of("userId", userId)).build();
        }
    }

}
