package com.eazylearn.service.impl;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.enums.TabType;
import com.eazylearn.exception.CategoryDoesNotExistException;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CardMapper;
import com.eazylearn.repository.CardRepository;
import com.eazylearn.security.jwt.JwtUser;
import com.eazylearn.service.CardService;
import com.eazylearn.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static java.lang.Double.compare;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
@Scope(value = SCOPE_SESSION, proxyMode = INTERFACES)
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CategoryService categoryService;
    private final CardMapper cardMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtUser currentUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    private final Long currentUserId = currentUser.getId();

    @Override
    @Transactional(readOnly = true)
    public List<CardResponseDTO> findAllCardsByTabAndCategoryId(@Nullable String tab, @Nullable Long categoryId) throws EntityDoesNotExistException, CategoryDoesNotExistException {
        if (tab == null) {
            tab = "HOME";
        }

        checkTabExistenceByTabName(tab);

        TabType tabType = TabType.valueOf(tab.toUpperCase());
        log.trace("tabType = {}", tabType);

        checkCategoryExistenceById(categoryId);
        log.trace("categoryId = {}", categoryId);



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
                allCardsList = cardRepository.findAllByUserIdAndCategoryId(currentUserId, categoryId)
                        .stream()
                        .map(cardMapper::toResponseDTO)
                        .collect(toList());
                break;
            case RECENT:
                allCardsList = cardRepository.findAllByUserId(currentUserId)
                        .stream()
                        .sorted((card1, card2) -> compare(card2.getTimeAddition(), card1.getTimeAddition()))
                        .map(cardMapper::toResponseDTO)
                        .collect(toList());
                break;
        }

        log.trace("allCardsList = {}", allCardsList);
        return allCardsList;
    }

    @Override
    @Transactional
    public CardResponseDTO createCard(CardCreateRequestDTO cardCreateRequestDTO) {
        Card card = cardMapper.toEntity(cardCreateRequestDTO);

        Card savedCard = cardRepository.save(card);
        return cardMapper.toResponseDTO(savedCard);
    }

//    @Override
//    @NotNull
//    @Transactional(readOnly = true)
//    public CardResponseDTO findCardById(@NotNull Long cardId) {
//        Optional<Card> optionalCard = cardRepository.findById(cardId);
//        Card card = optionalCard.get(); // todo
//        return cardMapper.toDto(card);
//    }
//
//    @Override
//    @Transactional
//    public CardResponseDTO updateCard(@NotNull CardResponseDTO cardDto) {
//        Card card = cardMapper.toEntity(cardDto);
//        return cardMapper.toDto(cardRepository.save(card));
//    }
//
//    @Override
//    @Transactional
//    public void deleteCardById(@NotNull Long cardId) {
//        log.debug("Deleted cardDto's id = {}", cardId);
//        cardRepository.deleteById(cardId);
//    }

    @Override
    public CardResponseDTO findCardById(Long cardId) {
        return null;
    }

    @Override
    public CardResponseDTO updateCard(CardResponseDTO card) {
        return null;
    }

    @Override
    public void deleteCardById(Long cardId) {

    }

    protected void checkTabExistenceByTabName(@NotNull String tab) throws EntityDoesNotExistException {
        final String finalTab = tab;
        if (Arrays.stream(TabType.values())
                .noneMatch(tabType -> tabType.toString()
                        .equalsIgnoreCase(finalTab))) {
            throw new EntityDoesNotExistException(String.format("Tab with name:%s doesn't exist", tab));
        }
    }

    protected void checkCategoryExistenceById(@Nullable Long categoryId) throws EntityDoesNotExistException {
        if (categoryId != null) {
            boolean isCategoryExists = categoryService.existsByIdAndUserId(categoryId, currentUserId);
            if (!isCategoryExists) {
                throw new EntityDoesNotExistException(String.format("Category with id:%d doesn't exist", categoryId));
            }
        }
    }
}
