package com.eazylearn.mapper;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.enums.ProficiencyLevel;
import com.eazylearn.security.jwt.JwtUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring",
        imports = {SecurityContextHolder.class, JwtUser.class})
public abstract class CardMapper {

    public abstract CardResponseDTO toResponseDTO(Card card);

    @Mapping(source = "proficiencyLevel", target = "proficiencyLevel", qualifiedByName = "proficiencyLevelToProficiencyDouble")
    @Mapping(target = "userId",
            expression = "java( ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId() )")
    public abstract Card toEntity(CardCreateRequestDTO cardDto);

    @Mapping(target = "card.id", expression = "java(card.getId())")
    @Mapping(target = "card.userId", expression = "java(card.getUserId())")
    @Mapping(target = "card.categoryId", defaultExpression = "java(card.getCategoryId())")
    public abstract void updateEntity(CardResponseDTO cardDto, @MappingTarget Card card);

    @Named("proficiencyLevelToProficiencyDouble")
    public static double proficiencyLevelToProficiencyDouble(ProficiencyLevel proficiencyLevel) {
        return proficiencyLevel.getLevelPoints();
    }
}
