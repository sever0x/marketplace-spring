package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.AccountInfo;
import com.shdwraze.dmarket.entity.enums.Role;
import com.shdwraze.dmarket.repo.AccountRepository;
import com.shdwraze.dmarket.repo.HeroRepository;
import com.shdwraze.dmarket.repo.ItemRepository;
import com.shdwraze.dmarket.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.time.LocalDate;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({"/", ""})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/reg")
    public String registration(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("accountInfo", new AccountInfo());
        return "registration";
    }

    @PostMapping("/reg")
    public String confirmRegistration(Account account, AccountInfo accountInfo) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(Role.CUSTOMER);
        accountInfo.setBalance(0.0);
        accountInfo.setRegistrationDate(Date.valueOf(LocalDate.now()));
        account.setAccountInfo(accountInfo);
        accountInfo.setAccount(account);

        accountRepository.save(account);
        return "redirect:/";
    }
}
