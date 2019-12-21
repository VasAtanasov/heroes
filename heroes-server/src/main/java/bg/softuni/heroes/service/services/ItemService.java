package bg.softuni.heroes.service.services;

import bg.softuni.heroes.service.models.item.ItemCreateServiceModel;
import bg.softuni.heroes.service.models.item.ItemDetailedServiceModel;
import bg.softuni.heroes.service.models.item.ItemEditServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ItemService {
    Page<ItemDetailedServiceModel> getItemsPage(Pageable pageable);

    ItemDetailedServiceModel create(ItemCreateServiceModel request);

    ItemDetailedServiceModel findById(UUID id);

    ItemDetailedServiceModel findByName(String name);

    ItemDetailedServiceModel edit(ItemEditServiceModel request);

    void delete(UUID id);
}
