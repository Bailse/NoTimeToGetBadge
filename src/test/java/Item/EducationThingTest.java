package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test Class for checking EducationThing
 */
public class EducationThingTest {

    @Test
    void educationThingHasEducationCategory() {
        EducationThing e = new EducationThing("Edu", "edu.png");
        assertEquals(Category.EDUCATION, e.getCategory());
    }
}
