package com.eazylearn.dto.request.card;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateCardProficiencyLevelDTO {

    @NotNull
    private UUID cardId;

    @NotNull
    private Double proficiencyLevel;

}
