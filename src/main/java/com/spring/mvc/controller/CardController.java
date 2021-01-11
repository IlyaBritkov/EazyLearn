package com.spring.mvc.controller;

import com.spring.mvc.service.CardService;
import com.spring.mvc.tabs.TabsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String findAllByFilter(@RequestParam("tab") String tab, @RequestParam(value = "categoryId", required = false) Long categoryId, Model model) {
        // if not existing tab param
        if (Arrays.stream(TabsEnum.values()).noneMatch(tabsEnum -> tabsEnum.toString().equalsIgnoreCase(tab))) {
            // TODO: add redirect to login page
            return "redirect:/login";
        }

        model.addAttribute("allCards", cardService.getAllCards(tab, categoryId));

        TabsEnum tabType = TabsEnum.valueOf(tab.toUpperCase());
        switch (tabType) {
            case HOME:
                return "home";
            case CATEGORY:
                return "category";
            case RECENT:
                return "recent";
            default:
                // TODO: add redirect/cards with param ?tab=home
                return null;
        }
    }

}
