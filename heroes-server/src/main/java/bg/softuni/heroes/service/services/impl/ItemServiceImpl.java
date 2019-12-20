package bg.softuni.heroes.service.services.impl;

import bg.softuni.heroes.data.enums.Slot;
import bg.softuni.heroes.data.models.Item;
import bg.softuni.heroes.data.repositories.ItemRepository;
import bg.softuni.heroes.service.services.ItemService;
import bg.softuni.heroes.util.EnumUtils;
import bg.softuni.heroes.util.ModelMapperWrapper;
import bg.softuni.heroes.web.exception.ResourceNotFoundException;
import bg.softuni.heroes.web.models.request.ItemCreateRequest;
import bg.softuni.heroes.web.models.request.ItemEditRequest;
import bg.softuni.heroes.web.models.response.api.ApiResponse;
import bg.softuni.heroes.web.models.response.item.ItemDetailsResponseModel;
import bg.softuni.heroes.web.models.response.item.ItemListModel;
import bg.softuni.heroes.web.models.response.item.ItemResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

import static bg.softuni.heroes.config.WebConfig.URL_API_BASE;
import static bg.softuni.heroes.config.WebConfig.URL_ITEMS_BASE;
import static bg.softuni.heroes.constants.ResponseMessages.*;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapperWrapper modelMapper;

    @Override
    public ItemResponseModel getAllItems(Pageable pageable) {
        Page<Item> items = itemRepository.findAll(pageable);
        return ItemResponseModel.builder()
                .items(items.stream().map(item -> modelMapper.map(item, ItemListModel.class)).collect(Collectors.toList()))
                .total(items.getTotalElements())
                .build();
    }

    @Override
    public ResponseEntity<?> create(ItemCreateRequest request) {
        EnumUtils.fromString(request.getSlot(), Slot.class)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_SLOT));

        if (itemRepository.findByName(request.getName()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(String.format(ITEM_EXISTS, request.getName()))
                            .build());
        }

        Item item = modelMapper.map(request, Item.class);
        itemRepository.saveAndFlush(item);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path(URL_API_BASE + URL_ITEMS_BASE)
                .buildAndExpand().toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.builder()
                        .success(true)
                        .message(SUCCESSFUL_ITEM_CREATED)
                        .build());
    }

    @Override
    public ItemDetailsResponseModel findById(UUID id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_ID_NOT_EXISTS));
        return modelMapper.map(item, ItemDetailsResponseModel.class);
    }

    @Override
    public ItemDetailsResponseModel findByName(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_NAME_NOT_EXISTS));
        return modelMapper.map(item, ItemDetailsResponseModel.class);
    }

    @Override
    public ResponseEntity<?> edit(ItemEditRequest request) {
        EnumUtils.fromString(request.getSlot(), Slot.class)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_SLOT));

        Item item = itemRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_ID_NOT_EXISTS));

        BeanUtils.copyProperties(request, item);
        itemRepository.saveAndFlush(item);

        return ResponseEntity
                .ok(ApiResponse.builder()
                        .success(true)
                        .message(SUCCESSFUL_ITEM_EDITED)
                        .build());
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_ID_NOT_EXISTS));

        itemRepository.delete(item);

        return ResponseEntity
                .ok(ApiResponse.builder()
                        .success(true)
                        .message(SUCCESSFUL_ITEM_DELETED)
                        .build());
    }
}
