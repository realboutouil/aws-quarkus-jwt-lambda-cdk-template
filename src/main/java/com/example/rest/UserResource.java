package com.example.rest;

import com.example.model.RegisterPayload;
import com.example.service.user.UserService;
import lombok.AllArgsConstructor;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor = @__(@Inject))
public class UserResource {

    private final UserService service;

    @POST
    @Path("/")
    @PermitAll
    public Response register(@Valid RegisterPayload payload) {
        return Response.ok(service.register(payload)).build();
    }
}
