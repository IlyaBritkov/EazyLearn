package eazy.learn.service;

import eazy.learn.dto.CardDto;

import java.util.List;

public interface CardService {
    List<CardDto> findAllCardsByTabAndCategoryId(String tab, Long categoryId);

    void createCard(CardDto card);

    CardDto findCardById(Long cardId);

    CardDto updateCard(CardDto card);

    void deleteCardById(Long cardId);
}
