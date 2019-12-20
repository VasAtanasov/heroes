package bg.softuni.heroes.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class UnitTestBase {

    @BeforeEach
    private void setupTest() {
        MockitoAnnotations.initMocks(this);
        this.beforeEach();
    }

    protected void beforeEach() { }
}
