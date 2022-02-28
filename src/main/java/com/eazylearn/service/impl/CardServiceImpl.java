package com.eazylearn.service.impl;

import com.eazylearn.dto.request.card.CardCreateRequestDTO;
import com.eazylearn.dto.request.card.CardUpdateRequestDTO;
import com.eazylearn.dto.request.card.UpdateCardProficiencyLevelDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CardMapper;
import com.eazylearn.repository.CardRepository;
import com.eazylearn.repository.CardSetRepository;
import com.eazylearn.security.jwt.JwtAuthenticationFacadeImpl;
import com.eazylearn.service.CardService;
import com.eazylearn.service.CheckExistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardSetRepository cardSetRepository;
    private final CheckExistenceService checkExistenceService;
    private final CardMapper cardMapper;
    private final JwtAuthenticationFacadeImpl jwtAuthenticationFacade;

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllCards() {
        return cardRepository.findAlByUserId(jwtAuthenticationFacade.getJwtPrincipalId());
    }

    @Override
    @Transactional(readOnly = true)
    public Card findCardById(@NotNull String cardId) {
        Optional<Card> optionalCard = cardRepository.findByIdAndUserId(cardId, jwtAuthenticationFacade.getJwtPrincipalId());

        return optionalCard.
                orElseThrow(() ->
                        new EntityDoesNotExistException(String.format("Card with id=%s doesn't exist", cardId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllFavouriteCards() {
        return cardRepository.findAllByIsFavouriteAndUserId(true, jwtAuthenticationFacade.getJwtPrincipalId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllCardsBySetId(String cardSetId) {
        return cardRepository.findAllByCardSetIdAndUserId(cardSetId, jwtAuthenticationFacade.getJwtPrincipalId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllFavouriteCardsBySetId(String cardSetId) {
        return cardRepository.findAllByIsFavouriteAndCardSetIdAndUserId(true, cardSetId, jwtAuthenticationFacade.getJwtPrincipalId());
    }

    @Override
    public List<Card> createCards(List<CardCreateRequestDTO> cardCreateRequestList) {
        final List<String> linkedCardSetsIds = cardCreateRequestList.stream()
                .flatMap(createRequest -> createRequest.getLinkedCardSetsIds().stream())
                .collect(toList());

        final List<CardSet> linkedCardSets = cardSetRepository.findAllByIdInAndUserId(linkedCardSetsIds, jwtAuthenticationFacade.getJwtPrincipalId());
        checkLinkedCardSetsExistence(linkedCardSetsIds, linkedCardSets);

        final List<Card> newCardsList = cardCreateRequestList.stream()
                .map(cardDto -> {
                    final Card newCard = cardMapper.toEntity(cardDto);
                    // noinspection because existence are checked above
                    //noinspection OptionalGetWithoutIsPresent
                    final CardSet correspondingCardSet = linkedCardSets.stream()
                            .filter(set -> cardDto.getLinkedCardSetsIds().contains(set.getId()))
                            .findAny()
                            .get();
                    newCard.addLinkedCardSet(correspondingCardSet);

                    return newCard;
                })
                .collect(toList());

        return cardRepository.saveAll(newCardsList);
    }

    @Override
    public Card createCard(CardCreateRequestDTO createDto) {
        checkLinkedCardSetsExistence(createDto.getLinkedCardSetsIds());

        return cardRepository.save(cardMapper.toEntity(createDto));
    }

    @Override
    public Card updateCard(CardUpdateRequestDTO updateDto) throws EntityDoesNotExistException {
        final String cardId = updateDto.getCardId();

        final Card cardToUpdate = cardRepository.findByIdAndUserId(cardId, jwtAuthenticationFacade.getJwtPrincipalId())
                .orElseThrow(() -> new EntityDoesNotExistException(String.format("Card with id:%s doesn't exist", cardId)));

        final List<String> linkedCardSetsIds = updateDto.getLinkedCardSetsIds();
        List<CardSet> linkedCardSets = cardToUpdate.getLinkedCardSets();
        if (!isNull(linkedCardSetsIds)) {
            linkedCardSets = cardSetRepository.findAllByIdInAndUserId(linkedCardSetsIds, jwtAuthenticationFacade.getJwtPrincipalId());
            checkLinkedCardSetsExistence(linkedCardSetsIds, linkedCardSets);
        }
        cardMapper.updateEntity(updateDto, cardToUpdate);
        cardToUpdate.setLinkedCardSets(linkedCardSets);

        return cardToUpdate;
    }

    @Override
    public List<Card> updateCards(List<CardUpdateRequestDTO> updateDTOList) {
        final List<String> cardsIds = updateDTOList.stream()
                .map(CardUpdateRequestDTO::getCardId)
                .collect(toList());

        final List<Card> cards = cardRepository.findAllByIdInAndUserId(cardsIds, jwtAuthenticationFacade.getJwtPrincipalId());

        updateDTOList.forEach(updateDTO -> cards.forEach(card -> {
            if (isCorresponding(updateDTO.getCardId(), card.getId())) {
                cardMapper.updateEntity(updateDTO, card);
            }
        }));

        return cards;
    }

    @Override
    public void deleteCardById(String cardId) {
        cardRepository.deleteById(cardId);
    }

    @Override
    public void deleteCardsByCardSetId(String cardSetId) {
        final List<Card> cardsByCardSetId = cardRepository.findAllByCardSetIdAndUserId(cardSetId, jwtAuthenticationFacade.getJwtPrincipalId());

        // filter for cards that belong only to the given cardSet
        final List<Card> cardsToDelete = cardsByCardSetId.stream()
                .filter(card -> {
                    final List<CardSet> linkedCardSets = card.getLinkedCardSets();
                    return linkedCardSets.size() == 1 && isCorresponding(linkedCardSets.get(0).getId(), cardSetId);
                })
                .collect(toList());

        cardRepository.deleteAll(cardsToDelete);
    }

    @Override
    public List<Card> updateCardsProficiencyLevel(List<UpdateCardProficiencyLevelDTO> updateProficiencyDTOList) {
        final List<String> cardsIds = updateProficiencyDTOList.stream()
                .map(UpdateCardProficiencyLevelDTO::getCardId)
                .collect(toList());

        final List<Card> cardsToUpdate = cardRepository.findAllByIdInAndUserId(cardsIds, jwtAuthenticationFacade.getJwtPrincipalId());

        updateProficiencyDTOList.forEach(updateDTO -> cardsToUpdate.forEach(card -> {
            if (isCorresponding(updateDTO.getCardId(), card.getId())) {
                card.setProficiencyLevel(updateDTO.getProficiencyLevelValue());
            }
        }));

        return cardsToUpdate;
    }

    private boolean isCorresponding(String updateDTO, String card) {
        return updateDTO.equals(card);
    }

    /**
     * Checks that all CardSets that are linked with the Card exist.<br>
     * If at least one CardSet doesn't exist exception is thrown.
     * <br>
     *
     * @throws EntityDoesNotExistException if at least one CardSet doesn't exist.
     **/
    private void checkLinkedCardSetsExistence(List<String> linkedCardSetsIds) {

        boolean allCardSetExist = checkExistenceService.areCardSetsByIdsExist(linkedCardSetsIds);
        if (!allCardSetExist) {
            throw new EntityDoesNotExistException(String.format(
                    "%s by some of the following IDs %s doesnt exist", CardSet.class.getName(), linkedCardSetsIds));
        }
    }

    /**
     * Checks that all CardSets, that are linked with the Card,
     * exist by comparing ids from {@param linkedCardSetsIds} with ids of {@param linkedCardSets}.<br>
     * If at least one CardSet doesn't exist exception is thrown.
     * <br>
     *
     * @throws EntityDoesNotExistException if at least one CardSet doesn't exist.
     **/
    private void checkLinkedCardSetsExistence(List<String> linkedCardSetsIds, List<CardSet> linkedCardSets) {
        linkedCardSetsIds.forEach(id -> {
            linkedCardSets.stream()
                    .filter(cardSet -> Objects.equals(id, cardSet.getId()))
                    .findAny()
                    .orElseThrow(() -> new EntityDoesNotExistException(String.format("CardSet by id=%s doesn't exist", id)));
        });
    }

    /**
     * Checks that all entities exist according passed updateDTOList
     *
     * @throws IllegalArgumentException if at least one DTO object doesn't contain ID value
     * @throws IllegalArgumentException if at least one Card doesn't exist by ID
     */
    private void checkCardsExistenceById(@NotNull final List<CardUpdateRequestDTO> updateDTOList) {
        Set<String> cardIds = updateDTOList.stream()
                .map(CardUpdateRequestDTO::getCardId)
                .collect(toSet());

        if (updateDTOList.size() != cardIds.size()) {
            throw new IllegalArgumentException("Some CardDTO doesn't have CardId in the payload");
        }

        boolean areAllCardsExist = checkExistenceService.areCardsByIdsExist(cardIds);
        if (!areAllCardsExist) {
            throw new IllegalArgumentException("Some Card or Cards doesn't exist and cannot be updated");
        }
    }

    private void checkCardExistenceById(String cardId) {
        boolean isCardExists = checkExistenceService.isCardByIdExist(cardId);
        if (!isCardExists) {
            throw new EntityDoesNotExistException(String.format("Card with id:%s doesn't exist", cardId));
        }
    }
}
