package bg.softuni.heroes.web.controllers;

import bg.softuni.heroes.service.models.item.ItemCreateServiceModel;
import bg.softuni.heroes.service.models.item.ItemEditServiceModel;
import bg.softuni.heroes.service.services.ItemService;
import bg.softuni.heroes.util.ModelMapperWrapper;
import bg.softuni.heroes.web.models.request.ItemCreateRequest;
import bg.softuni.heroes.web.models.request.ItemEditRequest;
import bg.softuni.heroes.web.models.response.item.ItemDetailsResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

import static bg.softuni.heroes.config.WebConfig.*;
import static bg.softuni.heroes.constants.ResponseMessages.ITEM_DELETED;

@RestController
@RequestMapping(URL_ITEMS_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemController {

    private final ItemService itemService;
    private final ModelMapperWrapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all(@PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
                                 @SortDefault.SortDefaults({
                                         @SortDefault(sort = "name", direction = Sort.Direction.ASC),
                                         @SortDefault(sort = "attack", direction = Sort.Direction.DESC)
                                 }) Pageable pageable) {

        Page<ItemDetailsResponseModel> itemsPage = itemService.getItemsPage(pageable)
                .map(model -> modelMapper.map(model, ItemDetailsResponseModel.class));

        return ResponseEntity.ok(itemsPage);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @ModelAttribute ItemCreateRequest request) {

        final ItemCreateServiceModel createModel = modelMapper.map(request, ItemCreateServiceModel.class);

        final ItemDetailsResponseModel createdItem = modelMapper
                .map(itemService.create(createModel), ItemDetailsResponseModel.class);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path(URL_API_BASE + URL_ITEMS_BASE)
                .buildAndExpand().toUri();

        return ResponseEntity.created(location).body(createdItem);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "id")
    public ResponseEntity<?> findById(@RequestParam UUID id) {

        ItemDetailsResponseModel item = modelMapper
                .map(itemService.findById(id), ItemDetailsResponseModel.class);

        return ResponseEntity.ok(item);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    public ResponseEntity<?> findByName(@RequestParam("name") String name) {

        ItemDetailsResponseModel item = modelMapper
                .map(itemService.findByName(name), ItemDetailsResponseModel.class);

        return ResponseEntity.ok(item);
    }

    @PatchMapping
    public ResponseEntity<?> edit(@Valid @ModelAttribute ItemEditRequest request) {

        ItemEditServiceModel editModel = modelMapper
                .map(request, ItemEditServiceModel.class);

        ItemDetailsResponseModel createdItem = modelMapper
                .map(itemService.edit(editModel), ItemDetailsResponseModel.class);

        return ResponseEntity.ok(createdItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        itemService.delete(id);

        return ResponseEntity.ok(new HashMap<>() {{
            put("message", ITEM_DELETED);
        }});
    }
}
