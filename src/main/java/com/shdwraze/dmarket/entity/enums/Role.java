package com.shdwraze.dmarket.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, CUSTOMER, SELLER;

    @Override
    public String getAuthority() {
        return name();
    }
}
