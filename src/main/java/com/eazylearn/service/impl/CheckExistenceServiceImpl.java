package com.eazylearn.service.impl;

import com.eazylearn.repository.CardRepository;
import com.eazylearn.repository.CardSetRepository;
import com.eazylearn.service.CheckExistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor

@Service
public class CheckExistenceServiceImpl implements CheckExistenceService {

    private final CardSetRepository cardSetRepository;
    private final CardRepository cardRepository;

    @Override
    public boolean areCardSetByIdsExist(Collection<String> cardSetIds) {
        long countByIds = cardSetRepository.countByIds(cardSetIds);

        return cardSetIds.size() == countByIds;
    }

    @Override
    public boolean areCardsByIdsExist(Collection<String> cardIds) {
        return cardIds.size() == cardRepository.countByIdIn(cardIds);
    }

}
