package bg.softuni.heroes.service.services.impl;

import bg.softuni.heroes.service.services.ItemService;
import bg.softuni.heroes.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static bg.softuni.heroes.constants.ResponseMessages.ITEM_NAME_NOT_EXISTS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Transactional
class ItemServiceImplIT {

    @Autowired
    private ItemService itemService;

    @ParameterizedTest(name = "#{index} - Name is {0}")
    @NullAndEmptySource
    @DisplayName("Throws exception when item name null or empty string.")
    void findByName_whenNullOrEmptyName_shouldThrow(String name) {
        Throwable error = assertThrows(
                ResourceNotFoundException.class,
                () -> itemService.findByName(name)
        );
        assertThat(error.getMessage(), equalTo(ITEM_NAME_NOT_EXISTS));
    }
}