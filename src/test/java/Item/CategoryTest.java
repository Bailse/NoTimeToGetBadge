package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    void enumHasExpectedValues() {
        Category[] values = Category.values();
        assertEquals(4, values.length);
        assertNotNull(Category.EDUCATION);
        assertNotNull(Category.HEALTH);
        assertNotNull(Category.VEHICLE);
        assertNotNull(Category.MONEY);
    }

    @Test
    void jfrCategoryMethodsAreSafeToCall() {
        // These are implemented to satisfy the interface; just ensure no crash.
        assertNotNull(Category.EDUCATION.value());
        assertDoesNotThrow(() -> Category.EDUCATION.annotationType());
    }
}
