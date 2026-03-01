package Screen.BuildingScreen.Restaurant;

import Logic.GameSession;
import Screen.BuildingScreen.ShopItem;
import Logic.GamePane;
import Character.BasePlayer;
import Screen.BuildingScreen.Normal;
import Screen.BuildingScreen.Shopable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static Screen.UI.ToastUtil.showToast;

public class RestaurantPopup implements Shopable, Normal {

    private enum FoodMenu implements ShopItem {
        PAD_THAI("PAD THAI", 80, "#FFD700", 20, 5),
        KRAPAO("PAD KRAPAO", 120, "#FF4500", 35, 10),
        TOM_YUM("TOM YUM KUNG", 250, "#FF0000", 60, 20);

        private final String name;
        private final int price;
        private final String color;
        private final int staminaGain;
        private final int healthGain;

        FoodMenu(String name, int price, String color, int staminaGain, int healthGain) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaGain = staminaGain;
            this.healthGain = healthGain;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            BasePlayer p = gamePane.getPlayer();
            p.setStamina((int)p.getStamina() + staminaGain);
            p.setHealth((int)p.getHealth() + healthGain);
        }
    }

    public static void show(GamePane gamePane) {
        BasePlayer p = gamePane.getPlayer();

        RestaurantPopup popup = new RestaurantPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // ===== 1. เตรียม Labels =====
        Label staminaLabel = new Label("STAMINA: " + p.getStamina());
        Label healthLabel = new Label("HEALTH: " + p.getHealth());
        Label moneyLabel = new Label("MONEY: " + p.getMoney());

        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px;");

        // ===== 2. Refresh UI Logic =====
        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + p.getStamina());
            healthLabel.setText("HEALTH: " + p.getHealth());
            moneyLabel.setText("MONEY: " + p.getMoney());
        };

        // ===== 3. Work Action (ล้างจานในร้านอาหาร) =====
        Runnable workAction = () -> {
            int staminaCost = 10;
            int moneyGain = 200;

            // 1. สั่งให้ทำงาน และเก็บผลลัพธ์ (boolean)
            boolean isSuccess = p.work(staminaCost, moneyGain);

            if (isSuccess) {
                // 2. ถ้าสำเร็จ: โชว์ Toast ปกติ (ไม่กะพริบ = false)
                showToast("💦 Dishwashing: +$" + moneyGain, "white", 300, 50, false);
            } else {
                // 3. ถ้าพลังหมด: โชว์ Toast กะพริบเตือน (กะพริบ = true)
                showToast("❌ NOT ENOUGH STAMINA!", "#ff4d4d", 300, 50, true);
            }

            // 4. อัปเดต UI ตามปกติ
            gamePane.notifyUpdate();
            refreshUI.run();
        };

        // ===== 4. สร้าง Layout หลักจาก Normal Interface =====
        BorderPane root = popup.createBaseLayout(
                stage,
                gamePane,
                "RESTAURANT",
                Color.ORANGE,
                "WASH DISH",    // ปุ่มขวาล่าง เปลี่ยนเป็นชื่อเก๋ๆ ว่าล้างจาน
                "#ffa500",      // สีส้ม
                workAction,
                refreshUI,
                staminaLabel, healthLabel, moneyLabel
        );

        // ===== 5. ส่วนตรงกลาง (เมนูอาหาร) =====
        HBox foodBox = new HBox(25);
        foodBox.setAlignment(Pos.CENTER);
        foodBox.setPadding(new Insets(40));

        for (FoodMenu food : FoodMenu.values()) {
            Button btn = popup.createShopButton(food, gamePane, refreshUI);
            btn.setPrefSize(180, 140);
            foodBox.getChildren().add(btn);
        }

        root.setCenter(foodBox);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}