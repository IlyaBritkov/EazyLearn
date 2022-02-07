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

import static com.eazylearn.util.Convertor.uuidToString;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RequiredArgsConstructor

@RestController
@RequestMapping(value = "/api/v1/cards")
public class CardRestController {

    private final CardService cardService;
    private final CardMapper cardMapper;

    /**
     * Returns all cards created by authorized user
     * or only cards associated with cardSetId.
     *
     * @param cardSetId - optional request parameter.
     *                  If passed only cards that belong to cardSet will be returned.
     *                  Otherwise, will be returned all cards.
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

        List<CardResponseDTO> cardResponseDTOList = cardMapper.mapCardListToCardResponseDTOList(allCards);
        return ok(cardResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> findCardById(@PathVariable("id") UUID cardId) {
        Card card = cardService.findCardById(uuidToString(cardId));

        return ok(cardMapper.toResponseDTO(card));
    }

    /**
     * Returns all cards with the field value isFavourite = true and created by authorized user
     * or only cards associated with cardSetId.
     *
     * @param cardSetId - optional request parameter.
     *                  If passed only cards that are favourite and belong to cardSet will be returned.
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

        List<CardResponseDTO> cardResponseDTOList = cardMapper.mapCardListToCardResponseDTOList(allCards);
        return ok(cardResponseDTOList);
    }

    @PostMapping
    public ResponseEntity<List<CardResponseDTO>> createCards(
            @RequestBody List<CardCreateRequestDTO> cardCreateDTOList) {

        List<Card> cardList = cardService.createCards(cardCreateDTOList);

        List<CardResponseDTO> cardResponseDTOList = cardMapper.mapCardListToCardResponseDTOList(cardList);
        return ok(cardResponseDTOList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardResponseDTO> updateCardById(@PathVariable("id") UUID cardId,
                                                          @RequestBody CardUpdateRequestDTO updateDto) {

        updateDto.setCardId(uuidToString(cardId));
        Card card = cardService.updateCard(updateDto);

        return ok(cardMapper.toResponseDTO(card));
    }

    @PatchMapping
    public ResponseEntity<List<CardResponseDTO>> updateCards(@RequestBody List<CardUpdateRequestDTO> updateDTOList) {
        List<Card> cardList = cardService.updateCards(updateDTOList);

        List<CardResponseDTO> cardResponseDTOList = cardMapper.mapCardListToCardResponseDTOList(cardList);
        return ok(cardResponseDTOList);
    }

    /**
     * Updates {@link Card#getProficiencyLevel()} of cards by cardId and
     * new proficiencyLevel passed in {@link UpdateCardProficiencyLevelDTO}
     **/
    @PatchMapping(value = "/proficiencyLevel")
    public ResponseEntity<List<CardResponseDTO>> updateCardsProficiencyLevel(
            @RequestBody List<UpdateCardProficiencyLevelDTO> updateProficiencyDTOList) {

        List<Card> cardList = cardService.updateCardsProficiencyLevel(updateProficiencyDTOList);

        List<CardResponseDTO> cardResponseDTOList = cardMapper.mapCardListToCardResponseDTOList(cardList);
        return ok(cardResponseDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCardById(@PathVariable("id") UUID cardId) {
        cardService.deleteCardById(uuidToString(cardId));

        return noContent().build();
    }

}
