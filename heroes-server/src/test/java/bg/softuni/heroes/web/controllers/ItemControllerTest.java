package bg.softuni.heroes.web.controllers;

import bg.softuni.heroes.data.enums.Slot;
import bg.softuni.heroes.data.models.Item;
import bg.softuni.heroes.data.repositories.ItemRepository;
import bg.softuni.heroes.DataSetUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static bg.softuni.heroes.constants.ResponseMessages.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Transactional
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>();

    @BeforeEach
    public void init() {
        items.addAll(DataSetUtils.getItems());
        itemRepository.saveAll(items);
        itemRepository.flush();
    }

    @Test
    public void all() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(14)))
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    @DisplayName("return successful creation message")
    void create_whenWithValidData_returnsValidMessage() throws Exception {
        mockMvc.perform(post("/api/items")
                .param("name", "Item")
                .param("slot", Slot.WEAPON.name())
                .param("stamina", "100")
                .param("strength", "100")
                .param("attack", "100"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Item")))
                .andExpect(jsonPath("$.slot", equalTo(Slot.WEAPON.name())));
    }

    @Test
    void when_create_withInvalidSlot_shouldReturnsErrorMessage() throws Exception {
        mockMvc.perform(post("/api/items")
                .param("name", "mhearne0")
                .param("slot", "Invalid slot")
                .param("stamina", "100")
                .param("strength", "100")
                .param("attack", "100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", is(404)))
                .andExpect(jsonPath("$.message", is(INVALID_SLOT)));
    }

    @Test
    void create_whenWithExistingName_shouldReturnsNameExistsMessage() throws Exception {
        mockMvc.perform(post("/api/items")
                .param("name", "mhearne0")
                .param("slot", Slot.WEAPON.name())
                .param("stamina", "100")
                .param("strength", "100")
                .param("attack", "100"))
                .andExpect(status().isConflict());
    }


    @Test
    public void findByName_whenWithExistingName_shouldReturnsValidObject() throws Exception {
        final String NAME = "bkitchenman4";
        final String SLOT = "HELMET";
        final int ATTACK = 73;
        final int STAMINA = 32;
        final int STRENGTH = 48;
        final int DEFENCE = 2;

        mockMvc.perform(get("/api/items?name=" + NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.slot", is(SLOT)))
                .andExpect(jsonPath("$.attack", is(ATTACK)))
                .andExpect(jsonPath("$.stamina", is(STAMINA)))
                .andExpect(jsonPath("$.strength", is(STRENGTH)))
                .andExpect(jsonPath("$.defence", is(DEFENCE)));
    }

    @Test
    public void findByName_whenWithNonExistingName_shouldReturnsErrorMessage() throws Exception {
        final String NAME = "Non existing name";

        mockMvc.perform(get("/api/items?name=" + NAME))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", is(404)))
                .andExpect(jsonPath("$.message", is(ITEM_NAME_NOT_EXISTS)));
    }

    @Test
    public void edit_whenWithValidEntity_shouldReturnsValidMessage() throws Exception {
        final String NAME = "bkitchenman4";

        Item item = itemRepository.findByName(NAME).orElse(Item.builder().build());

        item.setAttack(200);

        mockMvc.perform(patch("/api/items/")
                .param("id", item.getId().toString())
                .param("name", item.getName())
                .param("slot", item.getSlot().name())
                .param("stamina", Integer.toString(item.getStamina()))
                .param("strength", Integer.toString(item.getStrength()))
                .param("attack", Integer.toString(item.getAttack()))
                .param("defence", Integer.toString(item.getDefence())))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_whenWithValidUUID_shouldReturnsValidMessage() throws Exception {
        final String NAME = "bkitchenman4";

        Item item = itemRepository.findByName(NAME).orElse(Item.builder().build());

        item.setAttack(200);

        mockMvc.perform(delete("/api/items/" + item.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(ITEM_DELETED)));
    }


}