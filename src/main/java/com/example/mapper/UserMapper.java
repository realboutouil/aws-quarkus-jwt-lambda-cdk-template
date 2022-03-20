package com.example.mapper;

import com.example.dto.UserInfo;
import com.example.entities.ERole;
import com.example.entities.UserEntity;
import com.example.entities.UserRole;
import com.example.model.RegisterPayload;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        config = QuarkusMappingConfig.class,
        uses = {CommonMapper.class}
)
public interface UserMapper extends GenericMapper<UserInfo, UserEntity> {

    @Override
    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "roles", target = "roles", qualifiedByName = "mapUserRoles"),
            @Mapping(source = ".", target = "full_name", qualifiedByName = "mapFullName"),
    })
    UserInfo mapToDomain(UserEntity entity);

    @Override
    List<UserInfo> mapToDomains(List<UserEntity> entities);


    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "username", ignore = true),
    })
    void mapInputToEntity(RegisterPayload payload, @MappingTarget UserEntity entity);

    @Named("mapFullName")
    default String mapFullName(UserEntity entity) {
        return entity.getFirstName() + " " + entity.getLastName();
    }

    @Named("mapUserRoles")
    default List<ERole> mapUserRoles(List<UserRole> roles) {
        return roles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }
}