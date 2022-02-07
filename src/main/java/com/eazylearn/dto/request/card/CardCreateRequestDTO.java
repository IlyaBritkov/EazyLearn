package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CardCreateRequestDTO implements CardRequest { // todo: add validation

    @Length(min = 1, max = 100)
    private String term;

    @Length(min = 1, max = 1000)
    private String definition;

    @NotNull
    private ProficiencyLevel proficiencyLevel;

    // todo: handle mapping with null value
    @Nullable
    private List<String> linkedCardSetsIds;

}
