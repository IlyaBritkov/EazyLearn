package com.eazylearn.mapper;

import com.eazylearn.dto.request.card.CardCreateRequestDTO;
import com.eazylearn.dto.request.card.CardUpdateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
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
public abstract class CardMapper {

    // todo: ADD CardSet mapping
    @Mapping(source = "proficiencyLevel", target = "proficiencyLevel", qualifiedByName = "proficiencyLevelToProficiencyDouble")
    @Mapping(target = "userId",
            expression =
                    "java( ((com.eazylearn.security.jwt.JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId() )")
    public abstract Card toEntity(CardCreateRequestDTO cardDto);

    @Mapping(source = "linkedCardSets", target = "linkedCardSetsIds", qualifiedByName = "linkedCardSetsToLinkedCardSetsIds")
    public abstract CardResponseDTO toResponseDTO(Card card);

    @Mapping(target = "card.id", ignore = true)
    @Mapping(target = "card.userId", ignore = true) // TODO check if ignore is needed
    @Mapping(source = "proficiencyLevel", target = "proficiencyLevel",
            qualifiedByName = "proficiencyLevelToProficiencyDouble")
    public abstract void updateEntity(CardUpdateRequestDTO cardDto, @MappingTarget Card card);

    // TODO maybe make it protected
    // ? should it be static?
    @Named("proficiencyLevelToProficiencyDouble")
    public static double proficiencyLevelToProficiencyDouble(ProficiencyLevel proficiencyLevel) {
        return proficiencyLevel.getLevelPoints();
    }

    @Named("linkedCardSetsToLinkedCardSetsIds")
    public static List<String> linkedCardSetsToLinkedCardSetsIds(List<CardSet> linkedCardSets) {
        return linkedCardSets.stream()
                .map(BaseEntity::getId)
                .collect(toList());
    }

    public List<CardResponseDTO> mapCardListToCardResponseDTOList(List<Card> cardList) {
        return cardList.stream()
                .map(this::toResponseDTO)
                .collect(toList());
    }

}
