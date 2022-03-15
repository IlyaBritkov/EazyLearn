package com.eazylearn.dto.request.cardset;

import com.eazylearn.enums.ProficiencyLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CardSetCreateRequestDTO {
    @ApiModelProperty(required = true, example = "Set")
    @NotBlank
    @Length(min = 1, max = 50)
    private String name;

    @ApiModelProperty(required = true, allowableValues = "LOW, AVERAGE, HIGH", position = 1)
    @NotNull
    private ProficiencyLevel proficiencyLevel;

    @ApiModelProperty(dataType = "boolean")
    private Boolean isFavourite = false;

    @ApiModelProperty(required = true, allowEmptyValue = true, position = 2)
    @NotNull
    private List<String> linkedCardsIds;

    @ApiModelProperty(required = true, allowEmptyValue = true, position = 3)
    @NotNull
    private List<NestedCardCreateDTO> linkedNewCards;
}
