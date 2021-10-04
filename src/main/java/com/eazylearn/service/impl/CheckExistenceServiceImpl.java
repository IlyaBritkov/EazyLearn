package com.eazylearn.service.impl;

import com.eazylearn.repository.CardSetRepository;
import com.eazylearn.service.CheckExistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor

@Service
public class CheckExistenceServiceImpl implements CheckExistenceService {

    private final CardSetRepository cardSetRepository;

    @Override
    public boolean areCardSetByIdsExist(Collection<UUID> cardSetIds) {
        long countByIds = cardSetRepository.countByIds(cardSetIds);

        return cardSetIds.size() == countByIds;
    }

}
