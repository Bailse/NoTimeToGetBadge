package Screen.BuildingScreen;

import javafx.scene.control.Button;

public interface Shopable extends Normal {

    default Button createShopButton(ShopItem item, Logic.GamePane gamePane, Runnable refreshUI) {
        Button btn = new Button(item.getName() + "\n$" + item.getPrice());

        // เรียกใช้ style ที่เราย้ายมา
        applyPixelStyle(btn, item.getColor());

        btn.setOnAction(e -> {
            // Logic การซื้อ (ใส่ทีหลังได้)
            refreshUI.run();
        });

        return btn;
    }
}