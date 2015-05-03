package com.tumcca.api.resources;

import com.tumcca.api.db.ShopsDAO;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-22
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class CommonResource {
    static final Logger LOGGER = LoggerFactory.getLogger(CommonResource.class);

    final DBI dbi;

    public CommonResource(DBI dbi) {
        this.dbi = dbi;
    }

    @GET
    @Path("/shops")
    public Response getShops(@Auth String principal) {
        LOGGER.info("Principal {} is getting shops....", principal);
        try (final ShopsDAO shopsDAO = dbi.open(ShopsDAO.class)) {
            return Response.ok(shopsDAO.findShops()).build();
        }
    }
}
