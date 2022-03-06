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
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

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
        Optional<Card> optionalCard = cardRepository.findById(cardId);

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
        return cardRepository.findAllByCardSetId(cardSetId);
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

        final List<CardSet> linkedCardSets = cardSetRepository.findAllById(linkedCardSetsIds);
        checkExistenceService.checkCardSetsExistence(linkedCardSetsIds, linkedCardSets);

        final List<Card> newCardsList = cardCreateRequestList.stream()
                .map(cardDto -> {
                    final Card newCard = cardMapper.toEntity(cardDto);
                    // noinspection because existence are checked above
                    linkedCardSets.stream()
                            .filter(set -> cardDto.getLinkedCardSetsIds().contains(set.getId()))
                            .findAny()
                            .ifPresent(newCard::addLinkedCardSet);

                    return newCard;
                })
                .collect(toList());

        return cardRepository.saveAll(newCardsList);
    }

    @Override
    public Card createCard(CardCreateRequestDTO createDto) {
        checkExistenceService.checkCardsExistenceByIds(createDto.getLinkedCardSetsIds());

        return cardRepository.save(cardMapper.toEntity(createDto));
    }

    @Override
    public Card updateCard(CardUpdateRequestDTO updateDto) throws EntityDoesNotExistException {
        final String cardId = updateDto.getCardId();

        final Card cardToUpdate = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityDoesNotExistException(String.format("Card with id:%s doesn't exist", cardId)));

        final List<String> linkedCardSetsIds = updateDto.getLinkedCardSetsIds();
        List<CardSet> linkedCardSets = cardToUpdate.getLinkedCardSets();
        if (!isNull(linkedCardSetsIds)) {
            linkedCardSets = cardSetRepository.findAllById(linkedCardSetsIds);
            checkExistenceService.checkCardSetsExistence(linkedCardSetsIds, linkedCardSets);
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

        final List<Card> cards = cardRepository.findAllById(cardsIds);

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
        final List<Card> cardsByCardSetId = cardRepository.findAllByCardSetId(cardSetId);

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

        final List<Card> cardsToUpdate = cardRepository.findAllById(cardsIds);

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
}
