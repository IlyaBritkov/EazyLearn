package eazy.learn.controller;

import eazy.learn.dto.CardDto;
import eazy.learn.enums.TabType;
import eazy.learn.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }


    @GetMapping()
    public String findAllByFilter(@RequestParam(value = "tab", required = false) String tab,
                                  @RequestParam(value = "categoryId", required = false) Long categoryId,
                                  Model model, @ModelAttribute("card") CardDto card) {
        // if not existing tab param
        if (tab == null) {
            tab = "home";
        }
        String finalTab = tab;
        if (Arrays.stream(TabType.values()).noneMatch(TabType -> TabType.toString().equalsIgnoreCase(finalTab)) && !tab.equalsIgnoreCase(TabType.HOME.toString())) {
            // TODO: add redirect to login page
            return "redirect:/login";
        }


        List<CardDto> allCards = cardService.findAllCardsByTabAndCategoryId(tab, categoryId);
        log.debug("tab = {}, categoryId = {}, allCards = {}", tab, categoryId, allCards);
        System.out.println(allCards); // todo
        model.addAttribute("allCards", allCards);

        TabType tabType = TabType.valueOf(tab.toUpperCase());
        switch (tabType) {
            case HOME:
                return "home";
            case CATEGORY:
                if (categoryId == null) {
                    // TODO add redirect to categories page
                    return "redirect:/category";
                } else {
                    // TODO get Category for display its name in view
//                    model.addAttribute("category", categoryService.getCategoryById(categoryId));
                    return "category_page";
                }
            case RECENT:
                return "recent";
            default:
                return "redirect:/cards";
        }
    }

    @PostMapping()
    public String createCard(@RequestParam(value = "tab", required = false) String tab,
                             @ModelAttribute("card") CardDto card) {
        log.debug("Created card = {}", card);
        cardService.createCard(card);

        return redirectPageByTab(tab);
    }

    // TODO remove and replace by popup form
    @GetMapping("/{id}/edit")
    public String editCard(Model model, @PathVariable("id") Long cardId) {
        model.addAttribute("card", cardService.findCardById(cardId));
        return "edit_card";
    }

    @PatchMapping("/{id}")
    public String updateCard(@RequestParam(value = "tab", required = false) String tab,
                             @ModelAttribute("card") CardDto card) {
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
