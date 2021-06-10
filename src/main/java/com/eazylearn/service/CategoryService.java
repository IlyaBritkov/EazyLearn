package com.eazylearn.service;

import com.eazylearn.dto.response.CategoryResponseDTO;
import com.eazylearn.exception.EntityDoesNotExistException;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> findAllCategories();

    CategoryResponseDTO findCategoryById(Long categoryId) throws EntityDoesNotExistException;

    boolean existsById(Long categoryId);

}
