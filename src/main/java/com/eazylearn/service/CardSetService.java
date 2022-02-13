package com.eazylearn.service;

import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.dto.response.CardSetResponseDTO;
import com.eazylearn.exception.EntityAlreadyExistsException;
import com.eazylearn.exception.EntityDoesNotExistException;

import java.util.List;

public interface CardSetService {

    List<CardSetResponseDTO> findAllCategories();

    CardSetResponseDTO findCategoryById(String categoryId) throws EntityDoesNotExistException;

    boolean isExistById(String categoryId);

    CardSetResponseDTO createCategory(CardSetCreateRequestDTO cardSetCreateRequestDTO)
            throws EntityAlreadyExistsException;

    CardSetResponseDTO updateCategoryById(String categoryId, CardSetUpdateRequestDTO cardSetUpdateRequestDTO)
            throws EntityDoesNotExistException;

    void deleteCardSetById(String categoryId, boolean isDeleteAllCardsInCategory) throws EntityDoesNotExistException;
}
