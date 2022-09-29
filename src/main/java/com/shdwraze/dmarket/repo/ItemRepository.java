package com.shdwraze.dmarket.repo;

import com.shdwraze.dmarket.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
