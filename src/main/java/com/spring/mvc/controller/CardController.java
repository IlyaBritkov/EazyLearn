package com.spring.mvc.controller;

import com.spring.mvc.entity.CardEntity;
import com.spring.mvc.service.CardService;
import com.spring.mvc.tabs.TabsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
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
                                  Model model, @ModelAttribute("card") CardEntity card) {

        // if not existing tab param
        if (tab == null) {
            tab = "home";
        }
        String finalTab = tab;
        if (Arrays.stream(TabsEnum.values()).noneMatch(tabsEnum -> tabsEnum.toString().equalsIgnoreCase(finalTab)) && !tab.equalsIgnoreCase(TabsEnum.HOME.toString())) {
            // TODO: add redirect to login page
            return "redirect:/login";
        }

        // TODO get Category for display its name in view
//        model.addAttribute("categoryId", categoryService.getCategoryById(categoryId));
        model.addAttribute("allCards", cardService.getAllCards(tab, categoryId));

        TabsEnum tabType = TabsEnum.valueOf(tab.toUpperCase());
        switch (tabType) {
            case HOME:
                return "home";
            case CATEGORY:
                if (categoryId == null) {
                    // TODO add redirect to categories page
                    return "redirect:/category";
                } else {
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
                             @ModelAttribute("card") CardEntity card) {
        cardService.createCard(card);

        return redirectPageByTab(tab);
    }

    // TODO replace by popup form
    @GetMapping("/{id}/edit")
    public String editCard(Model model, @PathVariable("id") Long cardId) {
        model.addAttribute("card", cardService.findCardById(cardId));
        return "edit_card";
    }

    @PatchMapping("/{id}")
    public String updateCard(@RequestParam(value = "tab", required = false) String tab,
                             @ModelAttribute("card") CardEntity card) {
        cardService.updateCard(card);

        return redirectPageByTab(tab);
    }

    @DeleteMapping("/{id}")
    public String deleteCardById(@RequestParam(value = "tab", required = false) String tab,
                                 @PathVariable("id") Long cardId) {
        cardService.deleteCardById(cardId);

        return redirectPageByTab(tab);
    }

    private String redirectPageByTab(String tab) {
        if (tab == null) {
            tab = "home";
        }

        TabsEnum tabType = TabsEnum.valueOf(tab.toUpperCase());
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
