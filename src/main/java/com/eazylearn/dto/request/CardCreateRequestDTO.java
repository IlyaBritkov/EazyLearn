package com.eazylearn.dto.request;

import com.eazylearn.dto.BaseCardDTO;
import com.eazylearn.enums.ProficientLevel;

public class CardCreateRequestDTO extends BaseCardDTO { // todo: add validation
    private String foreignWord;

    private String translateWord;

    private ProficientLevel proficientLevel;

    private Long categoryId;
}
