package Character;

import Item.Item;
import Item.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * "Production-grade" unit tests for BasePlayer logic.
 * Uses a lightweight test subclass to avoid UI concerns.
 */
public class BasePlayerProductionTest {

    /** Minimal player for deterministic logic testing. */
    static class TestPlayer extends BasePlayer {
        public TestPlayer(int stamina, int money, int education, int health,
                          double moneyDiscount, double educationMultiply,
                          int healthDecrease, int staminaDecrease, int happiness) {
            super(stamina, money, education, health, moneyDiscount, educationMultiply, healthDecrease, staminaDecrease, happiness);
        }
    }

    @Test
    void numericSettersClampToZeroForNegativeOrZeroValues() {
        TestPlayer p = new TestPlayer(1,1,1,1,1.0,1.0,1,1,1);

        p.setStamina(-999);
        assertEquals(0, p.getStamina());

        p.setMoney(-999);
        assertEquals(0, p.getMoney());

        p.setEducation(-999);
        assertEquals(0, p.getEducation());

        p.setHealth(-999);
        assertEquals(0, p.getHealth());

        p.setStaminaDecrease(-5);
        assertEquals(0, p.getStaminaDecrease());

        p.setHealthDecrease(-5);
        assertEquals(0, p.getHealthDecrease());

        p.setHappiness(-5);
        assertEquals(0, p.getHappiness());

        p.setMoneyDiscount(-0.5);
        assertEquals(0.0, p.getMoneyDiscount());

        p.setEducationMultiply(0.0);
        assertEquals(0.0, p.getEducationMultiply());
    }

    @Test
    void walkConsumesStaminaAndNeverGoesNegative() {
        TestPlayer p = new TestPlayer(5,0,0,0,1.0,1.0,0,10,0);

        // Without vehicle: cost = staminaDecrease (10)
        p.walk();
        assertEquals(0, p.getStamina(), "Must clamp to 0, not negative");
    }

    @Test
    void walkCostIsReducedWhenVehicleEquipped() {
        TestPlayer p = new TestPlayer(100,0,0,0,1.0,1.0,0,10,0);

        // Equip vehicle in inventory slot 2 via Item manager.
        Item inv = p.getItemManager();
        inv.addItem(new Vehicle());

        int before = p.getStamina();
        p.walk();

        // cost = staminaDecrease * 0.5 => 5
        assertEquals(before - 5, p.getStamina());
    }

    @Test
    void studyIncreasesEducationAndDecreasesHealthWithClamp() {
        TestPlayer p = new TestPlayer(0,0,0,1,1.0,2.0,10,0,0);

        p.study(10); // gained = 10 * 2.0 = 20; health decreases by 10 -> clamp to 0
        assertEquals(20, p.getEducation());
        assertEquals(0, p.getHealth());
    }

    @Test
    void buyItemAppliesDiscountAndNeverGoesNegative() {
        TestPlayer p = new TestPlayer(0,30,0,0,0.5,1.0,0,0,0);

        p.buyItem(100); // finalPrice=50 => money 30-50 -> clamp to 0
        assertEquals(0, p.getMoney());
    }

    @Test
    void maxUnlockedLevelNeverNegative() {
        TestPlayer p = new TestPlayer(0,0,0,0,1.0,1.0,0,0,0);
        p.setMaxUnlockedLevel(-3);
        assertEquals(0, p.getMaxUnlockedLevel());
    }
}
