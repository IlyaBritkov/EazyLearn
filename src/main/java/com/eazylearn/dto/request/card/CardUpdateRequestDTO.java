package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Data
public class CardUpdateRequestDTO implements CardRequest {

    @Nullable
    private UUID cardId; // can be null while dto creation, but injected from path variable

    @Nullable
    @Length(min = 1, max = 100)
    private String term;

    @Nullable
    @Length(min = 1, max = 1000)
    private String definition;

    // TODO: check that mapping from this string value to double equivalent works right
    @Nullable
    private ProficiencyLevel proficiencyLevel;

    @Nullable
    private Boolean isFavourite;

    // TODO: handle mapping with null value
    @Nullable
    private List<UUID> linkedCardSetsIds;

}
