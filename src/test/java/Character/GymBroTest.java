package Character;

import org.junit.jupiter.api.Test;
import testsupport.JavaFxTestBase;

import static org.junit.jupiter.api.Assertions.*;

public class GymBroTest extends JavaFxTestBase {

    @Test
    void constructorSetsExpectedBaselineStats() {
        GymBro p = new GymBro();
        assertEquals(200, p.getStamina());
        assertEquals(4500, p.getMoney());
        assertEquals(5, p.getEducation());
        assertEquals(100, p.getHealth());
        assertEquals(1.0, p.getMoneyDiscount(), 1e-9);
        assertEquals(1.0, p.getEducationMultiply(), 1e-9);
        assertEquals(1, p.getHealthDecrease());
        assertEquals(2, p.getStaminaDecrease());
        assertEquals(100, p.getHappiness());
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

    @Test
    void statsNeverNegativeAfterExtremeOperations() {
        GymBro p = new GymBro();
        // drain stamina heavily
        for (int i = 0; i < 1000; i++) {
            p.walk();
        }
        assertTrue(p.getStamina() >= 0);

        // buy very expensive item
        p.buyItem(Integer.MAX_VALUE);
        assertTrue(p.getMoney() >= 0);

        // study with huge health decrease effect (by repeating)
        for (int i = 0; i < 100; i++) {
            p.study(1000);
        }
        assertTrue(p.getHealth() >= 0);
        assertTrue(p.getEducation() >= 0);
    }
}
