package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModelProperty;
import io.vavr.control.Option;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CardCreateRequestDTO implements CardRequest {
    @ApiModelProperty(example = "word", required = true)
    @Length(min = 1, max = 100)
    @NotBlank
    private String term;

    @ApiModelProperty(example = "слово", required = true, position = 1)
    @Length(min = 1, max = 1000)
    @NotBlank
    private String definition;

    @ApiModelProperty(position = 2)
    private boolean isFavourite;

    @ApiModelProperty(allowableValues = "LOW, AVERAGE, HIGH", required = true, position = 3)
    @NotNull
    private ProficiencyLevel proficiencyLevel;

    @ApiModelProperty(allowEmptyValue = true, position = 4)
    private List<String> linkedCardSetsIds; // in any cases initialized with ArrayList

    @JsonSetter("linkedCardSetsIds")
    public void setLinkedCardSetsIds(List<String> linkedCardSetsIds) {
        this.linkedCardSetsIds = Option.of(linkedCardSetsIds).getOrElse(new ArrayList<>());
    }
}
