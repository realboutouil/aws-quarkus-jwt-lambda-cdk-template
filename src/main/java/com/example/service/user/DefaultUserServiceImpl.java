package com.example.service.user;

import com.example.entities.ERole;
import com.example.entities.UserEntity;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.mapper.UserMapper;
import com.example.model.RegisterPayload;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Slf4j
@Transactional
@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class DefaultUserServiceImpl implements UserService {

    private final UserMapper mapper;

    @Override
    public String register(RegisterPayload payload) {
        final var username = payload.getUsername().toLowerCase().trim();
        if (UserEntity.existsByUsername(username))
            throw new UsernameAlreadyExistsException();

        var user = UserEntity.builder()
                .username(username)
                .password(payload.getPassword())
                .role(ERole.USER)
                .build();

        mapper.mapInputToEntity(payload, user);
        // Persist User
        user.persist();
        return "SUCCESS";
    }
}