package com.eazylearn.service;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.dto.request.CardUpdateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;

import java.util.List;

public interface CardService {
    List<CardResponseDTO> findAllCardsByTabAndCategoryId(String tab, Long categoryId) throws EntityDoesNotExistException;

    List<Card> findAllCardsEntityByCategoryId(Long categoryId) throws EntityDoesNotExistException;

    CardResponseDTO findCardById(Long cardId) throws EntityDoesNotExistException;

    CardResponseDTO createCard(CardCreateRequestDTO cardCreateRequestDTO) throws EntityDoesNotExistException;

    CardResponseDTO updateCardById(Long cardId, CardUpdateRequestDTO updateDto) throws EntityDoesNotExistException;

    void deleteCardById(Long cardId) throws EntityDoesNotExistException;

    void deleteCardByCategoryId(Long categoryId) throws EntityDoesNotExistException;
}
