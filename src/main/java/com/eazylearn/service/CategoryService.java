package com.eazylearn.service;

import com.eazylearn.dto.request.CategoryCreateRequestDTO;
import com.eazylearn.dto.request.CategoryUpdateRequestDTO;
import com.eazylearn.dto.response.CategoryResponseDTO;
import com.eazylearn.exception_handling.exception.EntityAlreadyExistsException;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> findAllCategories();

    CategoryResponseDTO findCategoryById(Long categoryId) throws EntityDoesNotExistException;

    boolean existsById(Long categoryId);

    CategoryResponseDTO createCategory(CategoryCreateRequestDTO categoryCreateRequestDTO) throws EntityAlreadyExistsException;

    CategoryResponseDTO updateCategoryById(Long categoryId, CategoryUpdateRequestDTO categoryUpdateRequestDTO) throws EntityDoesNotExistException;

    void deleteCategoryById(Long categoryId, boolean isDeleteAllCardsInCategory) throws EntityDoesNotExistException;
}
