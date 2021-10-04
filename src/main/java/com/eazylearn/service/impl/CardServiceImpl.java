package com.eazylearn.service.impl;

import com.eazylearn.dto.request.card.CardCreateRequestDTO;
import com.eazylearn.dto.request.card.CardRequest;
import com.eazylearn.dto.request.card.CardUpdateRequestDTO;
import com.eazylearn.dto.request.card.UpdateCardProficiencyLevelDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CardMapper;
import com.eazylearn.repository.CardRepository;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.service.CardService;
import com.eazylearn.service.CardSetService;
import com.eazylearn.service.CheckExistenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
@Scope(value = SCOPE_SESSION, proxyMode = INTERFACES)
public class CardServiceImpl implements CardService { // TODO refactor

    private final CardRepository cardRepository;
    private final CardSetService cardSetService;
    private final CheckExistenceService checkExistenceService;
    private final CardMapper cardMapper;

    private final JwtUser currentUser;
    private final UUID currentUserId;

    {
        currentUser = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        currentUserId = currentUser.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllCards() {
        return cardRepository.findAlByUserId(currentUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Card findCardById(@NotNull UUID cardId) {
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
    public List<Card> findAllCardsBySetId(UUID cardSetId) {
        return cardRepository.findAllByCardSetIdAndUserId(cardSetId, currentUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAllFavouriteCardsBySetId(UUID cardSetId) {
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
    public Card updateCardById(UUID cardId, CardUpdateRequestDTO updateDto) throws EntityDoesNotExistException {
        checkCardExistenceById(cardId);

        Card updatedCard = cardRepository.findByIdAndUserId(cardId, currentUserId).get();

        cardMapper.updateEntity(updateDto, updatedCard);

        return cardMapper.toResponseDTO(updatedCard);
    }

    @Override
    public List<Card> updateCards(List<CardUpdateRequestDTO> updateDTOList) {
        return null;
    }

    @Override
    @Transactional
    public void deleteCardById(UUID cardId) throws EntityDoesNotExistException {
        checkCardExistenceById(cardId);

        cardRepository.deleteById(cardId);
    }

    @Override
    public void deleteCardByCardSetId(UUID cardSetId) throws EntityDoesNotExistException {
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
        Set<UUID> assignedCardSetIds = new HashSet<>();

        cardRequestList.stream()
                .map(CardRequest::getCardSetIds)
                .forEach(assignedCardSetIds::addAll);

        boolean allCardSetExist = checkExistenceService.areCardSetByIdsExist(assignedCardSetIds);

        if (!allCardSetExist) {
            throw new EntityDoesNotExistException(String.format(
                    "%s with some of the following IDs %s doesnt exist",
                    CardSet.class.getName(),
                    assignedCardSetIds.toString())); // TODO check if toString call is needed
        }

    }

    // TODO is needed?
    protected void checkCategoryExistenceById(@Nullable UUID cardSetId) throws EntityDoesNotExistException {
        if (cardSetId != null) {
            boolean isCategoryExists = cardSetService.existsById(cardSetId);
            if (isCategoryExists) {
                return;
            }
        }
        throw new EntityDoesNotExistException(String.format("CardSet with id:%d doesn't exist", cardSetId));
    }

    // TODO is needed?
    protected void checkCardExistenceById(@Nullable UUID cardId) throws EntityDoesNotExistException {
        if (cardId != null) {
            boolean isCardExists = cardRepository.existsByIdAndUserId(cardId, currentUserId);
            if (isCardExists) {
                return;
            }
        }
        throw new EntityDoesNotExistException(String.format("Card with id:%d doesn't exist", cardId));
    }

}
