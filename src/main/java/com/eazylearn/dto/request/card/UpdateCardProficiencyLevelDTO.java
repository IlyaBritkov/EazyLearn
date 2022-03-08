package com.eazylearn.dto.request.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCardProficiencyLevelDTO {
    @ApiModelProperty(required = true)
    @NotNull
    private String cardId;

    @ApiModelProperty(required = true, notes = "Double value in range of 0.00 and 1.00", dataType = "double", example = "0.15", position = 1)
    @NotNull
    private Double proficiencyLevelValue;
}
