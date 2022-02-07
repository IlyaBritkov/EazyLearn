package com.eazylearn.mapper;

import com.eazylearn.dto.request.user.UserRegistryRequestDTO;
import com.eazylearn.dto.request.user.UserUpdateRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.Role;
import com.eazylearn.entity.User;
import com.eazylearn.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE)
public abstract class UserMapper {

    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToUserRoles")
    public abstract UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    public abstract User toEntity(UserRegistryRequestDTO userDto);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntity(UserUpdateRequestDTO userDTO, @MappingTarget User user);

    // TODO maybe make it protected
    // ? should it be static?
    @Named("rolesToUserRoles")
    public static List<UserRole> rolesToUserRoles(List<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(toList());
    }
}
