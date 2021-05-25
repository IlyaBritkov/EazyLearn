package eazy.learn.mapper;

import eazy.learn.dto.CardDto;
import eazy.learn.entity.Card;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CardMapper {

    @NotNull
    public abstract CardDto toDto(@NotNull Card card);

    @NotNull
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract Card toEntity(@NotNull CardDto cardDto);

    @Mapping(target = "card.id", expression = "java(card.getId())")
    @Mapping(target = "card.userId", expression = "java(card.getUserId())")
    @Mapping(target = "card.categoryId", defaultExpression = "java(card.getCategoryId())")
    public abstract void updateModel(@NotNull CardDto cardDto, @NotNull @MappingTarget Card card);
}
