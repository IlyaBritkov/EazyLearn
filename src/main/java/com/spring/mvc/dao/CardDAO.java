package com.spring.mvc.dao;


import com.spring.mvc.entity.CardEntity;

import java.util.List;

public interface CardDAO {
    List<CardEntity> getAllCards(Long userId, Long categoryId);

    void createCard(Long userId, CardEntity card);

    CardEntity findCardById(Long userId, Long cardId);

    void updateCard(Long userId, CardEntity card);

    void deleteCardById(Long userId, Long cardId);
}
