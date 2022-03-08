package com.eazylearn.controller;


import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.dto.response.CardSetResponseDTO;
import com.eazylearn.entity.CardSet;
import com.eazylearn.mapper.CardSetMapper;
import com.eazylearn.service.CardSetService;
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

import static com.eazylearn.util.Constants.CARDSETS_ENDPOINT_PATH;
import static com.eazylearn.util.Convertor.uuidToString;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = CARDSETS_ENDPOINT_PATH)
@RequiredArgsConstructor
@Slf4j
public class CardSetRestController { // todo: add pagination

    private final CardSetService cardSetService;
    private final CardSetMapper cardSetMapper;

    // todo: GET specific cardsets (with or without cards inside)

    @GetMapping()
    public ResponseEntity<List<CardSetResponseDTO>> findAllCardSets() {
        final List<CardSet> allCategories = cardSetService.findAllCardSets();

        final List<CardSetResponseDTO> cardSetResponseDTOs = allCategories.stream()
                .map(cardSetMapper::toResponseDTO)
                .collect(toList());
        return ok(cardSetResponseDTOs);
    }

    @GetMapping("/favourite")
    public ResponseEntity<List<CardSetResponseDTO>> findAllFavouriteCardSets() {
        final List<CardSet> allCardSets = cardSetService.findAllFavouriteCardSets();

        final List<CardSetResponseDTO> cardSetResponseDTOs = allCardSets.stream()
                .map(cardSetMapper::toResponseDTO)
                .collect(toList());
        return ok(cardSetResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardSetResponseDTO> findCardSetById(@PathVariable("id") UUID cardSetId) {
        final CardSet cardSetById = cardSetService.findCardSetById(uuidToString(cardSetId));
        return ok(cardSetMapper.toResponseDTO(cardSetById));
    }

    @PostMapping
    public ResponseEntity<CardSetResponseDTO> createCardSet(@RequestBody CardSetCreateRequestDTO cardSetCreateRequestDTO) {
        final CardSet newCardSet = cardSetService.createCardSet(cardSetCreateRequestDTO);
        return ok(cardSetMapper.toResponseDTO(newCardSet));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardSetResponseDTO> updateCardSetById(@PathVariable("id") UUID categoryId,
                                                                @RequestBody CardSetUpdateRequestDTO updateDto) {
        final CardSet updatedCardSet = cardSetService.updateCardSetById(uuidToString(categoryId), updateDto);
        return ok(cardSetMapper.toResponseDTO(updatedCardSet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") UUID categoryId,
                                                @RequestParam(required = false) boolean isDeleteAllCardsInCategory) {
        cardSetService.deleteCardSetById(uuidToString(categoryId), isDeleteAllCardsInCategory);
        return ResponseEntity.noContent().build();
    }
}
