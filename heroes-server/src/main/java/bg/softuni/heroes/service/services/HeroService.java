package bg.softuni.heroes.service.services;

import bg.softuni.heroes.service.models.hero.HeroCreateServiceClass;
import bg.softuni.heroes.service.models.hero.HeroDetailsServiceModel;

import java.util.UUID;

public interface HeroService {

    HeroDetailsServiceModel create(HeroCreateServiceClass heroCreateServiceClass);

    HeroDetailsServiceModel findById(UUID id);

    HeroDetailsServiceModel findByName(String name);

}
