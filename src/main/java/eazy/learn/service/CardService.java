package eazy.learn.service;

import eazy.learn.dto.request.CardCreateRequestDTO;
import eazy.learn.dto.response.CardResponseDTO;
import eazy.learn.exception.CategoryDoesNotExistException;
import eazy.learn.exception.TabDoesNotExistException;

import java.util.List;

public interface CardService {
    List<CardResponseDTO> findAllCardsByTabAndCategoryId(String tab, Long categoryId) throws TabDoesNotExistException, CategoryDoesNotExistException;

    CardResponseDTO createCard(CardCreateRequestDTO cardCreateRequestDTO);

    CardResponseDTO findCardById(Long cardId);

    CardResponseDTO updateCard(CardResponseDTO card);

    void deleteCardById(Long cardId);
}
