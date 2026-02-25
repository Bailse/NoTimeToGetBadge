package Item;
import java.util.ArrayList;

public class Item {
    // Arraylist ล๊อค 4 ช่อง (1:Vehicle, 2:Education, 3:Health, 4:Money)
    private ArrayList<BaseItem> inventory;

    public Item() {
        inventory = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            inventory.add(null); // เริ่มต้นเป็นช่องว่าง
        }
    }

    // Method สำหรับเพิ่มไอเทมลงในช่องเฉพาะของมัน
    public void addItem(BaseItem item) {
        if (item instanceof Vehicle) {
            inventory.set(0, item);
        } else if (item instanceof EducationThing) {
            inventory.set(1, item);
        } else if (item instanceof HealthThing) {
            inventory.set(2, item);
        } else if (item instanceof MoneyThing) {
            inventory.set(3, item);
        }
    }

    // สำหรับ GUI: ดึงรูปภาพไปแสดงตาม Slot
    public String getImagePath(int index) {
        if (inventory.get(index) != null) {
            return inventory.get(index).getImage();
        }
        return "empty_slot.png";
    }

    // สำหรับ GUI: เช็คสถานะ Active เพื่อเปลี่ยนสีปุ่มหรือขอบ
    public boolean isSlotActive(int index) {
        return inventory.get(index) != null && inventory.get(index).isActive();
    }

    public ArrayList<BaseItem> getInventory() { return inventory; }
}