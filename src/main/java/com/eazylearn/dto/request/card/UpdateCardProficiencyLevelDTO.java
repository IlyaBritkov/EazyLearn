package com.eazylearn.dto.request.card;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCardProficiencyLevelDTO {
    @NotNull
    private String cardId;
    @NotNull
    private Double proficiencyLevelValue;
}
