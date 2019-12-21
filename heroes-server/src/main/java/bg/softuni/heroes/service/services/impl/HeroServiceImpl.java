package bg.softuni.heroes.service.services.impl;

import bg.softuni.heroes.data.models.Hero;
import bg.softuni.heroes.data.repositories.HeroRepository;
import bg.softuni.heroes.data.repositories.ItemRepository;
import bg.softuni.heroes.service.models.hero.HeroCreateServiceClass;
import bg.softuni.heroes.service.models.hero.HeroDetailsServiceModel;
import bg.softuni.heroes.service.services.HeroService;
import bg.softuni.heroes.util.ModelMapperWrapper;
import bg.softuni.heroes.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static bg.softuni.heroes.constants.ResponseMessages.HERO_ID_NOT_EXISTS;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final ItemRepository itemRepository;
    private final ModelMapperWrapper modelMapper;

    @Override
    public HeroDetailsServiceModel create(HeroCreateServiceClass heroCreateServiceClass) {
        return null;
    }

    @Override
    public HeroDetailsServiceModel findById(UUID id) {
        Hero hero = heroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HERO_ID_NOT_EXISTS));

        return modelMapper.map(hero, HeroDetailsServiceModel.class);
    }

    @Override
    public HeroDetailsServiceModel findByName(String name) {
        Hero hero = heroRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(HERO_ID_NOT_EXISTS));

        return modelMapper.map(hero, HeroDetailsServiceModel.class);
    }

}
