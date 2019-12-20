package bg.softuni.heroes.service.services.impl;

import bg.softuni.heroes.DataSetUtils;
import bg.softuni.heroes.ModelMapperFactory;
import bg.softuni.heroes.base.UnitTestBase;
import bg.softuni.heroes.data.models.Item;
import bg.softuni.heroes.data.repositories.ItemRepository;
import bg.softuni.heroes.service.services.ItemService;
import bg.softuni.heroes.web.exception.ResourceNotFoundException;
import bg.softuni.heroes.web.models.response.item.ItemDetailsResponseModel;
import bg.softuni.heroes.web.models.response.item.ItemResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

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
    void getAllItems_whenWithItems_shouldReturnCollection() {
        Page<Item> page = DataSetUtils.getItemsPage();
        PageRequest pageRequest = PageRequest.of(1, page.getNumberOfElements());

        when(itemRepository.findAll(pageRequest))
                .thenReturn(page);

        ItemResponseModel itemResponseModel = itemService.getAllItems(pageRequest);

        assertThat(itemResponseModel.getTotal(), equalTo(page.getTotalElements()));
    }

    @Test
    void findByName_whenNonExistingName_shouldThrow() {
        when(itemRepository.findByName(NON_EXISTING_NAME))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> itemService.findByName(NON_EXISTING_NAME)
        );
    }

    @Test
    void findByName_whenExistingName_shouldReturnModel() {
        Item item = Item.builder()
                .name(NAME)
                .build();

        when(itemRepository.findByName(NAME))
                .thenReturn(Optional.of(item));

        ItemDetailsResponseModel model = itemService.findByName(NAME);

        assertThat(model.getName(), equalTo(NAME));
    }
}