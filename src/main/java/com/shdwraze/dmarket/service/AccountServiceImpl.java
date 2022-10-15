package com.shdwraze.dmarket.service;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.enums.Role;
import com.shdwraze.dmarket.repo.AccountRepository;
import com.shdwraze.dmarket.service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void updateAccountPermission(Principal principal, Authentication authentication) {
        Account account = accountRepository.findByLogin(principal.getName());
        account.setRole(Role.SELLER);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole().getAuthority()));
        authorities.add(new SimpleGrantedAuthority(account.getRole().getAuthority()));
        userDetails.setAuthorities(authorities);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                userDetails.getAuthorities()
        );
        token.setDetails(authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(token);

        accountRepository.save(account);
    }

    @Override
    public void setNewLogin(Authentication authentication, String login) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userDetails.setUsername(login);
    }
}
