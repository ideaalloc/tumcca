package com.tumcca.api.resources.admin;

import com.tumcca.api.admin.Error401View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-20
 */
@Path("/error")
@Produces(MediaType.TEXT_HTML)
public class ErrorResource {

    @GET
    @Path("/401")
    public Error401View error401() {
        return new Error401View();
    }
}
