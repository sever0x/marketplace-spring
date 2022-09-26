package com.shdwraze.dmarket.repo;

import com.shdwraze.dmarket.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
