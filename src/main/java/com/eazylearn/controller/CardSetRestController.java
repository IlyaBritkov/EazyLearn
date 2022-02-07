package com.eazylearn.controller;


import com.eazylearn.dto.request.cardset.CardSetCreateRequestDTO;
import com.eazylearn.dto.request.cardset.CardSetUpdateRequestDTO;
import com.eazylearn.dto.response.CardSetResponseDTO;
import com.eazylearn.exception.EntityAlreadyExistsException;
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

import static com.eazylearn.util.Convertor.uuidToString;

@RestController
@RequestMapping(value = "/api/v1/sets")
@RequiredArgsConstructor
@Slf4j
public class CardSetRestController {

    private final CardSetService cardSetService;

    // todo: API
    // get all sets
    // get favourite sets
    // create set

    @GetMapping()
    public ResponseEntity<List<CardSetResponseDTO>> findAllCategories() {
        List<CardSetResponseDTO> allCategories = cardSetService.findAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardSetResponseDTO> findCategoryById(@PathVariable("id") UUID categoryId) {
        CardSetResponseDTO cardSetResponseDTO = cardSetService.findCategoryById(uuidToString(categoryId));
        return ResponseEntity.ok(cardSetResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<CardSetResponseDTO> createCategory(
            @RequestBody CardSetCreateRequestDTO cardSetCreateRequestDTO) throws EntityAlreadyExistsException {
        CardSetResponseDTO cardSetResponseDTO = cardSetService.createCategory(cardSetCreateRequestDTO);
        return ResponseEntity.ok(cardSetResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardSetResponseDTO> updateCategoryById(@PathVariable("id") UUID categoryId,
                                                                 @RequestBody CardSetUpdateRequestDTO updateDto) {
        CardSetResponseDTO cardSetResponseDTO = cardSetService.updateCategoryById(uuidToString(categoryId), updateDto);

        return ResponseEntity.ok(cardSetResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") UUID categoryId,
                                                @RequestParam boolean isDeleteAllCardsInCategory) {
        cardSetService.deleteCategoryById(uuidToString(categoryId), isDeleteAllCardsInCategory);

        return ResponseEntity.noContent().build();
    }
}
