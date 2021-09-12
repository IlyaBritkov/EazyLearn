package com.eazylearn.repository;

import com.eazylearn.entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends CrudRepository<Card, UUID> {

    List<Card> findAllByUserId(UUID userId);

    List<Card> findAllByUserIdAndCardSetId(UUID userId, UUID cardSetId);

    Optional<Card> findByIdAndUserId(UUID cardId, UUID userId);

    boolean existsByIdAndUserId(UUID cardId, UUID userId);

    void deleteCardByCardSetIdAndUserId(UUID cardSetId, UUID userId);
}
