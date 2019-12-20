package bg.softuni.heroes.service.services;

import bg.softuni.heroes.service.models.items.ItemCreateServiceModel;
import bg.softuni.heroes.service.models.items.ItemDetailedServiceModel;
import bg.softuni.heroes.service.models.items.ItemEditServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ItemService {
    Page<ItemDetailedServiceModel> getItemsPage(Pageable pageable);

    ItemDetailedServiceModel create(ItemCreateServiceModel request);

    ItemDetailedServiceModel findById(UUID id);

    ItemDetailedServiceModel findByName(String name);

    ItemDetailedServiceModel edit(ItemEditServiceModel request);

    void delete(UUID id);
}
