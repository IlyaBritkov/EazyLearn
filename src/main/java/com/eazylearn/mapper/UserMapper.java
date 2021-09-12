package com.eazylearn.mapper;

import com.eazylearn.dto.request.UserRegistryRequestDTO;
import com.eazylearn.dto.request.UserUpdateRequestDTO;
import com.eazylearn.dto.response.UserResponseDTO;
import com.eazylearn.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "role", expression = "java( user.getRole().name() )")
    public abstract UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    public abstract User toEntity(UserRegistryRequestDTO userDto);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntity(UserUpdateRequestDTO userDTO, @MappingTarget User user);

}
