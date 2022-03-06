package com.eazylearn.service.impl;

import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.dto.request.cardset.NestedCardCreateDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;
import com.eazylearn.exception.EntityAlreadyExistsException;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CardMapper;
import com.eazylearn.mapper.CardSetMapper;
import com.eazylearn.repository.CardRepository;
import com.eazylearn.repository.CardSetRepository;
import com.eazylearn.security.jwt.JwtAuthenticationFacadeImpl;
import com.eazylearn.service.CardService;
import com.eazylearn.service.CardSetService;
import com.eazylearn.service.CheckExistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CardSetServiceImpl implements CardSetService {

    private final CardService cardService;
    private final CheckExistenceService checkExistenceService;
    private final JwtAuthenticationFacadeImpl jwtAuthenticationFacade;
    private final CardSetMapper cardSetMapper;
    private final CardMapper cardMapper;
    private final CardSetRepository cardSetRepository;
    private final CardRepository cardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CardSet> findAllCardSets() {
        return cardSetRepository.findAllByUserId(jwtAuthenticationFacade.getJwtPrincipalId());
    }

    @Override
    public List<CardSet> findAllFavouriteCardSets() {
        return cardSetRepository.findAllByIsFavouriteAndUserId(true, jwtAuthenticationFacade.getJwtPrincipalId());
    }

    @Override
    @Transactional(readOnly = true)
    public CardSet findCardSetById(String cardSetId) {
        return cardSetRepository.findByIdAndUserId(cardSetId, jwtAuthenticationFacade.getJwtPrincipalId())
                .orElseThrow(() -> new EntityDoesNotExistException(String.format("CardSet with id = %s doesn't exist", cardSetId)));
    }

    @Override
    public CardSet createCardSet(CardSetCreateRequestDTO cardSetCreateRequestDTO) {
        final String newCardSetName = cardSetCreateRequestDTO.getName();
        checkCardSetExistence(newCardSetName);

        final CardSet newCardSet = cardSetMapper.toEntity(cardSetCreateRequestDTO);

        // linked cards by IDs from DTO
        final List<String> linkedCardsIds = cardSetCreateRequestDTO.getLinkedCardsIds();
        final List<Card> existingCards = cardRepository.findAllById(linkedCardsIds);
        checkExistenceService.checkCardsExistence(linkedCardsIds, existingCards);

        // linked nested cards from DTO
        final List<NestedCardCreateDTO> linkedNewCardDTOs = cardSetCreateRequestDTO.getLinkedNewCards();
        final List<Card> nestedCards = linkedNewCardDTOs.stream()
                .map(cardMapper::toEntity)
                .collect(toList());

        existingCards.addAll(nestedCards);
        newCardSet.setLinkedCards(existingCards);

        existingCards.forEach(card -> card.addLinkedCardSet(newCardSet));

        return cardSetRepository.save(newCardSet);
    }

    @Override
    public CardSet updateCardSetById(String cardSetId, CardSetUpdateRequestDTO updateDTO) {
        final CardSet cardSetToUpdate = cardSetRepository.findByIdAndUserId(cardSetId, jwtAuthenticationFacade.getJwtPrincipalId())
                .orElseThrow(() -> new EntityDoesNotExistException(String.format("CardSet with id = %s doesn't exist", cardSetId)));

        cardSetMapper.updateEntity(updateDTO, cardSetToUpdate);

        final List<String> linkedCardsIds = updateDTO.getLinkedCardsIds();
        if (!isNull(linkedCardsIds)) {
            cardSetToUpdate.setLinkedCards(cardRepository.findAllById(linkedCardsIds));
        }

        final List<NestedCardCreateDTO> linkedNewCards = updateDTO.getLinkedNewCards();
        if (!isNull(linkedNewCards)) {
            final List<Card> newCards = linkedNewCards.stream()
                    .map(cardMapper::toEntity)
                    .collect(toList());
            newCards.forEach(card -> card.getLinkedCardSets().add(cardSetToUpdate));

            cardSetToUpdate.getLinkedCards().addAll(newCards);
        }

        return cardSetRepository.save(cardSetToUpdate);
    }

    @Override
    public void deleteCardSetById(String cardSetId, boolean deleteAllLinkedCards) {
        if (deleteAllLinkedCards) {
            cardService.deleteCardsByCardSetId(cardSetId);
        } else {
            // remove associations between Cards and CardSet
            final List<Card> allCardsBySetId = cardService.findAllCardsBySetId(cardSetId);
            allCardsBySetId.forEach(card -> card.getLinkedCardSets().removeIf(cardSet -> Objects.equals(cardSet.getId(), cardSetId)));
        }
        cardSetRepository.deleteById(cardSetId);
    }

    private void checkCardSetExistence(String newCardSetName) {
        final boolean isAlreadyExist = checkExistenceService.isCardSetByNameExist(newCardSetName, jwtAuthenticationFacade.getJwtPrincipalId());

        if (isAlreadyExist) {
            throw new EntityAlreadyExistsException(String.format("CardSet with name = %s already exists", newCardSetName));
        }
    }
}
