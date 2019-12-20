package bg.softuni.heroes.web.controllers;

import bg.softuni.heroes.data.enums.Slot;
import bg.softuni.heroes.data.models.Item;
import bg.softuni.heroes.data.repositories.ItemRepository;
import bg.softuni.heroes.DataSetUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
        mockMvc.perform(get("/api/items?page=0&size=5&sort=name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total", is(14)))
                .andExpect(jsonPath("$.items", hasSize(5)));
    }

    @Test
    public void when_create_withValidData_returnsValidMessage() throws Exception {
        mockMvc.perform(post("/api/items")
                .param("name", "Item")
                .param("slot", Slot.WEAPON.name())
                .param("stamina", "100")
                .param("strength", "100")
                .param("attack", "100"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is(SUCCESSFUL_ITEM_CREATED)));
    }

    @Test
    public void when_create_withInvalidSlot_shouldReturnsErrorMessage() throws Exception {
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
    public void when_create_withExistingName_shouldReturnsNameExistsMessage() throws Exception {
        mockMvc.perform(post("/api/items")
                .param("name", "mhearne0")
                .param("slot", Slot.WEAPON.name())
                .param("stamina", "100")
                .param("strength", "100")
                .param("attack", "100"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is(String.format(ITEM_EXISTS, "mhearne0"))));
    }

    @Test
    public void when_findByName_withExistingName_shouldReturnsValidObject() throws Exception {
        final String NAME = "bkitchenman4";
        final String SLOT = "HELMET";
        final int ATTACK = 73;
        final int STAMINA = 32;
        final int STRENGTH = 48;
        final int DEFENCE = 2;

        mockMvc.perform(get("/api/items/" + NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.slot", is(SLOT)))
                .andExpect(jsonPath("$.attack", is(ATTACK)))
                .andExpect(jsonPath("$.stamina", is(STAMINA)))
                .andExpect(jsonPath("$.strength", is(STRENGTH)))
                .andExpect(jsonPath("$.defence", is(DEFENCE)));
    }

    @Test
    public void when_findByName_withNonExistingName_shouldReturnsErrorMessage() throws Exception {
        final String NAME = "Non existing name";

        mockMvc.perform(get("/api/items/" + NAME))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode", is(404)))
                .andExpect(jsonPath("$.message", is(ITEM_NAME_NOT_EXISTS)));
    }

    @Test
    public void when_edit_withValidEntity_shouldReturnsValidMessage() throws Exception {
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is(SUCCESSFUL_ITEM_EDITED)));
    }


}