package com.eazylearn.dto.request.card;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class CardCreateRequestDTO { // todo: add validation

    @Length(min = 1, max = 100)
    private String term;

    @Length(min = 1, max = 1000)
    private String definition;

    @NotNull
    private ProficiencyLevel proficiencyLevel;

    private List<UUID> cardSetIds;

}
