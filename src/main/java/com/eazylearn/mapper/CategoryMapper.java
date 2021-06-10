package com.eazylearn.mapper;

import com.eazylearn.dto.request.CategoryCreateRequestDTO;
import com.eazylearn.dto.request.CategoryUpdateRequestDTO;
import com.eazylearn.dto.response.CategoryResponseDTO;
import com.eazylearn.entity.Category;
import com.eazylearn.security.jwt.JwtUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        imports = {SecurityContextHolder.class, JwtUser.class})
public abstract class CategoryMapper {

    public abstract CategoryResponseDTO toResponseDTO(Category category);

    @Mapping(target = "userId",
            expression = "java( ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId() )")
    public abstract Category toEntity(CategoryCreateRequestDTO categoryDto);

    @Mapping(target = "card.id", expression = "java(card.getId())")
    @Mapping(target = "card.userId", expression = "java(card.getUserId())")
    public abstract void updateEntity(CategoryUpdateRequestDTO categoryDto, @MappingTarget Category category);
}
