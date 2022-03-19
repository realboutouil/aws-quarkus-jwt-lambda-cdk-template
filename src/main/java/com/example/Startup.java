package com.example;

import com.example.entities.ERole;
import com.example.entities.UserEntity;
import com.example.entities.UserRole;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.stream.Stream;

@Slf4j
@Singleton
public class Startup {
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        UserEntity.deleteAll();
        UserRole.deleteAll();
        Stream.of(ERole.values())
                .map(role -> UserRole.builder().role(role).build())
                .forEach(UserEntity::persist);
        UserEntity.builder()
                .username("admin@example.com")
                .password("admin")
                .first_name("admin")
                .last_name("admin")
                .password("admin")
                .role(ERole.ADMIN)
                .build()
                .persist();
        UserEntity.builder()
                .username("user@example.com")
                .password("user")
                .first_name("user")
                .last_name("user")
                .password("user")
                .role(ERole.USER)
                .build()
                .persist();
    }
}