package com.eazylearn.dto.request.cardset;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NestedCardCreateDTO {
    @Length(min = 1, max = 100)
    @NotBlank
    private String term;

    @Length(min = 1, max = 1000)
    @NotBlank
    private String definition;

    @NotNull
    private ProficiencyLevel proficiencyLevel;
}
