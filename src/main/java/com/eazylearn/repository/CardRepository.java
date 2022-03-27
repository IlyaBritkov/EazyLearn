package com.eazylearn.repository;

import com.eazylearn.entity.Card;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CardRepository extends CrudRepository<Card, String> { // todo refactor it

    @Override
    @NotNull
    List<Card> findAllById(@NotNull Iterable<String> ids);

    @Override
    <S extends Card> @NotNull List<S> saveAll(@NotNull Iterable<S> entities);

    List<Card> findAlByUserId(String userId);

    List<Card> findAllByIsFavouriteAndUserId(boolean favourite, String userId);

    @Query(value = "SELECT * "
            + "FROM card " +
            "INNER JOIN set_card on card.id = set_card.card_id "
            + "INNER JOIN cardSet on cardSet.id = set_card.set_id "
            + "WHERE cardSet.id = :cardSetId",
            nativeQuery = true)
    List<Card> findAllByCardSetId(@Param("cardSetId") String cardSetId);

    @Query(value = "SELECT * "
            + "FROM card " +
            "INNER JOIN set_card on card.id = set_card.card_id "
            + "INNER JOIN cardSet on cardSet.id = set_card.set_id "
            + "WHERE card.is_favourite = :isFavourite "
            + "AND cardSet.id = :cardSetId "
            + "AND card.user_id = :userId"
            + ";",
            nativeQuery = true)
    List<Card> findAllByIsFavouriteAndCardSetIdAndUserId(@Param("isFavourite") boolean isFavourite,
                                                         @Param("cardSetId") String cardSetId,
                                                         @Param("userId") String userId);


    long countByIdIn(@NonNull Collection<String> ids);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE card "
            + "SET proficiency_level = "
            + "CASE "
            + "WHEN proficiency_level <= 0.1 THEN 0.0 "
            + "ELSE proficiency_level - :valueOfDecrease "
            + "END, "
            + "last_update_datetime = now() "
            + "WHERE last_update_datetime < (now() - INTERVAL '7 day')"
            + ";",
            nativeQuery = true)
    void decreaseProficiencyLevelIfLastUpdateDateLessThanWeek(@Param("valueOfDecrease") double valueOfDecrease);
}
