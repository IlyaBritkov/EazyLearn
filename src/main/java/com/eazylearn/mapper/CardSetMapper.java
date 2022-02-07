package com.eazylearn.mapper;

import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.dto.response.CardSetResponseDTO;
import com.eazylearn.entity.CardSet;
import com.eazylearn.security.jwt.JwtUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        imports = {SecurityContextHolder.class, JwtUser.class})
public abstract class CardSetMapper { // todo: update mapping

    public abstract CardSetResponseDTO toResponseDTO(CardSet cardSet);

    // todo: add facade pattern
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "linkedCards", ignore = true)
    @Mapping(target = "userId", expression = "java(((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId())")
    public abstract CardSet toEntity(CardSetCreateRequestDTO cardSetDto);

    @Mapping(target = "cardSet.id", expression = "java(cardSet.getId())")
    @Mapping(target = "cardSet.userId", expression = "java(cardSet.getUserId())") // todo maybe ignore or delete mapping
    public abstract void updateEntity(CardSetUpdateRequestDTO cardSetDto, @MappingTarget CardSet cardSet);
}
