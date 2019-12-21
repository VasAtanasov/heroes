package bg.softuni.heroes.service.services.impl;

import bg.softuni.heroes.data.repositories.HeroRepository;
import bg.softuni.heroes.data.repositories.ItemRepository;
import bg.softuni.heroes.service.models.hero.HeroCreateServiceClass;
import bg.softuni.heroes.service.models.hero.HeroDetailsServiceModel;
import bg.softuni.heroes.service.services.HeroService;
import bg.softuni.heroes.util.ModelMapperWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
