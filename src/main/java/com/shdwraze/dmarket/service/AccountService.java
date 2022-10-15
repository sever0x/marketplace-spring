package com.shdwraze.dmarket.service;

import com.shdwraze.dmarket.entity.Account;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface AccountService {
    void linkToSteamAccount(String steamID, String login);

    void updateAccountPermission(Principal principal, Authentication authentication);

    void setNewLogin(Authentication authentication, String login);
}
