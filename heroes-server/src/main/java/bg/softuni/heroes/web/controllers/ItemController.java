package bg.softuni.heroes.web.controllers;

import bg.softuni.heroes.service.services.ItemService;
import bg.softuni.heroes.web.models.request.ItemCreateRequest;
import bg.softuni.heroes.web.models.request.ItemEditRequest;
import bg.softuni.heroes.web.models.response.item.ItemDetailsResponseModel;
import bg.softuni.heroes.web.models.response.item.ItemResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static bg.softuni.heroes.config.WebConfig.URL_ITEMS_BASE;

@RestController
@RequestMapping(URL_ITEMS_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemController {

    private final ItemService itemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemResponseModel all(Pageable pageable) {
        return itemService.getAllItems(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @ModelAttribute ItemCreateRequest request) {
        return itemService.create(request);
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDetailsResponseModel findByName(@PathVariable String name) {
        return itemService.findByName(name);
    }

    @PatchMapping
    public ResponseEntity<?> edit(@Valid @ModelAttribute ItemEditRequest request) {
        return itemService.edit(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return itemService.delete(id);
    }

}
