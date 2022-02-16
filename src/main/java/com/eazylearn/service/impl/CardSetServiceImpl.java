package com.eazylearn.service.impl;

import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.entity.CardSet;
import com.eazylearn.exception.EntityAlreadyExistsException;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.mapper.CardSetMapper;
import com.eazylearn.repository.CardSetRepository;
import com.eazylearn.security.jwt.JwtAuthenticationFacadeImpl;
import com.eazylearn.service.CardService;
import com.eazylearn.service.CardSetService;
import com.eazylearn.service.CheckExistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CardSetServiceImpl implements CardSetService {

    private final CardSetRepository cardSetRepository;
    private final CardSetMapper cardSetMapper;
    private final CheckExistenceService checkExistenceService;
    private final CardService cardService;
    private final JwtAuthenticationFacadeImpl jwtAuthenticationFacade;

    @Override
    @Transactional(readOnly = true)
    public List<CardSet> findAllCardSets() {
        return cardSetRepository.findAllByUserId(jwtAuthenticationFacade.getJwtPrincipalId());
    }

    @Override
    public List<CardSet> findAllFavouriteCardSets() {
        return cardSetRepository.findAllByIsFavouriteAndUserId(true, jwtAuthenticationFacade.getJwtPrincipalId());
    }

    @Override
    @Transactional(readOnly = true)
    public CardSet findCardSetById(String cardSetId) {
        return cardSetRepository.findByIdAndUserId(cardSetId, jwtAuthenticationFacade.getJwtPrincipalId())
                .orElseThrow(() -> new EntityDoesNotExistException(String.format("CardSet with id = %s doesn't exist", cardSetId)));
    }

    @Override
    public CardSet createCardSet(CardSetCreateRequestDTO cardSetCreateRequestDTO) {
        final String newCardSetName = cardSetCreateRequestDTO.getName();
        final boolean isAlreadyExist = checkExistenceService.isCardSetByNameExist(newCardSetName, jwtAuthenticationFacade.getJwtPrincipalId());

        if (!isAlreadyExist) {
            CardSet newCardSet = cardSetMapper.toEntity(cardSetCreateRequestDTO);
            return cardSetRepository.save(newCardSet);
        } else {
            throw new EntityAlreadyExistsException(String.format("CardSet with name = %s already exists", newCardSetName));
        }
    }

    @Override
    public CardSet updateCardSetById(String cardSetId, CardSetUpdateRequestDTO updateDTO) {
        final CardSet cardSetToUpdate = cardSetRepository.findByIdAndUserId(cardSetId, jwtAuthenticationFacade.getJwtPrincipalId())
                .orElseThrow(() -> new EntityDoesNotExistException(String.format("CardSet with id = %s doesn't exist", cardSetId)));

        cardSetMapper.updateEntity(updateDTO, cardSetToUpdate);
        return cardSetToUpdate;
    }

    @Override
    public void deleteCardSetById(String cardSetId, boolean deleteAllLinkedCards) {
        if (deleteAllLinkedCards) {
            cardService.deleteCardsByCardSetId(cardSetId);
        }
        cardSetRepository.deleteById(cardSetId);
    }
}
