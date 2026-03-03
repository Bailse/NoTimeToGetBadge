package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test Class for checking HealthThing
 */
public class HealthThingTest {

    @Test
    void healthThingHasHealthCategory() {
        HealthThing h = new HealthThing("Hp", "hp.png");
        assertEquals(Category.HEALTH, h.getCategory());
    }
}
