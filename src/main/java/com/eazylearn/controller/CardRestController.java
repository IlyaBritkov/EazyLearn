package com.eazylearn.controller;

import com.eazylearn.dto.request.CardCreateRequestDTO;
import com.eazylearn.dto.request.CardUpdateRequestDTO;
import com.eazylearn.dto.response.CardResponseDTO;
import com.eazylearn.exception_handling.exception.EntityDoesNotExistException;
import com.eazylearn.service.CardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@RestController
@RequestMapping(value = "/api/v1/cards")
public class CardRestController { // TODO: refactor tab logic

    private final CardService cardService;

    @GetMapping()
    public ResponseEntity<List<CardResponseDTO>> findAllCardsByTabAndCardSetId(@RequestParam(value = "tab", required = false) String tab,
                                                                               @RequestParam(value = "cardSetId", required = false) UUID cardSetId)
            throws EntityDoesNotExistException {
        List<CardResponseDTO> allCards = cardService.findAllCardsByTabAndCardSetId(tab, cardSetId);
        return ResponseEntity.ok(allCards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDTO> findCardById(@PathVariable("id") UUID cardId) throws EntityDoesNotExistException {
        CardResponseDTO cardResponseDTO = cardService.findCardById(cardId);
        return ResponseEntity.ok(cardResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<CardResponseDTO> createCard(@RequestBody CardCreateRequestDTO cardCreateRequestDTO) throws EntityDoesNotExistException {
        CardResponseDTO cardResponseDTO = cardService.createCard(cardCreateRequestDTO);
        return ResponseEntity.ok(cardResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardResponseDTO> updateCardById(@PathVariable("id") UUID cardId,
                                                          @RequestBody CardUpdateRequestDTO updateDto) throws EntityDoesNotExistException {
        CardResponseDTO cardResponseDTO = cardService.updateCardById(cardId, updateDto);

        return ResponseEntity.ok(cardResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCardById(@PathVariable("id") UUID cardId) throws EntityDoesNotExistException {
        cardService.deleteCardById(cardId);

        return ResponseEntity.noContent().build();
    }
}
