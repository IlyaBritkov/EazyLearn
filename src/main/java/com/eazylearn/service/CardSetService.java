package com.eazylearn.service;

import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.entity.CardSet;

import java.util.List;

public interface CardSetService {

    List<CardSet> findAllCardSets();

    List<CardSet> findAllFavouriteCardSets();

    CardSet findCardSetById(String cardSetId);

    CardSet createCardSet(CardSetCreateRequestDTO cardSetCreateRequestDTO);

    CardSet updateCardSetById(String cardSetId, CardSetUpdateRequestDTO cardSetUpdateRequestDTO);

    void deleteCardSetById(String cardSetId, boolean isDeleteAllLinkedCards);
}
