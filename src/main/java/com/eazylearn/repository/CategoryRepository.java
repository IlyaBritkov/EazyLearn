package com.eazylearn.repository;

import com.eazylearn.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findAllByUserId(Long userId);

    Optional<Category> findByIdAndUserId(Long id, Long userId);

    boolean existsByIdAndUserId(Long categoryId, Long userId);

    boolean existsByNameAndUserId(String name, Long userId);
}
