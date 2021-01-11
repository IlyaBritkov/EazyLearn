package com.spring.mvc.service;

import com.spring.mvc.dao.CardDAO;
import com.spring.mvc.entity.CardEntity;
import com.spring.mvc.tabs.TabsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;

    // TODO: replace it by Spring Securi2ty Service
    private final Long userId = null;

    @Autowired
    public CardServiceImpl(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    @Override
    @Transactional
    public List<CardEntity> getAllCards(String tab, Long categoryId) {
        TabsEnum tabType = TabsEnum.valueOf(tab.toUpperCase());
        List<CardEntity> allCardsList;
        switch (tabType) {
            case HOME:
                allCardsList = cardDAO.getAllCards(userId, null);
                allCardsList.sort(Comparator.comparingDouble(CardEntity::getProficiencyLevel));
                return allCardsList;
            case CATEGORY:
                return cardDAO.getAllCards(userId, categoryId);
            case RECENT:
                allCardsList = cardDAO.getAllCards(userId, null);
                // TODO: add sorting by data adding
                return allCardsList;
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public void createCard(CardEntity card) {
        cardDAO.createCard(userId, card);
    }

    @Override
    @Transactional
    public CardEntity findCardById(Long cardId) {
        return cardDAO.findCardById(userId, cardId);
    }

    @Override
    @Transactional
    public void updateCard(CardEntity card) {
        cardDAO.updateCard(userId, card);
    }

    @Override
    @Transactional
    public void deleteCardById(Long cardId) {
        cardDAO.deleteCardById(userId, cardId);
    }

}
