package bg.softuni.heroes.web.controllers;

import bg.softuni.heroes.service.services.HeroService;
import bg.softuni.heroes.util.ModelMapperWrapper;
import bg.softuni.heroes.web.models.response.item.HeroDetailsResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static bg.softuni.heroes.config.WebConfig.URL_HERO_BASE;

@RestController
@RequestMapping(URL_HERO_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HeroController {

    private final HeroService heroService;
    private final ModelMapperWrapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "id")
    public ResponseEntity<?> findById(@RequestParam UUID id) {

        HeroDetailsResponseModel item = modelMapper
                .map(heroService.findById(id), HeroDetailsResponseModel.class);

        return ResponseEntity.ok(item);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    public ResponseEntity<?> findByName(@RequestParam("name") String name) {

        HeroDetailsResponseModel item = modelMapper
                .map(heroService.findByName(name), HeroDetailsResponseModel.class);

        return ResponseEntity.ok(item);
    }

}
