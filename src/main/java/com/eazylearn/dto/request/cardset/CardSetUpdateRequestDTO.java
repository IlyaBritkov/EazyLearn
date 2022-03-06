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

    @ApiModelProperty(allowableValues = "true false", dataType = "boolean")
    @Nullable
    private Boolean isFavourite;

    @ApiModelProperty(allowableValues = "LOW AVERAGE HIGH")
    @Nullable
    private ProficiencyLevel proficiencyLevel;

    @Nullable
    private List<String> linkedCardsIds;

    @Nullable
    private List<NestedCardCreateDTO> linkedNewCards;
}
