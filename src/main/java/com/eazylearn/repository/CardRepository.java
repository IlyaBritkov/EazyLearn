package com.eazylearn.repository;

import com.eazylearn.entity.Card;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends CrudRepository<Card, String> { // todo refactor it

    @Override
    <S extends Card> @NotNull List<S> saveAll(@NotNull Iterable<S> entities);

    List<Card> findAlByUserId(String userId);

    Optional<Card> findByIdAndUserId(String cardId, String userId);

    List<Card> findAllByIsFavouriteAndUserId(boolean favourite, String userId);

    @Query(value = "SELECT *"
            + "FROM card"
            + "         INNER JOIN set_card on set_card.card_id = card.id"
            + "WHERE set_card.set_id = :cardSetId"
            + "AND card.user_id = :userId"
            + ";",
            nativeQuery = true)
    List<Card> findAllByCardSetIdAndUserId(@Param("cardSetId") String cardSetId,
                                           @Param("userId") String userId);

    @Query(value = "SELECT *"
            + "FROM card"
            + "         INNER JOIN set_card set on card.id = set.card_id"
            + "WHERE card.is_favourite = :isFavourite"
            + "AND set.set_id = :cardSetId"
            + "AND card.user_id = :userId"
            + ";",
            nativeQuery = true)
    List<Card> findAllByIsFavouriteAndCardSetIdAndUserId(@Param("isFavourite") boolean isFavourite,
                                                         @Param("cardSetId") String cardSetId,
                                                         @Param("userId") String userId);


//    List<Card> findAllByUserIdAndCardSetId(UUID userId, UUID cardSetId); todo

    boolean existsByIdAndUserId(String cardId, String userId);

    long countByIdIn(@NonNull Collection<String> ids);

//    void deleteCardByCardSetIdAndUserId(UUID cardSetId, UUID userId); todo
}
