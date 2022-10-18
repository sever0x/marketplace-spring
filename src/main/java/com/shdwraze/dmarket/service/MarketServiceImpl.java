package com.shdwraze.dmarket.service;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.Lot;
import com.shdwraze.dmarket.entity.Purchase;
import com.shdwraze.dmarket.entity.enums.LotStatus;
import com.shdwraze.dmarket.entity.enums.PurchaseStatus;
import com.shdwraze.dmarket.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void buy(Account buyer, Account seller, Lot lot) {
        List<Purchase> purchases = buyer.getPurchases();

        Purchase purchase = new Purchase();
        purchase.setLot(lot);
        purchase.setBuyer(buyer);
        purchase.setDate(Date.valueOf(LocalDate.now()));
        purchase.setStatus(PurchaseStatus.PAID);
        purchases.add(purchase);

        buyer.setPurchases(purchases);

        seller.getAccountInfo().setBalance(seller.getAccountInfo().getBalance() + lot.getPrice());
        buyer.getAccountInfo().setBalance(buyer.getAccountInfo().getBalance() - lot.getPrice());

        lot.setStatus(LotStatus.ARCHIVE);

        accountRepository.save(seller);
        accountRepository.save(buyer);
    }
}
