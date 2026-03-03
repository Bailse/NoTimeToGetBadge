package Character;

import org.junit.jupiter.api.Test;
import testsupport.JavaFxTestBase;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test Class for checking Gymbro method
 */

public class GymBroTest extends JavaFxTestBase {

    @Test
    void constructorSetsExpectedBaselineStats() {
        GymBro p = new GymBro();
        assertEquals(200, p.getStamina());
        assertEquals(500, p.getMoney());
        assertEquals(0, p.getEducation());
        assertEquals(25, p.getHealth());

        assertEquals(1, p.getHealthDecrease());
        assertEquals(2, p.getStaminaDecrease());
        assertEquals(50, p.getHappiness());
    }

    @Test
    void imagesAndImagePathArePresent() {
        GymBro p = new GymBro();
        assertNotNull(p.getImagePath(), "imagePath should be set");
        assertNotNull(p.getImgUp());
        assertNotNull(p.getImgDown());
        assertNotNull(p.getImgLeft());
        assertNotNull(p.getImgRight());
    }


}
