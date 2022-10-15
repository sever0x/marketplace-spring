package com.shdwraze.dmarket.service.security;

import com.shdwraze.dmarket.entity.Account;
import com.shdwraze.dmarket.entity.enums.Role;
import com.shdwraze.dmarket.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = accountRepository.findByLogin(login);
        if (account == null) {
            throw new UsernameNotFoundException(login);
        }

        return new CustomUserDetails(account, mapRolesToAuthority(account.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority()));
        authorities.add(new SimpleGrantedAuthority(role.getAuthority()));

        return authorities;
    }
}
