package com.eazylearn.service;

import com.eazylearn.dto.request.card.CardCreateRequestDTO;
import com.eazylearn.dto.request.card.CardUpdateRequestDTO;
import com.eazylearn.dto.request.card.UpdateCardProficiencyLevelDTO;
import com.eazylearn.entity.Card;

import java.util.List;
import java.util.UUID;

public interface CardService {

    List<Card> findAllCards();

    Card findCardById(UUID cardId);

    List<Card> findAllFavouriteCards();

    List<Card> findAllCardsBySetId(UUID cardSetId);

    List<Card> findAllFavouriteCardsBySetId(UUID cardSetId);

    List<Card> createCards(List<CardCreateRequestDTO> cardCreateRequestList);

    Card updateCard(CardUpdateRequestDTO updateDto);

    List<Card> updateCards(List<CardUpdateRequestDTO> updateDTOList);

    void deleteCardById(UUID cardId);

    void deleteCardByCardSetId(UUID categoryId);

    List<Card> updateCardsProficiencyLevel(List<UpdateCardProficiencyLevelDTO> updateProficiencyDTOList);

}
