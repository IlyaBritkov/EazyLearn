package com.eazylearn.dto.request.card;

import javax.validation.constraints.NotNull;

public class UpdateCardProficiencyLevelDTO {

    @NotNull
    private String cardId;

    @NotNull
    private Double proficiencyLevel;

}
