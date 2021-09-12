package com.eazylearn.service;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.dto.request.CardUpdateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;

import java.util.List;
import java.util.UUID;

public interface CardService { // todo refactor

    List<CardResponseDTO> findAllCardsByTabAndCardSetId(String tab, UUID categoryId) throws EntityDoesNotExistException;

    List<Card> findAllCardsEntityByCardSetId(UUID categoryId) throws EntityDoesNotExistException;

    CardResponseDTO findCardById(UUID cardId) throws EntityDoesNotExistException;

    CardResponseDTO createCard(CardCreateRequestDTO cardCreateRequestDTO) throws EntityDoesNotExistException;

    CardResponseDTO updateCardById(UUID cardId, CardUpdateRequestDTO updateDto) throws EntityDoesNotExistException;

    void deleteCardById(UUID cardId) throws EntityDoesNotExistException;

    void deleteCardByCardSetId(UUID categoryId) throws EntityDoesNotExistException;
}
