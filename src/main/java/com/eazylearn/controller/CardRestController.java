package com.eazylearn.controller;

import com.eazylearn.dto.request.card.CardCreateRequestDTO;
import com.eazylearn.dto.request.card.CardUpdateRequestDTO;
import com.eazylearn.dto.request.card.UpdateCardProficiencyLevelDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.entity.Card;
import com.eazylearn.mapper.CardMapper;
import com.eazylearn.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.eazylearn.util.Constants.CARDS_ENDPOINT_PATH;
import static com.eazylearn.util.Convertor.uuidToString;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RequiredArgsConstructor

@RestController
@RequestMapping(value = CARDS_ENDPOINT_PATH)
public class CardRestController { // todo: add pagination

    private final CardService cardService;
    private final CardMapper cardMapper;

    /**
     * Returns ALL cards OR cards ASSOCIATED with <b>cardSetId</b> for authorized user.
     *
     * @param cardSetId optional request parameter.
     *                  If passed only cards that belong to that cardSet will be returned.
     *                  Otherwise, all cards will be returned.
     **/
    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> findAllCards(
            @RequestParam(value = "cardSetId", required = false) UUID cardSetId) {

        List<Card> allCards;
        if (cardSetId == null) {
            allCards = cardService.findAllCards();
        } else {
            allCards = cardService.findAllCardsBySetId(uuidToString(cardSetId));
        }

        return ok(cardMapper.mapCardListToCardResponseDTOList(allCards));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> findCardById(@PathVariable("id") UUID cardId) {
        final Card card = cardService.findCardById(uuidToString(cardId));

        return ok(cardMapper.toResponseDTO(card));
    }

    /**
     * Returns ALL cards or ONLY cards associated with <b>cardSetId</b> for which <b>{@link Card#isFavourite()} = true</b>
     * and created by authorized user.
     *
     * @param cardSetId optional request parameter.
     *                  If passed only favourite cards that belong to cardSet will be returned.
     *                  Otherwise, will be returned all favourite cards.
     **/
    @GetMapping("/favourite")
    public ResponseEntity<List<CardResponseDTO>> findAllFavouriteCards(
            @RequestParam(value = "cardSetId", required = false) UUID cardSetId) {

        List<Card> allCards;
        if (cardSetId == null) {
            allCards = cardService.findAllFavouriteCards();
        } else {
            allCards = cardService.findAllFavouriteCardsBySetId(uuidToString(cardSetId));
        }

        return ok(cardMapper.mapCardListToCardResponseDTOList(allCards));
    }

    @PostMapping
    public ResponseEntity<List<CardResponseDTO>> createCards(@RequestBody List<CardCreateRequestDTO> cardCreateDTOList) {
        final List<Card> cardList = cardService.createCards(cardCreateDTOList);

        return ok(cardMapper.mapCardListToCardResponseDTOList(cardList));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardResponseDTO> updateCardById(@PathVariable("id") UUID cardId,
                                                          @RequestBody CardUpdateRequestDTO updateDto) {
        updateDto.setCardId(uuidToString(cardId));
        final Card card = cardService.updateCard(updateDto);

        return ok(cardMapper.toResponseDTO(card));
    }

    @PatchMapping
    public ResponseEntity<List<CardResponseDTO>> updateCards(@RequestBody List<CardUpdateRequestDTO> updateDTOList) {
        final List<Card> cardList = cardService.updateCards(updateDTOList);

        return ok(cardMapper.mapCardListToCardResponseDTOList(cardList));
    }

    /**
     * Updates {@link Card#getProficiencyLevel()} of cards by cardId and
     * new proficiencyLevel passed in {@link UpdateCardProficiencyLevelDTO}
     **/
    @PatchMapping(value = "/proficiencyLevel")
    public ResponseEntity<List<CardResponseDTO>> updateCardsProficiencyLevel(@RequestBody List<UpdateCardProficiencyLevelDTO> updateProficiencyDTOList) {
        final List<Card> cardList = cardService.updateCardsProficiencyLevel(updateProficiencyDTOList);

        return ok(cardMapper.mapCardListToCardResponseDTOList(cardList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCardById(@PathVariable("id") UUID cardId) {
        cardService.deleteCardById(uuidToString(cardId));

        return noContent().build();
    }

}
