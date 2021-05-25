package eazy.learn.service.impl;

import eazy.learn.dto.CardDto;
import eazy.learn.entity.Card;
import eazy.learn.enums.TabType;
import eazy.learn.mapper.CardMapper;
import eazy.learn.repository.CardRepository;
import eazy.learn.service.CardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    // TODO: replace it by Spring Security Service
    private final Long userId = 1L;

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<CardDto> findAllCardsByTabAndCategoryId(@NotNull String tab, Long categoryId) {
        TabType tabType = TabType.valueOf(tab.toUpperCase());
        log.debug("tabType = {}", tabType);

        List<CardDto> allCardsList = null;
        switch (tabType) {
            case HOME:
                allCardsList = cardRepository.findAllByUserIdAndCategoryId(userId, null).stream().
                        sorted(Comparator.comparingDouble(Card::getProficiencyLevel)).
                        map(cardMapper::toDto).collect(toList());
                break;
            case CATEGORY:
                allCardsList = cardRepository.findAllByUserIdAndCategoryId(userId, categoryId).stream().map(cardMapper::toDto).collect(toList());
                break;
            case RECENT:
                allCardsList = cardRepository.findAllByUserIdAndCategoryId(userId, null).stream()
                        .sorted((card1, card2) -> card2.getTimeAddition().compareTo(card1.getTimeAddition()))
                        .map(cardMapper::toDto)
                        .collect(toList());
                break;
        }

        log.debug("allCardsList = {}", allCardsList);
        return allCardsList;
    }

    @Override
    @Transactional
    public void createCard(@NotNull CardDto cardDto) {
        cardDto.setUserId(userId);
        Card card = cardMapper.toEntity(cardDto);
        cardRepository.save(card);
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public CardDto findCardById(@NotNull Long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        Card card = optionalCard.get(); // todo
        return cardMapper.toDto(card);
    }

    @Override
    @Transactional
    public CardDto updateCard(@NotNull CardDto cardDto) {
        Card card = cardMapper.toEntity(cardDto);
        return cardMapper.toDto(cardRepository.save(card));
    }

    @Override
    @Transactional
    public void deleteCardById(@NotNull Long cardId) {
        log.debug("Deleted cardDto's id = {}", cardId);
        cardRepository.deleteById(cardId);
    }
}
