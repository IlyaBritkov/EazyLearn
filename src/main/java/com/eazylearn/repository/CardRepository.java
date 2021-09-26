package com.eazylearn.repository;

import com.eazylearn.entity.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends CrudRepository<Card, UUID> { // todo refactor it

    List<Card> findAlByUserId(UUID userId);

    Optional<Card> findByIdAndUserId(UUID cardId, UUID userId);

    List<Card> findAllByIsFavouriteAndUserId(boolean favourite, UUID userId);

    @Query(value = "SELECT *"
            + "FROM card"
            + "         INNER JOIN set_card set on card.id = set.card_id"
            + "WHERE set.set_id = ?1"
            + "AND card.user_id = ?2"
            + ";",
            nativeQuery = true)
    List<Card> findAllByCardSetIdAndUserId(UUID cardSetId, UUID userId);

    @Query(value = "SELECT *"
            + "FROM card"
            + "         INNER JOIN set_card set on card.id = set.card_id"
            + "WHERE card.is_favourite = ?1"
            + "AND set.set_id = ?2"
            + "AND card.user_id = ?3"
            + ";",
            nativeQuery = true)
        // TODO try to replace numbers by names in the query
    List<Card> findAllByIsFavouriteAndCardSetIdAndUserId(boolean favourite, UUID cardSetID, UUID userId);


//    List<Card> findAllByUserIdAndCardSetId(UUID userId, UUID cardSetId); todo


    boolean existsByIdAndUserId(UUID cardId, UUID userId);

//    void deleteCardByCardSetIdAndUserId(UUID cardSetId, UUID userId); todo
}
