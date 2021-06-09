package com.eazylearn.service.impl;

import com.eazylearn.repository.CategoryRepository;
import com.eazylearn.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean existsById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    @Override
    public boolean existsByIdAndUserId(Long categoryId, Long userId) {
        return categoryRepository.existsByIdAndUserId(categoryId, userId);
    }
}
