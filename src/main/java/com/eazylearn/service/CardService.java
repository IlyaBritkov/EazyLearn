package com.eazylearn.service;

import com.eazylearn.dto.request.card.CardCreateRequestDTO;
import com.eazylearn.dto.request.card.CardUpdateRequestDTO;
import com.eazylearn.dto.request.card.UpdateCardProficiencyLevelDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;

import java.util.List;

public interface CardService {

    List<Card> findAllCards();

    Card findCardById(String cardId);

    List<Card> findAllFavouriteCards();

    List<Card> findAllCardsBySetId(String cardSetId);

    List<Card> findAllFavouriteCardsBySetId(String cardSetId);

    List<Card> createCards(List<CardCreateRequestDTO> cardCreateRequestList);

    Card createCard(CardCreateRequestDTO createDto);

    List<Card> updateCards(List<CardUpdateRequestDTO> updateDTOList);

    Card updateCard(CardUpdateRequestDTO updateDto);

    void deleteCardById(String cardId);

    void deleteCardsFromCardSet(CardSet cardSet);

    List<Card> updateCardsProficiencyLevel(List<UpdateCardProficiencyLevelDTO> updateProficiencyDTOList);
}
