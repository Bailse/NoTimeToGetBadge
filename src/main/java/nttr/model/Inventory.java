package nttr.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Inventory with 4 fixed category slots.
 * Slot order:
 *  0 Vehicle, 1 Education, 2 Health, 3 Money
 *
 * Rule: equipping an item of the same category REPLACES the previous one.
 */
public class Inventory {
    public static final int SLOT_COUNT = 4;

    private final ArrayList<Item> slots;

    public Inventory() {
        slots = new ArrayList<>(SLOT_COUNT);
        slots.addAll(Collections.nCopies(SLOT_COUNT, null));
    }

    public void equip(Item item) {
        int idx = slotIndex(item.getType());
        slots.set(idx, item);
    }

    public Item get(ItemType type) {
        return slots.get(slotIndex(type));
    }

    public List<Item> getSlotsView() {
        return Collections.unmodifiableList(slots);
    }

    public void applyBonuses(Player player) {
        player.resetBonuses();
        for (Item item : slots) {
            if (item != null) {
                item.applyTo(player);
            }
        }
    }

    public static int slotIndex(ItemType type) {
        if (type == ItemType.VEHICLE) {
            return 0;
        }
        if (type == ItemType.EDUCATION) {
            return 1;
        }
        if (type == ItemType.HEALTH) {
            return 2;
        }
        return 3;
    }

    public static String slotLabel(int index) {
        if (index == 0) {
            return "Vehicle";
        }
        if (index == 1) {
            return "Education";
        }
        if (index == 2) {
            return "Health";
        }
        return "Money";
    }


public void unequipSlot(int index) {
    if (index < 0 || index >= SLOT_COUNT) return;
    slots.set(index, null);
}

}
