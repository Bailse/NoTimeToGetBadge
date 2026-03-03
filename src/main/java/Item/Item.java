package Item;

import java.util.ArrayList;
/**
 * this Class use for store item in player
 */
public class Item {
    private ArrayList<BaseItem> inventory;

    /**
     * constructor that initialize Item arraylist 3 slot
     */

    public Item() {
        inventory = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            inventory.add(null);
        }
    }

    /**
     * add item method like HealthThing , EducationThing , Vehicle
     */

    public void addItem(BaseItem item) {
        if (item instanceof HealthThing) {
            inventory.set(0, item);
        } else if (item instanceof EducationThing) {
            inventory.set(1, item);
        } else if (item instanceof Vehicle) {
            inventory.set(2, item);
        }
    }

    /**
     * get Inventory
     */

    public ArrayList<BaseItem> getInventory() {
        return inventory;
    }
}