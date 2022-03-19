package com.example.service.auth;

import com.example.dto.UserInfo;
import com.example.entities.UserEntity;
import com.example.exception.AuthenticationFailedException;
import com.example.mapper.UserMapper;
import com.example.model.LoginPayload;
import com.example.model.LoginResponse;
import com.example.security.TokenProvider;
import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class DefaultAuthServiceImpl implements AuthService {

    private final TokenProvider provider;
    private final UserMapper mapper;
    private final JsonWebToken jwt;

    @Override
    public LoginResponse authenticate(final LoginPayload payload) {
        return UserEntity.findByUsername(payload.getUsername())
                .filter(entity -> BcryptUtil.matches(payload.getPassword(), entity.getPassword()))
                .map(provider::getUserToken)
                .orElseThrow(AuthenticationFailedException::new);
    }

    @Override
    public UserInfo userinfo() {
        Optional<UserEntity> user = UserEntity.findByIdOptional(UUID.fromString(jwt.getSubject()));
        return user.map(mapper::mapToDomain)
                .orElseThrow(AuthenticationFailedException::new);
    }

    @Override
    public String user_roles() {
        return String.format("User %s has the following roles: %s", jwt.getName(), String.join(", ", jwt.getGroups()));
    }
}