package com.eazylearn.dto.request;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;

import java.util.UUID;

@Data
public class CardCreateRequestDTO { // todo: add validation

    private String term;

    private String definition;

    private ProficiencyLevel proficiencyLevel;

    private UUID cardSetId; // todo: maybe many cardSets

}
