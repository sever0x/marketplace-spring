package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.enums.Role;
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
        boolean newLogin = false;

        updAccount.getAccountInfo().setEmail(account.getAccountInfo().getEmail());
        updAccount.getAccountInfo().setPhone(account.getAccountInfo().getPhone());
        if (!updAccount.getLogin().equals(account.getLogin())) {
            newLogin = true;
            updAccount.setLogin(account.getLogin());
        }
        if (!account.getPassword().equals("")) {
            if (!encoder.matches(account.getPassword(), updAccount.getPassword())) {
                updAccount.setPassword(encoder.encode(account.getPassword()));
            }
        }

        accountRepository.save(updAccount);

        return newLogin ? "redirect:/logout" : "redirect:/";
    }

    @GetMapping("/purchases")
    public String showAllPurchases(Principal principal, Model model) {
        Account account = accountRepository.findByLogin(principal.getName());
        model.addAttribute("purchases", account.getPurchases());

        return "purchases";
    }

    @GetMapping("/trade")
    public String switchToSeller(Principal principal, Model model) {
        Account account = accountRepository.findByLogin(principal.getName());
        model.addAttribute("phone",
                account.getAccountInfo().getPhone().equals("")
                        ? null
                        : account.getAccountInfo().getPhone());
        model.addAttribute("steamID",
                account.getAccountInfo().getSteamID() == null
                        ? null
                        : account.getAccountInfo().getSteamID());

        return "trade-info";
    }

    @PostMapping("/trade")
    public String updateAccountRole(Principal principal) {
        Account account = accountRepository.findByLogin(principal.getName());
        account.setRole(Role.SELLER);
        accountRepository.save(account);

        return "redirect:/logout";
    }
}
