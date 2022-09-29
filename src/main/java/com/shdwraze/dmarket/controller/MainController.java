package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.repo.AccountRepository;
import com.shdwraze.dmarket.repo.HeroRepository;
import com.shdwraze.dmarket.repo.ItemRepository;
import com.shdwraze.dmarket.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private LotRepository lotRepository;

    @GetMapping({"/", ""})
    public List<Account> index() {
        return accountRepository.findAll();
    }
}
