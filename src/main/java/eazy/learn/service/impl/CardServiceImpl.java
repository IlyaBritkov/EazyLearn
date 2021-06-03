package eazy.learn.service.impl;

import eazy.learn.dto.request.CardCreateRequestDTO;
import eazy.learn.dto.response.CardResponseDTO;
import eazy.learn.entity.Card;
import eazy.learn.enums.TabType;
import eazy.learn.exception.CategoryDoesNotExistException;
import eazy.learn.exception.TabDoesNotExistException;
import eazy.learn.mapper.CardMapper;
import eazy.learn.repository.CardRepository;
import eazy.learn.service.CardService;
import eazy.learn.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    private final CategoryService categoryService;

    private final CardMapper cardMapper;

    // TODO: replace it by Spring Security Service
    private final Long userId = 1L;

    @Override
    @Transactional(readOnly = true)
    public List<CardResponseDTO> findAllCardsByTabAndCategoryId(@Nullable String tab, @Nullable Long categoryId) throws TabDoesNotExistException, CategoryDoesNotExistException {
        if (tab == null) {
            tab = "HOME";
        }

        final String finalTab = tab;
        if (Arrays.stream(TabType.values())
                .noneMatch(tabType -> tabType.toString()
                        .equalsIgnoreCase(finalTab))) {
            throw new TabDoesNotExistException(String.format("Tab with name:%s doesn't exist", tab));
        }

        TabType tabType = TabType.valueOf(tab.toUpperCase());
        log.trace("TabType = {}", tabType);
        System.out.println("tabType = " + tabType);

        List<CardResponseDTO> allCardsList = null;
        switch (tabType) {
            case HOME:
                allCardsList = cardRepository.findAllByUserIdAndCategoryId(userId, null)
                        .stream()
                        .sorted(comparingDouble(Card::getProficiencyLevel))
                        .map(cardMapper::toResponseDTO)
                        .collect(toList());
                break;
            case CATEGORY:
                if ((categoryId == null || categoryId < 0) &&
                        !categoryService.existsByCategoryId(categoryId)) {
                    throw new CategoryDoesNotExistException(String.format("Category with id:%d doesn't exist", categoryId));
                }
                allCardsList = cardRepository.findAllByUserIdAndCategoryId(userId, categoryId)
                        .stream()
                        .map(cardMapper::toResponseDTO)
                        .collect(toList());
                break;
            case RECENT:
                allCardsList = cardRepository.findAllByUserIdAndCategoryId(userId, null)
                        .stream()
                        .sorted((card1, card2) -> card2.getTimeAddition().compareTo(card1.getTimeAddition()))
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
        Card card = cardMapper.createDTOtoEntity(cardCreateRequestDTO);
        card.setUserId(userId);
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
}
