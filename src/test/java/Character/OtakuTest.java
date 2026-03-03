package Character;

import org.junit.jupiter.api.Test;
import testsupport.JavaFxTestBase;

import static org.junit.jupiter.api.Assertions.*;

public class OtakuTest extends JavaFxTestBase {

    @Test
    void constructorSetsExpectedBaselineStats() {
        Otaku p = new Otaku();
        assertEquals(200, p.getStamina());
        assertEquals(1500, p.getMoney());
        assertEquals(5, p.getEducation());
        assertEquals(0, p.getHealth());
        assertEquals(3, p.getHealthDecrease());
        assertEquals(4, p.getStaminaDecrease());
        assertEquals(45, p.getHappiness());
    }

    @Test
    void imagesAndImagePathArePresent() {
        Otaku p = new Otaku();
        assertNotNull(p.getImagePath(), "imagePath should be set");
        assertNotNull(p.getImgUp());
        assertNotNull(p.getImgDown());
        assertNotNull(p.getImgLeft());
        assertNotNull(p.getImgRight());
    }


}
