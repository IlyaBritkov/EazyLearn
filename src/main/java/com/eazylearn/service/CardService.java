package com.eazylearn.service;

import com.eazylearn.dto.request.card.CardCreateRequestDTO;
import com.eazylearn.dto.request.card.CardUpdateRequestDTO;
import com.eazylearn.dto.request.card.UpdateCardProficiencyLevelDTO;
import com.eazylearn.entity.Card;

import java.util.List;

public interface CardService {

    List<Card> findAllCards();

    Card findCardById(String cardId);

    List<Card> findAllFavouriteCards();

    List<Card> findAllCardsBySetId(String cardSetId);

    List<Card> findAllFavouriteCardsBySetId(String cardSetId);

    List<Card> createCards(List<CardCreateRequestDTO> cardCreateRequestList);

    Card updateCard(CardUpdateRequestDTO updateDto);

    List<Card> updateCards(List<CardUpdateRequestDTO> updateDTOList);

    void deleteCardById(String cardId);

    void deleteCardByCardSetId(String categoryId);

    List<Card> updateCardsProficiencyLevel(List<UpdateCardProficiencyLevelDTO> updateProficiencyDTOList);

}
