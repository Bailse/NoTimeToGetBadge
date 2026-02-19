package Item;

import java.util.ArrayList;
import Character.BasePlayer;

public class ItemInventory {

    private ArrayList<BaseItem> slots;

    public ItemInventory() {
        slots = new ArrayList<>();
        slots.add(null); // slot 0 Vehicle
        slots.add(null); // slot 1 EducationThing
        slots.add(null); // slot 2 HealthThing
        slots.add(null); // slot 3 MoneyThing
    }

    // ใส่ item ลงช่องที่ถูกต้องตามประเภท
    public boolean equip(BasePlayer player, BaseItem item) {

        int idx = item.getSlotIndex();
        if (idx < 0 || idx > 3)
            return false;

        BaseItem oldItem = slots.get(idx);
        if (oldItem != null) {
            oldItem.removeEffect(player);
        }

        slots.set(idx, item);
        item.applyEffect(player);
        return true;
    }

    public boolean unequip(BasePlayer player, int slotIndex) {

        if (slotIndex < 0 || slotIndex > 3)
            return false;

        BaseItem oldItem = slots.get(slotIndex);
        if (oldItem == null)
            return false;

        oldItem.removeEffect(player);
        slots.set(slotIndex, null);
        return true;
    }

    public BaseItem getItemInSlot(int slotIndex) {

        if (slotIndex < 0 || slotIndex > 3)
            return null;

        return slots.get(slotIndex);
    }

    public ArrayList<BaseItem> getSlots() {
        return slots;
    }
}
