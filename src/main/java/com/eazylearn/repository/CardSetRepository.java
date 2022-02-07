package com.eazylearn.repository;

import com.eazylearn.entity.CardSet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardSetRepository extends CrudRepository<CardSet, String> {

    List<CardSet> findAllByUserId(String userId);

    Optional<CardSet> findByIdAndUserId(String id, String userId);

    boolean existsByIdAndUserId(String cardSetId, String userId);

    boolean existsByNameAndUserId(String name, String userId);

    @Query("SELECT COUNT(cardSet) "
            + "FROM CardSet cardSet "
            + "WHERE cardSet.id IN (:ids)")
    long countByIds(@Param("ids") Collection<String> ids);
}
