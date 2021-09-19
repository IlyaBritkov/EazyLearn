package com.eazylearn.service.impl;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.dto.request.CardUpdateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.enums.TabType;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CardMapper;
import com.eazylearn.repository.CardRepository;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.service.CardService;
import com.eazylearn.service.CardSetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Double.compare;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
@Scope(value = SCOPE_SESSION, proxyMode = INTERFACES)
public class CardServiceImpl implements CardService { // TODO: refactor

    private final CardRepository cardRepository;
    private final CardSetService cardSetService;
    private final CardMapper cardMapper;

    private final JwtUser currentUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    private final UUID currentUserId = currentUser.getId();

    @Transactional(readOnly = true)
    public List<CardResponseDTO> findAllCardsByTabAndCardSetId(@Nullable String tab, @Nullable UUID categoryId) throws EntityDoesNotExistException {
        if (tab == null) {
            tab = "HOME";
        }

        checkTabExistenceByTabName(tab);

        TabType tabType = TabType.valueOf(tab.toUpperCase());
        log.trace("tabType = {}", tabType);

        List<CardResponseDTO> allCardsList = null;
        switch (tabType) {
            case HOME:
                allCardsList = cardRepository.findAllByUserId(currentUserId)
                        .stream()
                        .sorted(comparingDouble(Card::getProficiencyLevel))
                        .map(cardMapper::toResponseDTO)
                        .collect(toList());
                break;
            case CATEGORY:
                checkCategoryExistenceById(categoryId);
                log.trace("categoryId = {}", categoryId);
//                allCardsList = cardRepository.findAllByUserIdAndCardSetId(currentUserId, categoryId)
//                        .stream()
//                        .map(cardMapper::toResponseDTO)
//                        .collect(toList()); TODO
                break;
            case RECENT:
                allCardsList = cardRepository.findAllByUserId(currentUserId)
                        .stream()
                        .sorted((card1, card2) -> compare(card2.getCreatedTime(), card1.getCreatedTime()))
                        .map(cardMapper::toResponseDTO)
                        .collect(toList());
                break;
        }

        log.trace("allCardsList = {}", allCardsList);
        return allCardsList;
    }

    public List<Card> findAllCardsEntityByCardSetId(UUID categoryId) throws EntityDoesNotExistException {
        checkCategoryExistenceById(categoryId);

//        return cardRepository.findAllByUserIdAndCardSetId(currentUserId, categoryId); TODO
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponseDTO findCardById(@NotNull UUID cardId) throws EntityDoesNotExistException {
        Optional<Card> optionalCard = cardRepository.findByIdAndUserId(cardId, currentUserId);
        Card card = optionalCard.
                orElseThrow(() -> new EntityDoesNotExistException(String.format("Card with id=%d doesn't exist", cardId)));
        return cardMapper.toResponseDTO(card);
    }

    @Override
    @Transactional
    public CardResponseDTO createCard(CardCreateRequestDTO cardCreateRequestDTO) throws EntityDoesNotExistException {
        UUID cardSetId = cardCreateRequestDTO.getCardSetId();

        if (cardSetId != null) {
            checkCategoryExistenceById(cardSetId);
        }

        Card card = cardMapper.toEntity(cardCreateRequestDTO);

        Card savedCard = cardRepository.save(card);
        return cardMapper.toResponseDTO(savedCard);
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public CardResponseDTO updateCardById(UUID cardId, CardUpdateRequestDTO updateDto) throws EntityDoesNotExistException {
        checkCardExistenceById(cardId);

        Card updatedCard = cardRepository.findByIdAndUserId(cardId, currentUserId).get();

        cardMapper.updateEntity(updateDto, updatedCard);

        return cardMapper.toResponseDTO(updatedCard);
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

    protected void checkTabExistenceByTabName(@NotNull String tab) throws EntityDoesNotExistException {
        final String finalTab = tab;
        if (Arrays.stream(TabType.values())
                .noneMatch(tabType -> tabType.toString()
                        .equalsIgnoreCase(finalTab))) {
            throw new EntityDoesNotExistException(String.format("Tab with name:%s doesn't exist", tab));
        }
    }

    protected void checkCategoryExistenceById(@Nullable UUID cardSetId) throws EntityDoesNotExistException {
        if (cardSetId != null) {
            boolean isCategoryExists = cardSetService.existsById(cardSetId);
            if (isCategoryExists) {
                return;
            }
        }
        throw new EntityDoesNotExistException(String.format("CardSet with id:%d doesn't exist", cardSetId));
    }

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
