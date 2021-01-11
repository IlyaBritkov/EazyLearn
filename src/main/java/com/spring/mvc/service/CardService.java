package com.spring.mvc.service;

import com.spring.mvc.entity.CardEntity;

import java.util.List;

public interface CardService {
    List<CardEntity> getAllCards(String tab, Long categoryId);
}
