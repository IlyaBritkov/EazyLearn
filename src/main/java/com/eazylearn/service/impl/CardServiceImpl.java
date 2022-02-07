package com.eazylearn.service.impl;

import com.eazylearn.dto.request.card.CardCreateRequestDTO;
import com.eazylearn.dto.request.card.CardRequest;
import com.eazylearn.dto.request.card.CardUpdateRequestDTO;
import com.eazylearn.dto.request.card.UpdateCardProficiencyLevelDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CardMapper;
import com.eazylearn.repository.CardRepository;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.service.CardService;
import com.eazylearn.service.CardSetService;
import com.eazylearn.service.CheckExistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Service
@Scope(value = SCOPE_SESSION, proxyMode = INTERFACES)
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService { // TODO refactor

    private final CardRepository cardRepository;
    private final CardSetService cardSetService;
    private final CheckExistenceService checkExistenceService;
    private final CardMapper cardMapper;

    // todo: inject to controller as Principal or use Facade pattern
    private final JwtUser currentUser = (JwtUser) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

    private final String currentUserId = currentUser.getId();

//    private final JwtUser currentUser;
//
//    private final UUID currentUserId;

    // todo fixme: build failure if uncomment
//    {
//        currentUser = (JwtUser) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        currentUserId = currentUser.getId();
//    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllCards() {
        return cardRepository.findAlByUserId(currentUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Card findCardById(@NotNull String cardId) {
        Optional<Card> optionalCard = cardRepository.findByIdAndUserId(cardId, currentUserId);

        return optionalCard.
                orElseThrow(() ->
                        new EntityDoesNotExistException(String.format("Card with id=%s doesn't exist", cardId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllFavouriteCards() {
        return cardRepository.findAllByIsFavouriteAndUserId(true, currentUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllCardsBySetId(String cardSetId) {
        return cardRepository.findAllByCardSetIdAndUserId(cardSetId, currentUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllFavouriteCardsBySetId(String cardSetId) {
        return cardRepository.findAllByIsFavouriteAndCardSetIdAndUserId(true, cardSetId, currentUserId);
    }

    @Override
    public List<Card> createCards(List<CardCreateRequestDTO> cardCreateRequestList) {
        checkAssignedCardSetsExistence(cardCreateRequestList);

        List<Card> newCardsList = cardCreateRequestList.stream()
                .map(cardMapper::toEntity)
                .collect(toList());

        return cardRepository.saveAll(newCardsList);
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Card updateCard(CardUpdateRequestDTO updateDto) throws EntityDoesNotExistException {
        final String cardId = updateDto.getCardId();
        checkCardExistenceById(cardId);

        Card cardToUpdate = cardRepository.findByIdAndUserId(cardId, currentUserId).get();

        cardMapper.updateEntity(updateDto, cardToUpdate);

        return cardToUpdate;
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public List<Card> updateCards(List<CardUpdateRequestDTO> updateDTOList) { // TODO add asynchronous logic
        // ? maybe use Callable for the purpose below
        // todo: 1 Thread: sort updateDTOList by ID descending

        // todo: 2 Thread: checkCardsExistenceById +
        //  request database and receive Card collection (maybe user LinkedList)
        checkCardsExistenceById(updateDTOList);

        return null;
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

    @Override
    @Transactional
    public void deleteCardById(String cardId) throws EntityDoesNotExistException {
        checkCardExistenceById(cardId);

        cardRepository.deleteById(cardId);
    }

    @Override
    public void deleteCardByCardSetId(String cardSetId) throws EntityDoesNotExistException {
        checkCategoryExistenceById(cardSetId);

//        cardRepository.deleteCardByCardSetIdAndUserId(cardSetId, currentUserId); TODO
    }

    @Override
    public List<Card> updateCardsProficiencyLevel(List<UpdateCardProficiencyLevelDTO> updateProficiencyDTOList) {
        return null;
    }

    /**
     * Check that all CardSets assigned to a Card exist
     * If at least one CardSet doesn't exist exception is thrown
     *
     * @param cardRequestList - list from which IDs of CardSets will be extracted
     * @throws EntityDoesNotExistException if at least one CardSet doesn't exist the exception is thrown
     **/
    protected void checkAssignedCardSetsExistence(List<? extends CardRequest> cardRequestList) {
        Set<String> assignedCardSetIds = new HashSet<>();

        cardRequestList.stream()
                .map(CardRequest::getLinkedCardSetsIds)
                .forEach(assignedCardSetIds::addAll);

        boolean allCardSetExist = checkExistenceService.areCardSetByIdsExist(assignedCardSetIds);

        if (!allCardSetExist) {
            throw new EntityDoesNotExistException(String.format(
                    "%s with some of the following IDs %s doesnt exist",
                    CardSet.class.getName(),
                    assignedCardSetIds)); // TODO check if toString call is needed
        }

    }

    // TODO is needed?
    protected void checkCategoryExistenceById(@Nullable String cardSetId) throws EntityDoesNotExistException {
        if (cardSetId != null) {
            boolean isCategoryExists = cardSetService.existsById(cardSetId);
            if (isCategoryExists) {
                return;
            }
        }
        throw new EntityDoesNotExistException(String.format("CardSet with id:%d doesn't exist", cardSetId));
    }

    // TODO is needed?
    protected void checkCardExistenceById(@Nullable String cardId) throws EntityDoesNotExistException {
        if (cardId != null) {
            boolean isCardExists = cardRepository.existsByIdAndUserId(cardId, currentUserId);
            if (isCardExists) {
                return;
            }
        }
        throw new EntityDoesNotExistException(String.format("Card with id:%d doesn't exist", cardId));
    }

}
