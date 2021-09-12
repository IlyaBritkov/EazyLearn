package com.eazylearn.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CardUpdateRequestDTO { // TODO: 6/9/2021 add validation

    private String term;

    private String definition;

    private Double proficiencyLevel;

    private UUID cardSetId; // todo many

}
