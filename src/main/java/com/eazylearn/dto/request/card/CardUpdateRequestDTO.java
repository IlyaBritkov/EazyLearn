package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardUpdateRequestDTO implements CardRequest {
    @ApiModelProperty(notes = "Must be present in bulk update request")
    @EqualsAndHashCode.Include
    private String cardId; // can be null during dto creation, but injected in controller from path variable

    @ApiModelProperty(example = "word")
    @Length(min = 1, max = 100)
    @Nullable
    private String term;

    @ApiModelProperty(example = "слово", position = 1)
    @Length(min = 1, max = 1000)
    @Nullable
    private String definition;

    @ApiModelProperty(allowableValues = "LOW, AVERAGE, HIGH", position = 2)
    @Nullable
    private ProficiencyLevel proficiencyLevel;

    @ApiModelProperty(dataType = "boolean", position = 3)
    @Nullable
    private Boolean isFavourite;

    @ApiModelProperty(allowEmptyValue = true, position = 4)
    @Nullable
    private List<String> linkedCardSetsIds;
}
