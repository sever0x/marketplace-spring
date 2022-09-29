package com.shdwraze.dmarket.service;

import java.util.List;

public interface HeroService {
    List<DotaHero> getAllHeroes();
    List<String> getHeroesForDatabase(List<DotaHero> heroesObj);
    void putListWithHeroesNameIntoDatabase();
}
