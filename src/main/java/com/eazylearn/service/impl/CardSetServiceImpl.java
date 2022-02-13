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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Service
@Transactional
@Scope(value = SCOPE_REQUEST, proxyMode = INTERFACES) // todo: extract JWT from SpringContext
@RequiredArgsConstructor
@Slf4j
public class CardSetServiceImpl implements CardSetService {

    private final CardSetRepository cardSetRepository;
    private final CardSetMapper cardSetMapper;

    private final CardService cardService;

    private final JwtUser currentUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private final String currentUserId = currentUser.getId();

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
    public CardSetResponseDTO findCategoryById(String categoryId) throws EntityDoesNotExistException {

        checkCardSetExistenceById(categoryId);

        CardSet cardSetById = cardSetRepository.findByIdAndUserId(categoryId, currentUserId).get();
        return cardSetMapper.toResponseDTO(cardSetById);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistById(String categoryId) {
        return cardSetRepository.existsByIdAndUserId(categoryId, currentUserId);
    }

    @Override
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
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public CardSetResponseDTO updateCategoryById(String categoryId, CardSetUpdateRequestDTO updateDTO)
            throws EntityDoesNotExistException {

        checkCardSetExistenceById(categoryId);

        CardSet updatedCardSet = cardSetRepository.findByIdAndUserId(categoryId, currentUserId).get();
        cardSetMapper.updateEntity(updateDTO, updatedCardSet);

        return cardSetMapper.toResponseDTO(updatedCardSet);
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void deleteCardSetById(String setId, boolean isDeleteAllCardsInCategory)
            throws EntityDoesNotExistException {
        checkCardSetExistenceById(setId);

        CardSet cardSet = cardSetRepository.findByIdAndUserId(setId, currentUserId).get();

        if (isDeleteAllCardsInCategory) {
            cardService.deleteCardsByCardSetId(setId);
        } else {
            List<Card> allCardsByCategory = cardService.findAllCardsBySetId(setId);
//            allCardsByCategory
//                    .forEach(card -> card.setCardSetId(null)); todo
        }

        cardSetRepository.delete(cardSet);
    }

    private void checkCardSetExistenceById(String categoryId) throws EntityDoesNotExistException {
        boolean isCategoryExists = isExistById(categoryId);
        if (isCategoryExists) {
            throw new EntityDoesNotExistException(String.format("CardSet with id:%d doesn't exist", categoryId));
        }
    }
}
