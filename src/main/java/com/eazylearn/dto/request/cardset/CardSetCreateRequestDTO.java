package com.eazylearn.dto.request.cardset;

import com.eazylearn.enums.ProficiencyLevel;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.vavr.control.Option;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CardSetCreateRequestDTO { // todo: 6/10/2021 add validation
    @NotBlank
    private String name;

    @NotNull
    private ProficiencyLevel proficiencyLevel;

    // todo: handle it in controller or service
    private List<String> linkedCardsIds; // can be empty

    // todo: handle it in controller or service
    private List<NestedCardCreateDTO> linkedNewCards; // can be empty

    @JsonSetter("linkedCardsIds")
    public void setLinkedCardsIds(List<String> linkedCardsIds) {
        this.linkedCardsIds = Option.of(linkedCardsIds).getOrElse(new ArrayList<>());
    }

    @JsonSetter("linkedNewCards")
    public void setLinkedNewCards(List<NestedCardCreateDTO> linkedNewCards) {
        this.linkedNewCards = Option.of(linkedNewCards).getOrElse(new ArrayList<>());
    }
}
