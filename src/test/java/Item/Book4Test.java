package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Book4Test {

    @Test
    void book4Properties() {
        Book4 b = new Book4();
        assertEquals("Doctorate Book", b.getName());
        assertEquals("book4.png", b.getImage());
        assertEquals(Category.EDUCATION, b.getCategory());
    }
}
