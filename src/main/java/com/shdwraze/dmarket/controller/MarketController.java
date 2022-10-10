package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.entity.Item;
import com.shdwraze.dmarket.entity.Lot;
import com.shdwraze.dmarket.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/market")
public class MarketController {
    @Autowired
    private LotRepository lotRepository;

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
}
