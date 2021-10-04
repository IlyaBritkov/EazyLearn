package com.eazylearn.repository;

import com.eazylearn.entity.Card;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends CrudRepository<Card, UUID> { // todo refactor it

    @Override
    <S extends Card> @NotNull List<S> saveAll(@NotNull Iterable<S> entities);

    List<Card> findAlByUserId(UUID userId);

    Optional<Card> findByIdAndUserId(UUID cardId, UUID userId);

    List<Card> findAllByIsFavouriteAndUserId(boolean favourite, UUID userId);

    @Query(value = "SELECT *"
            + "FROM card"
            + "         INNER JOIN set_card set on card.id = set.card_id"
            + "WHERE set.set_id = :cardSetId"
            + "AND card.user_id = :userId"
            + ";",
            nativeQuery = true)
    List<Card> findAllByCardSetIdAndUserId(@Param("cardSetId") UUID cardSetId,
                                           @Param("userId") UUID userId);

    @Query(value = "SELECT *"
            + "FROM card"
            + "         INNER JOIN set_card set on card.id = set.card_id"
            + "WHERE card.is_favourite = :isFavourite"
            + "AND set.set_id = :cardSetId"
            + "AND card.user_id = :userId"
            + ";",
            nativeQuery = true)
    List<Card> findAllByIsFavouriteAndCardSetIdAndUserId(@Param("isFavourite") boolean isFavourite,
                                                         @Param("cardSetId") UUID cardSetId,
                                                         @Param("userId") UUID userId);


//    List<Card> findAllByUserIdAndCardSetId(UUID userId, UUID cardSetId); todo

    boolean existsByIdAndUserId(UUID cardId, UUID userId);

//    void deleteCardByCardSetIdAndUserId(UUID cardSetId, UUID userId); todo
}
