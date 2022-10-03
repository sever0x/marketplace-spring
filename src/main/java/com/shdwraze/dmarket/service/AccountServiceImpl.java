package com.shdwraze.dmarket.service;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void linkToSteamAccount(String steamID, String login) {
        Account account = accountRepository.findByLogin(login);
        account.getAccountInfo().setSteamID(steamID);
        accountRepository.save(account);
    }
}
