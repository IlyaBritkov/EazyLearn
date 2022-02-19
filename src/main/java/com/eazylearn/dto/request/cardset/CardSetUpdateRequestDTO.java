package com.eazylearn.dto.request.cardset;

import com.eazylearn.enums.ProficiencyLevel;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class CardSetUpdateRequestDTO { // todo: 6/10/2021 add validation
    @Nullable
    private String name;

    @Nullable
    private Boolean isFavourite;

    @Nullable
    private ProficiencyLevel proficiencyLevel;

    // todo: handle it in controller or service
    @Nullable
    private List<String> linkedCardsIds; // can be empty

    // todo: handle it in controller or service
    @Nullable
    private List<NestedCardCreateDTO> linkedNewCards; // can be empty
}
