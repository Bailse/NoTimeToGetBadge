package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WheyProteinTest {

    @Test
    void wheyProteinProperties() {
        WheyProtein w = new WheyProtein();
        assertEquals("Whey Protein", w.getName());
        assertEquals("WheyProtein.png", w.getImage());
        assertEquals(Category.HEALTH, w.getCategory());
    }
}
