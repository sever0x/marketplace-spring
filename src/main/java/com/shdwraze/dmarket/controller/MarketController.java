package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.Item;
import com.shdwraze.dmarket.entity.Lot;
import com.shdwraze.dmarket.entity.Purchase;
import com.shdwraze.dmarket.entity.enums.LotStatus;
import com.shdwraze.dmarket.entity.enums.PurchaseStatus;
import com.shdwraze.dmarket.repo.AccountRepository;
import com.shdwraze.dmarket.repo.LotRepository;
import com.shdwraze.dmarket.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/market")
public class MarketController {
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MarketService marketService;

    @GetMapping({"/", ""})
    public String showAllLots(Model model) {
        List<Lot> lots = lotRepository.findAll();
        model.addAttribute("lots", lots);
        return "market";
    }

    @GetMapping("/{id}")
    public String showLotOnMarket(@PathVariable(name = "id") int id, Model model) {
        Lot lot = lotRepository.findById(id).orElse(null);
        model.addAttribute("lot", lot);
        if ((lot != null ? lot.getItem().getHero() : null) != null) {
            model.addAttribute("hero", lot.getItem().getHero());
        }

        return "show-lot";
    }

    @PostMapping("/{id}")
    public String buy(@PathVariable(name = "id") int id, Principal principal, RedirectAttributes attributes) {
        Account account = accountRepository.findByLogin(principal.getName());
        Lot lot = lotRepository.findById(id).orElse(null);
        if (lot != null && account.getAccountInfo().getBalance() >= lot.getPrice()) {
            marketService.buy(account, lot.getSeller(), lot);
        } else {
            attributes.addFlashAttribute("error", "Not enough money on account");
        }
        return "redirect:/market/{id}";
    }
}
