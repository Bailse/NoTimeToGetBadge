package Item;

import java.util.ArrayList;

public class Item {
    private ArrayList<BaseItem> inventory;

    public Item() {
        inventory = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            inventory.add(null);
        }
    }

    public void addItem(BaseItem item) {
        if (item instanceof HealthThing) {
            inventory.set(0, item);
        } else if (item instanceof EducationThing) {
            inventory.set(1, item);
        } else if (item instanceof Vehicle) {
            inventory.set(2, item);
        }
    }

    public ArrayList<BaseItem> getInventory() {
        return inventory;
    }
}