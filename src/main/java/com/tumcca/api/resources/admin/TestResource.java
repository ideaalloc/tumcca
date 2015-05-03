package com.tumcca.api.resources.admin;

import com.tumcca.api.admin.ChatView;

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
 * @since 2015-03-24
 */
@Path("/test")
@Produces(MediaType.TEXT_HTML)
public class TestResource {
    @GET
    @Path("/chat")
    public ChatView chat() {
        return new ChatView();
    }
}
