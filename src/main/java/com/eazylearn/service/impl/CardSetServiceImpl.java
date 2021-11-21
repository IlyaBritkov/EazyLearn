package com.eazylearn.service.impl;

import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.dto.response.CardSetResponseDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.entity.CardSet;
import com.eazylearn.exception.EntityAlreadyExistsException;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CardSetMapper;
import com.eazylearn.repository.CardSetRepository;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.service.CardService;
import com.eazylearn.service.CardSetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
@Scope(value = SCOPE_SESSION, proxyMode = INTERFACES)
public class CardSetServiceImpl implements CardSetService {

    private final CardSetRepository cardSetRepository;
    private final CardSetMapper cardSetMapper;

    private final CardService cardService;

    private final JwtUser currentUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private final UUID currentUserId = currentUser.getId();

    @Override
    @Transactional(readOnly = true)
    public List<CardSetResponseDTO> findAllCategories() {

        List<CardSet> allCategories = cardSetRepository.findAllByUserId(currentUserId);

        return allCategories.stream()
                .map(cardSetMapper::toResponseDTO)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public CardSetResponseDTO findCategoryById(UUID categoryId) throws EntityDoesNotExistException {

        checkCategoryExistenceById(categoryId);

        CardSet cardSetById = cardSetRepository.findByIdAndUserId(categoryId, currentUserId).get();
        return cardSetMapper.toResponseDTO(cardSetById);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID categoryId) {
        return cardSetRepository.existsByIdAndUserId(categoryId, currentUserId);
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public CardSetResponseDTO createCategory(CardSetCreateRequestDTO cardSetCreateRequestDTO)
            throws EntityAlreadyExistsException {

        String newCategoryName = cardSetCreateRequestDTO.getName();

        if (cardSetRepository.existsByNameAndUserId(newCategoryName, currentUserId)) { // todo
            throw new EntityAlreadyExistsException(String.format("CardSet with name = %s already exists",
                    newCategoryName));
        } else {
            CardSet newCardSet = cardSetMapper.toEntity(cardSetCreateRequestDTO);
            CardSet persistedCardSet = cardSetRepository.save(newCardSet);

            return cardSetMapper.toResponseDTO(persistedCardSet);
        }
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public CardSetResponseDTO updateCategoryById(UUID categoryId, CardSetUpdateRequestDTO updateDTO)
            throws EntityDoesNotExistException {

        checkCategoryExistenceById(categoryId);

        CardSet updatedCardSet = cardSetRepository.findByIdAndUserId(categoryId, currentUserId).get();
        cardSetMapper.updateEntity(updateDTO, updatedCardSet);

        return cardSetMapper.toResponseDTO(updatedCardSet);
    }

    @Override
    @Transactional(isolation = SERIALIZABLE)
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    // todo refactor it
    public void deleteCategoryById(UUID setId, boolean isDeleteAllCardsInCategory)
            throws EntityDoesNotExistException {
        checkCategoryExistenceById(setId);

        CardSet cardSet = cardSetRepository.findByIdAndUserId(setId, currentUserId).get();

        if (isDeleteAllCardsInCategory) {
            cardService.deleteCardByCardSetId(setId);
        } else {
            List<Card> allCardsByCategory = cardService.findAllCardsBySetId(setId);
//            allCardsByCategory
//                    .forEach(card -> card.setCardSetId(null)); todo
        }

        cardSetRepository.delete(cardSet);
    }

    protected void checkCategoryExistenceById(@Nullable UUID categoryId) throws EntityDoesNotExistException {
        if (categoryId != null) {
            boolean isCategoryExists = existsById(categoryId);
            if (isCategoryExists) {
                return;
            }
        }
        throw new EntityDoesNotExistException(String.format("CardSet with id:%d doesn't exist", categoryId));
    }
}
