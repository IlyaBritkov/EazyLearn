package com.eazylearn.repository;

import com.eazylearn.entity.CardSet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardSetRepository extends CrudRepository<CardSet, UUID> {

    List<CardSet> findAllByUserId(UUID userId);

    Optional<CardSet> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByIdAndUserId(UUID cardSetId, UUID userId);

    boolean existsByNameAndUserId(String name, UUID userId);

    @Query("SELECT COUNT(cardSet) "
            + "FROM CardSet cardSet "
            + "WHERE cardSet.id IN (:ids)")
    long countByIds(@Param("ids") Collection<UUID> ids);

}
