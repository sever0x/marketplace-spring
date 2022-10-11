package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.Item;
import com.shdwraze.dmarket.entity.Lot;
import com.shdwraze.dmarket.entity.Purchase;
import com.shdwraze.dmarket.entity.enums.LotStatus;
import com.shdwraze.dmarket.entity.enums.PurchaseStatus;
import com.shdwraze.dmarket.repo.AccountRepository;
import com.shdwraze.dmarket.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String buy(@PathVariable(name = "id") int id, Principal principal) {
        Account account = accountRepository.findByLogin(principal.getName());
        Lot lot = lotRepository.findById(id).orElse(null);
        List<Purchase> purchases = account.getPurchases();

        Purchase purchase = new Purchase();
        if (lot != null) {
            lot.setStatus(LotStatus.ARCHIVE);
        } else {
            throw new NullPointerException("Lot cannot be zero");
        }

        purchase.setLot(lot);
        purchase.setBuyer(account);
        purchase.setDate(Date.valueOf(LocalDate.now()));
        purchase.setStatus(PurchaseStatus.PAID);

        purchases.add(purchase);
        account.setPurchases(purchases);
        accountRepository.save(account);

        return "redirect:/market/{id}";
    }
}
