package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Book3Test {

    @Test
    void book3Properties() {
        Book3 b = new Book3();
        assertEquals("Master Book", b.getName());
        assertEquals("book3.png", b.getImage());
        assertEquals(Category.EDUCATION, b.getCategory());
    }
}
