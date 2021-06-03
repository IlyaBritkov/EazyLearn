package eazy.learn.dto.request;

import eazy.learn.dto.BaseCardDTO;
import eazy.learn.enums.ProficientLevel;

public class CardCreateRequestDTO extends BaseCardDTO { // todo: add validation
    private String foreignWord;

    private String translateWord;

    private ProficientLevel proficientLevel;

    private Long categoryId;
}
