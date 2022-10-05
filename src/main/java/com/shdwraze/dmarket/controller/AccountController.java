package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/settings")
    public String showAccountSettings(Principal principal, Model model) {
        Account account = accountRepository.findByLogin(principal.getName());
        model.addAttribute("account", account);

        return "settings";
    }

    @PostMapping("/settings/apply")
    public String applyChangesInAccount(Principal principal, Account account) {
        Account updAccount = accountRepository.findByLogin(principal.getName());

        updAccount.getAccountInfo().setEmail(account.getAccountInfo().getEmail());
        updAccount.getAccountInfo().setPhone(account.getAccountInfo().getPhone());
        updAccount.setLogin(account.getLogin());
        updAccount.setPassword(encoder.encode(account.getPassword()));

        accountRepository.save(updAccount);

        return "redirect:/logout";
    }
}
