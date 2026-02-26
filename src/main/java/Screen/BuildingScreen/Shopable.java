package Screen.BuildingScreen;

import javafx.scene.control.Button;

public interface Shopable extends Normal {

    default Button createShopButton(ShopItem item, Logic.GamePane gamePane, Runnable refreshUI) {
        String buttonText = (item.getPrice() > 0)
                ? item.getName() + "\n$" + item.getPrice()
                : item.getName();

        Button btn = new Button(buttonText);

        // เรียกใช้ style ที่เราย้ายมา (ขอบโค้ง 15 และสีตาม Item)
        applyPixelStyle(btn, item.getColor());

        btn.setOnAction(e -> {
            // 1. ตรวจสอบเงื่อนไขการซื้อ (เช่น เงินพอไหม)
            if (gamePane.getPlayerMoney() >= item.getPrice()) {

                // 2. เรียกใช้ execute เพื่อรัน Logic ของไอเทม (เปลี่ยนค่า Status จริงๆ)
                item.execute(gamePane);


                // 4. อัปเดตหน้าจอทันทีหลังจากเปลี่ยนค่าเสร็จ
                refreshUI.run();

                System.out.println("Purchased: " + item.getName());
            } else {
                System.out.println("Not enough money!");
            }
        });

        return btn;
    }
}