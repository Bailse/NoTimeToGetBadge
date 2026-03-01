package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Book2Test {

    @Test
    void book2Properties() {
        Book2 b = new Book2();
        assertEquals("Bachelor Book", b.getName());
        assertEquals("book2.png", b.getImage());
        assertEquals(Category.EDUCATION, b.getCategory());
    }
}
