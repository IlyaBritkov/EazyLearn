package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardUpdateRequestDTO implements CardRequest {
    @EqualsAndHashCode.Include
    private String cardId; // can be null during dto creation, but injected in controller from path variable

    @Length(min = 1, max = 100)
    @Nullable
    private String term;

    @Length(min = 1, max = 1000)
    @Nullable
    private String definition;

    // todo: check that mapping from this string value to double equivalent works right
    @Nullable
    private ProficiencyLevel proficiencyLevel;

    @Nullable
    private Boolean isFavourite;

    // todo: handle mapping with null value
    @Nullable
    private List<String> linkedCardSetsIds;
}
