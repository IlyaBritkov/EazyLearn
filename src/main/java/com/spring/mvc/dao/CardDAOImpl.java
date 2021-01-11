package com.spring.mvc.dao;

import com.spring.mvc.entity.CardEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
}


























