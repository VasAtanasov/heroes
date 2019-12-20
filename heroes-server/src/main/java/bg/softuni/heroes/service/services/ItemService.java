package bg.softuni.heroes.service.services;

import bg.softuni.heroes.web.models.request.ItemCreateRequest;
import bg.softuni.heroes.web.models.request.ItemEditRequest;
import bg.softuni.heroes.web.models.response.item.ItemDetailsResponseModel;
import bg.softuni.heroes.web.models.response.item.ItemResponseModel;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ItemService {
    ItemResponseModel getAllItems(Pageable pageable);

    ResponseEntity<?> create(ItemCreateRequest request);

    ItemDetailsResponseModel findById(UUID id);

    ItemDetailsResponseModel findByName(String name);

    ResponseEntity<?> edit(ItemEditRequest request);

    ResponseEntity<?> delete(UUID id);
}
