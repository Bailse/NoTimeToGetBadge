package Character;

import org.junit.jupiter.api.Test;
import testsupport.JavaFxTestBase;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test Class for checking Nerd method
 */
public class NerdTest extends JavaFxTestBase {

    @Test
    void constructorSetsExpectedBaselineStats() {
        Nerd p = new Nerd();
        assertEquals(200, p.getStamina());
        assertEquals(1000, p.getMoney());
        assertEquals(30, p.getEducation());
        assertEquals(0, p.getHealth());

        assertEquals(2, p.getHealthDecrease());
        assertEquals(4, p.getStaminaDecrease());
        assertEquals(25, p.getHappiness());
    }

    @Test
    void imagesAndImagePathArePresent() {
        Nerd p = new Nerd();
        assertNotNull(p.getImagePath(), "imagePath should be set");
        assertNotNull(p.getImgUp());
        assertNotNull(p.getImgDown());
        assertNotNull(p.getImgLeft());
        assertNotNull(p.getImgRight());
    }


}
