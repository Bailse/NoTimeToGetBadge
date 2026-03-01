package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseItemTest {

    @Test
    void constructorSetsFieldsAndDefaults() {
        BaseItem item = new BaseItem("X", "x.png", Category.HEALTH);
        assertEquals("X", item.getName());
        assertEquals("x.png", item.getImage());
        assertEquals(Category.HEALTH, item.getCategory());
        assertFalse(item.isActive(), "New items should default to inactive");
    }

    @Test
    void settersUpdateState() {
        BaseItem item = new BaseItem("A", "a.png", Category.EDUCATION);

        item.setName("B");
        item.setImage("b.png");
        item.setActive(true);
        item.setCategory(Category.VEHICLE);

        assertEquals("B", item.getName());
        assertEquals("b.png", item.getImage());
        assertTrue(item.isActive());
        assertEquals(Category.VEHICLE, item.getCategory());
    }
}
