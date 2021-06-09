package com.eazylearn.repository;

import com.eazylearn.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    boolean existsByIdAndUserId(Long categoryId, Long userId);
}