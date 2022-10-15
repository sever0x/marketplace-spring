package com.shdwraze.dmarket.service.security;

import com.shdwraze.dmarket.entity.Account;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@ToString
public class CustomUserDetails implements UserDetails {
    @ToString.Exclude
    private Account account;
    private Collection<? extends GrantedAuthority> authorities;

    private final long serialVersionUID = 1L;

    public CustomUserDetails(Account account, Collection<? extends GrantedAuthority> authorities) {
        this.account = account;
        this.authorities = authorities;
    }

    //    public CustomUserDetails(Account account) {
//        this.account = account;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.account.getRole().getAuthority()));

        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    @Override
    public String getUsername() {
        return this.account.getLogin();
    }

    public void setUsername(String username) {
        this.account.setLogin(username);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
