package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test Class for checking Book1Test
 */
public class Book1Test {

    @Test
    void book1Properties() {
        Book1 b = new Book1();
        assertEquals("High School Book", b.getName());
        assertEquals("Item/book1.png", b.getImage());
        assertEquals(Category.EDUCATION, b.getCategory());
    }
}
