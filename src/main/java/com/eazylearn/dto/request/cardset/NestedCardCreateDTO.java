package com.eazylearn.dto.request.cardset;

import com.eazylearn.enums.ProficiencyLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NestedCardCreateDTO {
    @ApiModelProperty(required = true, example = "Term")
    @Length(min = 1, max = 100)
    @NotBlank
    private String term;

    @ApiModelProperty(required = true, example = "Термин")
    @Length(min = 1, max = 1000)
    @NotBlank
    private String definition;

    @ApiModelProperty(required = true, allowableValues = "LOW AVERAGE HIGH")
    @NotNull
    private ProficiencyLevel proficiencyLevel;
}
