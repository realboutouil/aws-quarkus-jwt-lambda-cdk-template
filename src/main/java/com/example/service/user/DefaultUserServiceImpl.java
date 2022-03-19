package com.example.service.user;

import com.example.model.RegisterPayload;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class DefaultUserServiceImpl implements UserService {

    @Override
    public String register(RegisterPayload payload) {
        return "SUCCESS";
    }
}