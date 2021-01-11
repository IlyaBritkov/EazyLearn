package com.spring.mvc.dao;


import com.spring.mvc.entity.CardEntity;

import java.util.List;

public interface CardDAO {
    List<CardEntity> getAllCards(Long userId, Long categoryId);
}
