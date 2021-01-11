package com.spring.mvc.service;

import com.spring.mvc.entity.CardEntity;

import java.util.List;

public interface CardService {
    List<CardEntity> getAllCards(String tab, Long categoryId);

    void createCard(CardEntity card);

    CardEntity findCardById(Long cardId);

    void updateCard(CardEntity card);

    void deleteCardById(Long cardId);
}
