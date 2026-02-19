package Item;

import Character.BasePlayer;

public abstract class BaseItem extends Thing {

    public BaseItem(String name, String image) {
        super(name, image);
    }

    // ตอนใส่ item เข้าช่อง
    public abstract void applyEffect(BasePlayer player);

    // ตอนถอด/เปลี่ยน item ในช่อง
    public abstract void removeEffect(BasePlayer player);

    // ชี้ว่า item นี้ควรอยู่ slot ไหน (0..3)
    public abstract int getSlotIndex();
}
