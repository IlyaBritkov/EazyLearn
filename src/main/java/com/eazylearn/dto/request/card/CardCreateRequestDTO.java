package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CardCreateRequestDTO implements CardRequest { // todo: add validation
    @Length(min = 1, max = 100)
    @NotBlank
    private String term;

    @Length(min = 1, max = 1000)
    @NotBlank
    private String definition;

    @NotNull
    private ProficiencyLevel proficiencyLevel;

    private List<String> linkedCardSetsIds = new ArrayList<>();
}
