package com.eazylearn.dto.request;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;

@Data
public class CardCreateRequestDTO { // todo: add validation
    private String foreignWord;

    private String translateWord;

    private ProficiencyLevel proficiencyLevel;

    private Long categoryId;
}
