package bg.softuni.heroes.service.services.impl;

import bg.softuni.heroes.DataSetUtils;
import bg.softuni.heroes.ModelMapperFactory;
import bg.softuni.heroes.base.UnitTestBase;
import bg.softuni.heroes.data.models.Item;
import bg.softuni.heroes.data.repositories.ItemRepository;
import bg.softuni.heroes.service.models.item.ItemDetailedServiceModel;
import bg.softuni.heroes.service.services.ItemService;
import bg.softuni.heroes.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static bg.softuni.heroes.constants.ResponseMessages.ITEM_NAME_NOT_EXISTS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ItemServiceImplTest extends UnitTestBase {

    private static final String NON_EXISTING_NAME = "Non existing name";
    private static final String NAME = "Item";

    @Mock
    private ItemRepository itemRepository;

    private ItemService itemService;

    @Override
    protected void beforeEach() {
        itemService = new ItemServiceImpl(itemRepository, ModelMapperFactory.getModelMapper());
    }

    @Test
    @DisplayName("Retrieve page of items.")
    void getAllItems_whenWithItems_shouldReturnCollection() {
        Page<Item> page = DataSetUtils.getItemsPage();
        PageRequest pageRequest = PageRequest.of(1, page.getNumberOfElements());

        when(itemRepository.findAll(pageRequest))
                .thenReturn(page);

        Page<ItemDetailedServiceModel> itemsPage = itemService.getItemsPage(pageRequest);

        assertThat(itemsPage.getTotalElements(), equalTo(page.getTotalElements()));
    }

    @Test
    @DisplayName("Throws exception when item name does not exist in database.")
    void findByName_whenNonExistingName_shouldThrow() {

        when(itemRepository.findByName(NON_EXISTING_NAME))
                .thenReturn(Optional.empty());

        Throwable error = assertThrows(
                ResourceNotFoundException.class,
                () -> itemService.findByName(NON_EXISTING_NAME)
        );

        assertThat(error.getMessage(), equalTo(ITEM_NAME_NOT_EXISTS));
    }

    @Test
    @DisplayName("Getting existing item by name.")
    void findByName_whenExistingName_shouldReturnModel() {
        Item item = Item.builder()
                .name(NAME)
                .build();

        when(itemRepository.findByName(NAME))
                .thenReturn(Optional.of(item));

        ItemDetailedServiceModel model = itemService.findByName(NAME);

        assertThat(model.getName(), equalTo(NAME));
    }
}