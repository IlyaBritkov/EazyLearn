package com.eazylearn.mapper;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.dto.request.CardUpdateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.enums.ProficiencyLevel;
import com.eazylearn.security.jwt.JwtUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        imports = {SecurityContextHolder.class, JwtUser.class})
public abstract class CardMapper {

    public abstract CardResponseDTO toResponseDTO(Card card);

    @Mapping(source = "proficiencyLevel", target = "proficiencyLevel", qualifiedByName = "proficiencyLevelToProficiencyDouble")
    @Mapping(target = "userId",
            expression = "java( ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId() )")
    public abstract Card toEntity(CardCreateRequestDTO cardDto);

    @Mapping(target = "card.id", expression = "java(card.getId())") // todo maybe just ignore
    @Mapping(target = "card.userId", expression = "java(card.getUserId())")
    public abstract void updateEntity(CardUpdateRequestDTO cardDto, @MappingTarget Card card);

    @Named("proficiencyLevelToProficiencyDouble")
    public static double proficiencyLevelToProficiencyDouble(ProficiencyLevel proficiencyLevel) {
        return proficiencyLevel.getLevelPoints();
    }

}
