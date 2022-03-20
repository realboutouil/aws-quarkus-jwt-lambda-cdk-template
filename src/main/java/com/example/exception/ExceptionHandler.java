package com.example.exception;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception exception) {
        if (exception instanceof AuthenticationFailedException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Authentication Failed")
                    .build();
        }
        if (exception instanceof UsernameAlreadyExistsException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Username Already Exists")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(exception.getMessage())
                .build();
    }

}