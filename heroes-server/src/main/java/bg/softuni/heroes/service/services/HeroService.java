package bg.softuni.heroes.service.services;

import bg.softuni.heroes.service.models.hero.HeroCreateServiceClass;
import bg.softuni.heroes.service.models.hero.HeroDetailsServiceModel;

public interface HeroService {

    HeroDetailsServiceModel create(HeroCreateServiceClass heroCreateServiceClass);

}
