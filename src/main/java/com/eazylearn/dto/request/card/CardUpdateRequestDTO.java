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
    @EqualsAndHashCode.Include
    @ApiModelProperty(hidden = true)
    private String cardId; // can be null during dto creation, but injected in controller from path variable

    @Length(min = 1, max = 100)
    @Nullable
    private String term;

    @Length(min = 1, max = 1000)
    @Nullable
    private String definition;

    @Nullable
    private ProficiencyLevel proficiencyLevel;

    @Nullable
    private Boolean isFavourite;

    @Nullable
    private List<String> linkedCardSetsIds;
}
