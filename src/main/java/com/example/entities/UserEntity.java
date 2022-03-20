package com.example.entities;

import com.example.entities.audit.AuditableEntity;
import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class UserEntity extends AuditableEntity {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    private List<UserRole> roles = new ArrayList<>();

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Builder
    public UserEntity(String username, String password, String first_name,
                      String last_name, ERole role) {
        this.username = username;
        this.password = BcryptUtil.bcryptHash(password);
        this.firstName = first_name;
        this.lastName = last_name;
        this.addRole(role);
    }

    public static Optional<UserEntity> findByUsername(String username) {
        return UserEntity.find("lower(username)", username.toLowerCase())
                .singleResultOptional();
    }

    public static boolean existsByUsername(String username) {
        return UserEntity.count("lower(username)", username.toLowerCase()) > 0;
    }

    public List<ERole> getGrantedRoles() {
        return this.roles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }

    public void addRole(ERole role) {
        this.roles.add(UserRole.findByRole(role));
    }
}