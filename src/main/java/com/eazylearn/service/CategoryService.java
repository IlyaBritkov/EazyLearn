package com.eazylearn.service;

public interface CategoryService {
    boolean existsById(Long categoryId);

    boolean existsByIdAndUserId(Long categoryId, Long userId);
}
