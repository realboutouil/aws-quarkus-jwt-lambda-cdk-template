package com.example.entities;

import com.example.entities.audit.AuditableEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_roles")
public class UserRole extends AuditableEntity {

    @Enumerated(EnumType.STRING)
    private ERole role;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

    public static UserRole findByRole(ERole role) {
        return find("role", role).firstResult();
    }
}
