package com.shdwraze.dmarket.controller;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.Hero;
import com.shdwraze.dmarket.entity.Item;
import com.shdwraze.dmarket.entity.Lot;
import com.shdwraze.dmarket.entity.enums.Quality;
import com.shdwraze.dmarket.entity.enums.Rarity;
import com.shdwraze.dmarket.entity.enums.Type;
import com.shdwraze.dmarket.repo.AccountRepository;
import com.shdwraze.dmarket.repo.HeroRepository;
import com.shdwraze.dmarket.repo.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/lots")
public class LotController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private LotRepository lotRepository;

    @GetMapping({"/", ""})
    public String showAllLotsOnCurrentAccount(Principal principal, Model model) {
        Account account = accountRepository.findByLogin(principal.getName());
        List<Lot> lots = account.getLots();
        model.addAttribute("lots", lots);

        return "lots";
    }

    @GetMapping("/add")
    public String addNewLot(Model model) {
        Item item = new Item();
        Lot lot = new Lot();
        List<Hero> heroes = heroRepository.findAll();
        model.addAttribute("item", item);
        model.addAttribute("lot", lot);
        model.addAttribute("heroes", heroes);

        return "add-lot";
    }

    @PostMapping("/add")
    public String addNewLotIntoDatabase(@RequestParam(name = "type") Type type,
                                        @RequestParam(name = "heroID") int heroID,
                                        @RequestParam(name = "rarity") Rarity rarity,
                                        @RequestParam(name = "quality") Quality quality,
                                        Item item, Lot lot, Principal principal) {
        Account account = accountRepository.findByLogin(principal.getName());
        item.setQuality(quality);
        item.setType(type);
        item.setRarity(rarity);

        Optional<Hero> heroOptional = heroRepository.findById(heroID);
        heroOptional.ifPresent(item::setHero);

        lot.setItem(item);
        lot.setSeller(account);
        lotRepository.save(lot);

        return "redirect:/lots";
    }

    @PostMapping("/delete/{id}")
    public String deleteLot(@PathVariable int id) {
        lotRepository.deleteById(id);

        return "redirect:/lots";
    }

    @GetMapping("/edit/{id}")
    public String getInfoForEditingLot(@PathVariable(name = "id") int id, Model model) {
        Lot lot = lotRepository.findById(id).orElse(null);
        List<Hero> heroes = heroRepository.findAll();

        model.addAttribute("lot", lot);
        model.addAttribute("heroes", heroes);

        return "lot-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateLotAfterEdit(@PathVariable(name = "id") int id, Lot lot) {
        Lot l = lotRepository.findById(id).orElse(null);

        if (l != null) {
            l.setDescription(lot.getDescription());
            l.setPrice(lot.getPrice());
            l.getItem().setHero(lot.getItem().getHero());
            l.getItem().setType(lot.getItem().getType());
            l.getItem().setRarity(lot.getItem().getRarity());
            l.getItem().setQuality(lot.getItem().getQuality());
            l.getItem().setName(lot.getItem().getName());

            lotRepository.save(l);
        }

        return "redirect:/lots";
    }
}
