package com.shdwraze.dmarket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shdwraze.dmarket.entity.Hero;
import com.shdwraze.dmarket.repo.HeroRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class HeroServiceImpl implements HeroService {
    private final String URL = "https://api.opendota.com/api/heroes";
    @Autowired
    private HeroRepository heroRepository;

    @Override
    public List<DotaHero> getAllHeroes() {
        List<DotaHero> heroesObj = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            heroesObj = Arrays.asList(objectMapper.readValue(new URL(URL), DotaHero[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return heroesObj;
    }

    @Override
    public List<String> getHeroesForDatabase(List<DotaHero> heroesObj) {
        Set<String> heroes = new TreeSet<>();

        for (DotaHero hero : heroesObj) {
            heroes.add(hero.getLocalized_name());
        }

        return new ArrayList<>(heroes);
    }

    @Override
    public void putListWithHeroesNameIntoDatabase() {
        List<String> heroes = getHeroesForDatabase(getAllHeroes());

        for (String heroName : heroes) {
            Hero hero = new Hero();
            hero.setName(heroName);
            heroRepository.save(hero);
        }
    }
}

@Getter
@Setter
@ToString
class DotaHero {
    private int id;
    private String name;
    private String localized_name;
    private String primary_attr;
    private String attack_type;
    private List<String> roles;
    private int legs;
}