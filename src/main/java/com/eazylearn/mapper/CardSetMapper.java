package com.eazylearn.mapper;

import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.dto.response.CardSetResponseDTO;
import com.eazylearn.entity.BaseEntity;
import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;
import com.eazylearn.enums.ProficiencyLevel;
import com.eazylearn.security.jwt.JwtUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        imports = {SecurityContextHolder.class, JwtUser.class})
public abstract class CardSetMapper { // todo: update mapping

    @Mapping(source = "isFavourite", target = "favourite")
    @Mapping(source = "linkedCards", target = "linkedCardsIds",
            qualifiedByName = "linkedCardsToLinkedCardsIds")
    public abstract CardSetResponseDTO toResponseDTO(CardSet cardSet);

    // todo: fix with jwt-facade
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "linkedCards", ignore = true)
    @Mapping(source = "proficiencyLevel", target = "proficiencyLevel", qualifiedByName = "proficiencyLevelToProficiencyDouble")
    @Mapping(target = "userId", expression = "java(((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId())")
    public abstract CardSet toEntity(CardSetCreateRequestDTO cardSetDto);

    @Mapping(target = "id", ignore = true) // TODO check if ignore is needed
    @Mapping(target = "createdDateTime", ignore = true) // TODO check if ignore is needed
    @Mapping(target = "userId", ignore = true) // TODO check if ignore is needed
    @Mapping(source = "proficiencyLevel", target = "proficiencyLevel", qualifiedByName = "proficiencyLevelToProficiencyDouble")
    public abstract void updateEntity(CardSetUpdateRequestDTO cardSetDto, @MappingTarget CardSet cardSet);

    // TODO maybe make it protected
    // ? should it be static?
    @Named("linkedCardsToLinkedCardsIds")
    public static List<String> linkedCardsToLinkedCardsIds(List<Card> linkedCards) {
        return linkedCards.stream()
                .map(BaseEntity::getId)
                .collect(toList());
    }

    // TODO maybe make it protected
    // ? should it be static?
    @Named("proficiencyLevelToProficiencyDouble")
    public static double proficiencyLevelToProficiencyDouble(ProficiencyLevel proficiencyLevel) {
        return proficiencyLevel.getLevelPoints();
    }
}
