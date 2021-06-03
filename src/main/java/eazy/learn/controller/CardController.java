package eazy.learn.controller;

import eazy.learn.dto.request.CardCreateRequestDTO;
import eazy.learn.dto.response.CardResponseDTO;
import eazy.learn.enums.TabType;
import eazy.learn.exception.CategoryDoesNotExistException;
import eazy.learn.exception.TabDoesNotExistException;
import eazy.learn.service.CardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    // todo add global exception handling
    @GetMapping()
    public ResponseEntity<List<CardResponseDTO>> findAllCardsByTabAndCategoryId(@RequestParam(value = "tab", required = false) String tab,
                                                                                @RequestParam(value = "categoryId", required = false) Long categoryId) throws TabDoesNotExistException, CategoryDoesNotExistException {

        List<CardResponseDTO> allCards = cardService.findAllCardsByTabAndCategoryId(tab, categoryId);
        log.debug("tab = {}, categoryId = {}, allCards = {}", tab, categoryId, allCards);
        return new ResponseEntity<>(allCards, OK);
    }

    @PostMapping()
    public ResponseEntity<CardResponseDTO> createCard(@RequestParam(value = "tab", required = false) String tab,
                                                      @RequestBody CardCreateRequestDTO cardCreateRequestDTO) {
        log.trace("CardCreateRequestDTO = {}", cardCreateRequestDTO);
        CardResponseDTO cardResponseDTO = cardService.createCard(cardCreateRequestDTO);

        return new ResponseEntity<>(cardResponseDTO, OK);
    }

    // TODO remove and replace by popup form
    @GetMapping("/{id}/edit")
    public String editCard(Model model, @PathVariable("id") Long cardId) {
        model.addAttribute("card", cardService.findCardById(cardId));
        return "edit_card";
    }

    @PatchMapping("/{id}")
    public String updateCard(@RequestParam(value = "tab", required = false) String tab,
                             @ModelAttribute("card") CardResponseDTO card) {
        log.debug("Updated card = {}", card);
        cardService.updateCard(card);

        return redirectPageByTab(tab);
    }

    @DeleteMapping("/{id}")
    public String deleteCardById(@RequestParam(value = "tab", required = false) String tab,
                                 @PathVariable("id") Long cardId) {
        log.debug("CardId for deleting = {}", cardId);
        cardService.deleteCardById(cardId);

        return redirectPageByTab(tab);
    }

    private String redirectPageByTab(String tab) {
        log.trace("Input tab for redirection = {}", tab);
        if (tab == null) {
            tab = "home";
        }
        log.trace("Tab for redirection = {}", tab);

        TabType tabType = TabType.valueOf(tab.toUpperCase());
        switch (tabType) {
            case CATEGORY:
                // TODO fix it
                return "redirect:/cards?tab=category&categoryId=??????????";
            case RECENT:
                return "redirect:/cards?tab=recent";
            default:
                return "redirect:/cards";
        }
    }
}
