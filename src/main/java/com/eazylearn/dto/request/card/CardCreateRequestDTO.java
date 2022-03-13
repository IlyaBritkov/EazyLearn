package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CardCreateRequestDTO implements CardRequest {
    @ApiModelProperty(required = true, example = "word")
    @Length(min = 1, max = 100)
    @NotBlank
    private String term;

    @ApiModelProperty(required = true, example = "слово", position = 1)
    @Length(min = 1, max = 1000)
    @NotBlank
    private String definition;

    @ApiModelProperty(dataType = "boolean", position = 2)
    private Boolean isFavourite;

    @ApiModelProperty(required = true, allowableValues = "LOW, AVERAGE, HIGH", position = 3)
    @NotNull
    private ProficiencyLevel proficiencyLevel;

    @ApiModelProperty(required = true, position = 4)
    @NotNull
    private List<String> linkedCardSetsIds;
}
