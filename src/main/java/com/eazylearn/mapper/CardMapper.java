package com.eazylearn.mapper;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CardMapper {

    public abstract CardResponseDTO toResponseDTO(Card card);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract Card createDTOtoEntity(CardCreateRequestDTO cardDto);

    @Mapping(target = "card.id", expression = "java(card.getId())")
    @Mapping(target = "card.userId", expression = "java(card.getUserId())")
    @Mapping(target = "card.categoryId", defaultExpression = "java(card.getCategoryId())")
    public abstract void updateModel(CardResponseDTO cardDto, @MappingTarget Card card);
}
