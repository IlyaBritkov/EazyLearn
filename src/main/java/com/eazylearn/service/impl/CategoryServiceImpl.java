package com.eazylearn.service.impl;

import com.eazylearn.dto.request.CategoryCreateRequestDTO;
import com.eazylearn.dto.request.CategoryUpdateRequestDTO;
import com.eazylearn.dto.response.CategoryResponseDTO;
import com.eazylearn.entity.Category;
import com.eazylearn.exception.EntityAlreadyExistsException;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CategoryMapper;
import com.eazylearn.repository.CategoryRepository;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
@Scope(value = SCOPE_SESSION, proxyMode = INTERFACES)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final JwtUser currentUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private final Long currentUserId = currentUser.getId();

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAllCategories() {
        List<Category> allCategories = categoryRepository.findAllByUserId(currentUserId);
        return allCategories.stream()
                .map(categoryMapper::toResponseDTO)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true, isolation = SERIALIZABLE)
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public CategoryResponseDTO findCategoryById(Long categoryId) throws EntityDoesNotExistException {
        checkCategoryExistenceById(categoryId);

        Category categoryById = categoryRepository.findByIdAndUserId(categoryId, currentUserId).get();
        return categoryMapper.toResponseDTO(categoryById);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long categoryId) {
        return categoryRepository.existsByIdAndUserId(categoryId, currentUserId);
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public CategoryResponseDTO createCategory(CategoryCreateRequestDTO categoryCreateRequestDTO) throws EntityAlreadyExistsException {
        String newCategoryName = categoryCreateRequestDTO.getName();
        if (categoryRepository.existsByNameAndUserId(newCategoryName, currentUserId)) {
            throw new EntityAlreadyExistsException(String.format("Category with name = %s already exists", newCategoryName));
        } else {
            Category newCategory = categoryMapper.toEntity(categoryCreateRequestDTO);
            Category persistedCategory = categoryRepository.save(newCategory);

            return categoryMapper.toResponseDTO(persistedCategory);
        }
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public CategoryResponseDTO updateCategoryById(Long categoryId, CategoryUpdateRequestDTO updateDTO) throws EntityDoesNotExistException {
        checkCategoryExistenceById(categoryId);

        Category updatedCategory = categoryRepository.findByIdAndUserId(categoryId, currentUserId).get();
        categoryMapper.updateEntity(updateDTO, updatedCategory);

        return categoryMapper.toResponseDTO(updatedCategory);
    }

    protected void checkCategoryExistenceById(@Nullable Long categoryId) throws EntityDoesNotExistException {
        if (categoryId != null) {
            boolean isCategoryExists = existsById(categoryId);
            if (isCategoryExists) {
                return;
            }
        }
        throw new EntityDoesNotExistException(String.format("Category with id:%d doesn't exist", categoryId));
    }
}
