package bg.softuni.heroes.service.services.impl;

import bg.softuni.heroes.data.enums.Slot;
import bg.softuni.heroes.data.models.Item;
import bg.softuni.heroes.data.repositories.ItemRepository;
import bg.softuni.heroes.service.models.item.ItemCreateServiceModel;
import bg.softuni.heroes.service.models.item.ItemDetailedServiceModel;
import bg.softuni.heroes.service.models.item.ItemEditServiceModel;
import bg.softuni.heroes.service.services.ItemService;
import bg.softuni.heroes.util.EnumUtils;
import bg.softuni.heroes.util.ModelMapperWrapper;
import bg.softuni.heroes.web.exception.ResourceDuplicateException;
import bg.softuni.heroes.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static bg.softuni.heroes.constants.ResponseMessages.*;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapperWrapper modelMapper;

    @Override
    public Page<ItemDetailedServiceModel> getItemsPage(Pageable pageable) {

        Page<Item> items = itemRepository.findAll(pageable);

        return items.map(item -> modelMapper.map(item, ItemDetailedServiceModel.class));
    }

    @Override
    public ItemDetailedServiceModel create(ItemCreateServiceModel createModel) {

        EnumUtils.fromString(createModel.getSlot(), Slot.class)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_SLOT));

        if (itemRepository.findByName(createModel.getName()).isPresent()) {
            throw new ResourceDuplicateException(String.format(ITEM_EXISTS, createModel.getName()));
        }

        Item item = modelMapper.map(createModel, Item.class);
        itemRepository.saveAndFlush(item);

        return modelMapper.map(item, ItemDetailedServiceModel.class);
    }

    @Override
    public ItemDetailedServiceModel findById(UUID id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_ID_NOT_EXISTS));

        return modelMapper.map(item, ItemDetailedServiceModel.class);
    }

    @Override
    public ItemDetailedServiceModel findByName(String name) {

        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_NAME_NOT_EXISTS));

        return modelMapper.map(item, ItemDetailedServiceModel.class);
    }

    @Override
    public ItemDetailedServiceModel edit(ItemEditServiceModel editModel) {

        EnumUtils.fromString(editModel.getSlot(), Slot.class)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_SLOT));

        Item item = itemRepository.findById(editModel.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_ID_NOT_EXISTS));

        BeanUtils.copyProperties(editModel, item);
        itemRepository.saveAndFlush(item);

        return modelMapper.map(item, ItemDetailedServiceModel.class);
    }

    @Override
    public void delete(UUID id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_ID_NOT_EXISTS));

        itemRepository.delete(item);
    }
}
