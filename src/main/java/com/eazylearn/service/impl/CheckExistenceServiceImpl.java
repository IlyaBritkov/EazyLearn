package com.eazylearn.service.impl;

import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.repository.CardRepository;
import com.eazylearn.repository.CardSetRepository;
import com.eazylearn.service.CheckExistenceService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.singletonList;

@RequiredArgsConstructor

@Service
public class CheckExistenceServiceImpl implements CheckExistenceService {

    private final CardSetRepository cardSetRepository;
    private final CardRepository cardRepository;

    @Override
    public boolean areCardSetsByIdsExist(Collection<String> cardSetIds) {
        long countByIds = cardSetRepository.countByIds(cardSetIds);
        return cardSetIds.size() == countByIds;
    }

    @Override
    public boolean isCardSetsByIdExist(String cardSetId) {
        long countByIds = cardSetRepository.countById(cardSetId);
        return countByIds != 0;
    }

    @Override
    public boolean isCardSetByNameExist(String cardSetName, String userId) {
        return cardSetRepository.existsByNameAndUserId(cardSetName, userId);
    }

    @Override
    public boolean areCardsByIdsExist(Collection<String> cardIds) {
        return cardIds.size() == cardRepository.countByIdIn(cardIds);
    }

    @Override
    public boolean isCardByIdExist(String cardId) {
        long countById = cardRepository.countByIdIn(singletonList(cardId));
        return countById != 0;
    }

    /**
     * Checks that all CardSets by passed ids exist.<br>
     * If at least one CardSet doesn't exist exception is thrown.
     * <br>
     *
     * @throws EntityDoesNotExistException if at least one CardSet doesn't exist.
     **/
    @Override
    public void checkCardSetsExistenceByIds(Collection<String> cardSetsIds) {
        boolean allCardSetExist = areCardSetsByIdsExist(cardSetsIds);
        if (!allCardSetExist) {
            throw new EntityDoesNotExistException(String.format(
                    "%s by some of the following IDs %s doesnt exist", CardSet.class.getName(), cardSetsIds));
        }
    }

    /**
     * Checks that all CardSets, that are linked with the Card,
     * exist by comparing ids from {@param cardSetsIds} with ids of {@param cardSets}.<br>
     * If at least one CardSet doesn't exist exception is thrown.
     * <br>
     *
     * @throws EntityDoesNotExistException if at least one CardSet doesn't exist.
     **/
    @Override
    public void checkCardSetsExistence(Collection<String> cardSetsIds, List<CardSet> cardSets) {
        cardSetsIds.forEach(id -> cardSets.stream()
                .filter(cardSet -> Objects.equals(id, cardSet.getId()))
                .findAny()
                .orElseThrow(() -> new EntityDoesNotExistException(String.format("CardSet by id=%s doesn't exist", id))));
    }


    @Override
    public void checkCardExistenceById(String cardId) {
        boolean isCardExists = isCardByIdExist(cardId);
        if (!isCardExists) {
            throw new EntityDoesNotExistException(String.format("Card with id:%s doesn't exist", cardId));
        }
    }

    /**
     * Checks that all entities exist according passed updateDTOList
     *
     * @throws IllegalArgumentException if at least one DTO object doesn't contain ID value
     * @throws IllegalArgumentException if at least one Card doesn't exist by ID
     */
    @Override
    public void checkCardsExistenceByIds(@NotNull final Collection<String> cardIds) {
        boolean areAllCardsExist = areCardsByIdsExist(cardIds);
        if (!areAllCardsExist) {
            throw new IllegalArgumentException("Some Card or Cards doesn't exist and cannot be updated");
        }
    }

    @Override
    public void checkCardsExistence(Collection<String> cardIds, List<Card> cards) {
        cardIds.forEach(id -> cards.stream()
                .filter(card -> Objects.equals(id, card.getId()))
                .findAny()
                .orElseThrow(() -> new EntityDoesNotExistException(String.format("Card by id=%s doesn't exist", id))));
    }
}
