package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.repo.AccountRepository;
import com.shdwraze.dmarket.repo.HeroRepository;
import com.shdwraze.dmarket.repo.ItemRepository;
import com.shdwraze.dmarket.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private LotRepository lotRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
