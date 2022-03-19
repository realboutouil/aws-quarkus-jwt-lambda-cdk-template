package com.example.rest;

import com.example.model.LoginPayload;
import com.example.service.auth.AuthService;
import io.quarkus.security.Authenticated;
import lombok.AllArgsConstructor;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor = @__(@Inject))
public class AuthResource {

    private final AuthService service;

    @POST
    @Path("/token")
    @PermitAll
    public Response login(@Valid LoginPayload payload) {
        return Response.ok(service.authenticate(payload)).build();
    }

    @GET
    @Path("/userinfo")
    @Authenticated
    public Response userinfo() {
        return Response.ok(service.userinfo()).build();
    }

    @GET
    @Path("/roles")
    @Authenticated
    public Response checkRolesAllowed() {
        return Response.ok(service.user_roles()).build();
    }
}
