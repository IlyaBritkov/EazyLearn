package com.eazylearn.dto.request;

import lombok.Data;

@Data
public class CardUpdateRequestDTO { // TODO: 6/9/2021 add validation
    private String foreignWord;

    private String translateWord;

    private Double proficiencyLevel;

    private Long categoryId;
}
