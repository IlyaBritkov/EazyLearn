package com.eazylearn.service;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.exception.CategoryDoesNotExistException;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.exception.EntityDoesNotExistException;

import java.util.List;

public interface CardService {
    List<CardResponseDTO> findAllCardsByTabAndCategoryId(String tab, Long categoryId) throws EntityDoesNotExistException, CategoryDoesNotExistException;

    CardResponseDTO createCard(CardCreateRequestDTO cardCreateRequestDTO);

    CardResponseDTO findCardById(Long cardId);

    CardResponseDTO updateCard(CardResponseDTO card);

    void deleteCardById(Long cardId);
}
