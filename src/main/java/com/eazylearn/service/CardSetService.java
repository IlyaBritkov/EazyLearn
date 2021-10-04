package com.eazylearn.service;

import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.dto.response.CardSetResponseDTO;
import com.eazylearn.exception_handling.exception.EntityAlreadyExistsException;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;

import java.util.List;
import java.util.UUID;

public interface CardSetService {

    List<CardSetResponseDTO> findAllCategories();

    CardSetResponseDTO findCategoryById(UUID categoryId) throws EntityDoesNotExistException;

    boolean existsById(UUID categoryId);

    CardSetResponseDTO createCategory(CardSetCreateRequestDTO cardSetCreateRequestDTO) throws EntityAlreadyExistsException;

    CardSetResponseDTO updateCategoryById(UUID categoryId, CardSetUpdateRequestDTO cardSetUpdateRequestDTO) throws EntityDoesNotExistException;

    void deleteCategoryById(UUID categoryId, boolean isDeleteAllCardsInCategory) throws EntityDoesNotExistException;
}
