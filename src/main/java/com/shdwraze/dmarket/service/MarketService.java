package com.shdwraze.dmarket.service;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.Lot;

public interface MarketService {
    void buy(Account buyer, Account seller, Lot lot);
}
