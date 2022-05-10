package com.eazylearn.dto.request.cardset;

import com.eazylearn.enums.ProficiencyLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class CardSetUpdateRequestDTO {
    @ApiModelProperty(example = "Set")
    @Nullable
    @Length(min = 1, max = 50)
    private String name;

    @ApiModelProperty(dataType = "boolean", position = 1)
    @Nullable
    private Boolean isFavourite;

    @ApiModelProperty(allowableValues = "LOW AVERAGE HIGH", position = 2)
    @Nullable
    private ProficiencyLevel proficiencyLevel;

    @ApiModelProperty(allowEmptyValue = true, position = 3)
    @Nullable
    private List<String> linkedCardsIds;

    @ApiModelProperty(allowEmptyValue = true, position = 4)
    @Nullable
    private List<NestedCardCreateDTO> linkedNewCards;
}
