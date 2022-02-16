package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.vavr.control.Option;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CardCreateRequestDTO implements CardRequest { // todo: add validation
    @Length(min = 1, max = 100)
    @NotBlank
    private String term;

    @Length(min = 1, max = 1000)
    @NotBlank
    private String definition;

    private boolean isFavourite;

    @NotNull
    private ProficiencyLevel proficiencyLevel;

    private List<String> linkedCardSetsIds;

    @JsonSetter("linkedCardSetsIds")
    public void setLinkedCardSetsIds(List<String> linkedCardSetsIds) {
        this.linkedCardSetsIds = Option.of(linkedCardSetsIds).getOrElse(new ArrayList<>());
    }
}
