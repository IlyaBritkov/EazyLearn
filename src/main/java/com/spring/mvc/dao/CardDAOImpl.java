package com.spring.mvc.dao;

import com.spring.mvc.entity.CardEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CardDAOImpl implements CardDAO {

    private final SessionFactory sessionFactory;


    @Autowired
    public CardDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<CardEntity> getAllCards(Long userId, Long categoryId) {
        Session session = sessionFactory.getCurrentSession();


        if (categoryId == null) {
            return session.createQuery("FROM CardEntity WHERE userId=null", CardEntity.class).getResultList();
        } else {
            return session.createQuery("FROM CardEntity WHERE userId = userId AND categoryId = categoryId").getResultList();
        }
    }

    @Override
    public void createCard(Long userId, CardEntity card) {
        Session session = sessionFactory.getCurrentSession();

        session.save(card);
    }

    @Override
    public CardEntity findCardById(Long userId, Long cardId) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(CardEntity.class, cardId);
    }

    @Override
    public void updateCard(Long userId, CardEntity card) {
        Session session = sessionFactory.getCurrentSession();
        session.update(card);
    }

    @Override
    public void deleteCardById(Long userId, Long cardId) {
        Session session = sessionFactory.getCurrentSession();

        Query<CardEntity> query = session.createQuery("DELETE CardEntity WHERE id =: cardId");
        query.setParameter("cardId", cardId);
        query.executeUpdate();
    }


}


























