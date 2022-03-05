package com.eazylearn.dto.request.cardset;

import com.eazylearn.enums.ProficiencyLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CardSetCreateRequestDTO {
    @ApiModelProperty(required = true, example = "Set")
    @NotBlank
    @Length(min = 1, max = 50)
    private String name;

    @ApiModelProperty(required = true, allowableValues = "LOW AVERAGE HIGH", position = 1)
    @NotNull
    private ProficiencyLevel proficiencyLevel;

    @ApiModelProperty(required = true, position = 2)
    private List<String> linkedCardsIds;

    @ApiModelProperty(required = true, position = 3)
    private List<NestedCardCreateDTO> linkedNewCards;
}
