package Character;

import org.junit.jupiter.api.Test;
import testsupport.JavaFxTestBase;

import static org.junit.jupiter.api.Assertions.*;

public class NormalGuyTest extends JavaFxTestBase {

    @Test
    void constructorSetsExpectedBaselineStats() {
        NormalGuy p = new NormalGuy();
        assertEquals(200, p.getStamina());
        assertEquals(500, p.getMoney());
        assertEquals(5, p.getEducation());
        assertEquals(10, p.getHealth());
        assertEquals(3, p.getHealthDecrease());
        assertEquals(3, p.getStaminaDecrease());
        assertEquals(35, p.getHappiness());
    }

    @Test
    void imagesAndImagePathArePresent() {
        NormalGuy p = new NormalGuy();
        assertNotNull(p.getImagePath(), "imagePath should be set");
        assertNotNull(p.getImgUp());
        assertNotNull(p.getImgDown());
        assertNotNull(p.getImgLeft());
        assertNotNull(p.getImgRight());
    }


}
