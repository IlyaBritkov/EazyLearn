package com.eazylearn.dto.request.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCardProficiencyLevelDTO {
    @ApiModelProperty(required = true)
    @NotNull
    private String cardId;

    @ApiModelProperty(required = true, example = "0.15", dataType = "Double", position = 1)
    @NotNull
    private Double proficiencyLevelValue;
}
