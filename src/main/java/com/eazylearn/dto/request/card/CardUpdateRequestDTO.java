package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@Data
public class CardUpdateRequestDTO {

    @Nullable
    private UUID cardId; // null when id is a path variable

    @Length(min = 1, max = 100)
    private String term;

    @Length(min = 1, max = 1000)
    private String definition;

    @Nullable
    private ProficiencyLevel proficiencyLevel;

    private List<UUID> cardSetIds;

}
