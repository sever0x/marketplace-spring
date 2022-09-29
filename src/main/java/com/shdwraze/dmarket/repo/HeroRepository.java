package com.shdwraze.dmarket.repo;

import com.shdwraze.dmarket.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Integer> {
}
