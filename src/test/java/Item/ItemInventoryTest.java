package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemInventoryTest {

    @Test
    void inventoryStartsWithFixedSlotsAndNulls() {
        Item inv = new Item();
        assertNotNull(inv.getInventory());
        assertEquals(3, inv.getInventory().size());
        assertNull(inv.getInventory().get(0));
        assertNull(inv.getInventory().get(1));
        assertNull(inv.getInventory().get(2));
    }

    @Test
    void addItemPlacesCorrectItemInCorrectSlot() {
        Item inv = new Item();

        WheyProtein hp = new WheyProtein();
        inv.addItem(hp);
        assertSame(hp, inv.getInventory().get(0));
        assertNull(inv.getInventory().get(1));
        assertNull(inv.getInventory().get(2));

        Book1 edu = new Book1();
        inv.addItem(edu);
        assertSame(hp, inv.getInventory().get(0));
        assertSame(edu, inv.getInventory().get(1));
        assertNull(inv.getInventory().get(2));

        Vehicle car = new Vehicle();
        inv.addItem(car);
        assertSame(hp, inv.getInventory().get(0));
        assertSame(edu, inv.getInventory().get(1));
        assertSame(car, inv.getInventory().get(2));
    }

    @Test
    void addNullDoesNotCrashOrChangeInventory() {
        Item inv = new Item();
        assertDoesNotThrow(() -> inv.addItem(null));
        assertEquals(3, inv.getInventory().size());
        assertNull(inv.getInventory().get(0));
        assertNull(inv.getInventory().get(1));
        assertNull(inv.getInventory().get(2));
    }

    @Test
    void addingSameCategoryOverwritesOnlyThatSlot() {
        Item inv = new Item();

        WheyProtein hp1 = new WheyProtein();
        WheyProtein hp2 = new WheyProtein();
        inv.addItem(hp1);
        inv.addItem(hp2);

        assertSame(hp2, inv.getInventory().get(0));
        assertNull(inv.getInventory().get(1));
        assertNull(inv.getInventory().get(2));
    }
}
