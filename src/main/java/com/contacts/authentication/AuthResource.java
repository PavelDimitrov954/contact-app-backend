package com.contacts.authentication;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {
    @POST
    @Path("/login")
    @PermitAll
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        if ("user".equals(username) && "password".equals(password)) {
            String token = "your-generated-token"; // This should be a real token in a real application
            return Response.ok("{\"token\": \"" + token + "\"}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\": \"Invalid credentials\"}").build();
    }
}
