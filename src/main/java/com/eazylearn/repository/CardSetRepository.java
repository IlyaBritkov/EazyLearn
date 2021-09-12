package com.eazylearn.repository;

import com.eazylearn.entity.CardSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardSetRepository extends CrudRepository<CardSet, UUID> {

    List<CardSet> findAllByUserId(UUID userId);

    Optional<CardSet> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByIdAndUserId(UUID cardSetId, UUID userId);

    boolean existsByNameAndUserId(String name, UUID userId);

}
