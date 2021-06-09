package com.eazylearn.repository;

import com.eazylearn.entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    List<Card> findAllByUserId(Long userId);

    List<Card> findAllByUserIdAndCategoryId(Long userId, Long categoryId);

    Optional<Card> findByIdAndUserId(Long cardId, Long userId);

    boolean existsByIdAndUserId(Long cardId, Long userId);
}
