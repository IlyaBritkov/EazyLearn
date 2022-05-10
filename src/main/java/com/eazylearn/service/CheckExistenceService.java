package com.eazylearn.service;

import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface CheckExistenceService {

    boolean areCardSetsByIdsExist(Collection<String> cardSetIds);

    boolean isCardSetsByIdExist(String cardSetId);

    boolean isCardSetByNameExist(String cardSetName, String userId);

    boolean areCardsByIdsExist(Collection<String> cardIds);

    boolean isCardByIdExist(String cardId);

    void checkCardSetsExistenceByIds(Collection<String> linkedCardSetsIds);

    void checkCardSetsExistence(Collection<String> cardSetsIds, List<CardSet> cardSets);

    void checkCardsExistenceByIds(@NotNull final Collection<String> cardIds);

    void checkCardExistenceById(String cardId);

    void checkCardsExistence(Collection<String> cardIds, List<Card> cards);
}
