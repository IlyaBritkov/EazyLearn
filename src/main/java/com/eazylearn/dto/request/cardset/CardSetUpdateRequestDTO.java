package com.eazylearn.dto.request.cardset;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class CardSetUpdateRequestDTO { // todo: 6/10/2021 add validation

    @Nullable
    private String name;

    @Nullable
    private Boolean isFavourite;

    // todo: maybe addedCardList

    // todo: maybe deletedCardList
}
