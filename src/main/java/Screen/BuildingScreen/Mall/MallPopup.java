package Screen.BuildingScreen.Mall;

import Screen.BuildingScreen.ShopItem;
import Logic.GamePane;
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

public class MallPopup implements Shopable, Normal {

    private enum MallItem implements ShopItem {
        PILLOW("PILLOW", 50, "#00ffff", 10, 0),
        TUNG_BED("TungTungSAHUR\nBED SET", 676, "#ffd700", 100, 20),
        CAR("CAR", 999, "#ff007f", 40, 5);

        private final String name;
        private final int price;
        private final String color;
        private final int staminaGain;
        private final int healthGain;

        MallItem(String name, int price, String color, int staminaGain, int healthGain) {
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
            if(gamePane.getPlayerMoney() >= price){
                gamePane.setPlayerStamina(gamePane.getPlayerStamina() + staminaGain);
                gamePane.setPlayerHealth(gamePane.getPlayerHealth() + healthGain);
                gamePane.setPlayerMoney(gamePane.getPlayerMoney() - price);
            }
        }
    }

    public static void show(GamePane gamePane) {
        MallPopup popup = new MallPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // ===== 1. เตรียม Labels =====
        Label staminaLabel = new Label("STAMINA: " + gamePane.getPlayerStamina());
        Label healthLabel = new Label("HEALTH: " + gamePane.getPlayerHealth());
        Label moneyLabel = new Label("MONEY: " + gamePane.getPlayerMoney());

        staminaLabel.setStyle("-fx-text-fill: #00ff99; -fx-font-size: 18px;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 18px;");

        // ===== 2. Refresh UI Logic =====
        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            healthLabel.setText("HEALTH: " + gamePane.getPlayerHealth());
            moneyLabel.setText("MONEY: " + gamePane.getPlayerMoney());
        };

        // ===== 3. Work Action (งานพาร์ทไทม์ในห้าง) =====
        Runnable workAction = () -> {
            if (gamePane.getPlayerStamina() >= 10) {
                gamePane.setPlayerStamina(gamePane.getPlayerStamina() - 10);
                gamePane.setPlayerMoney(gamePane.getPlayerMoney() + 20000);
                System.out.println("Part-time job at Mall... +$200");
            } else {
                System.out.println("You are too tired to work at the Mall!");
            }
        };

        // ===== 4. สร้าง Layout หลักจาก Normal Interface =====
        BorderPane root = popup.createBaseLayout(
                stage,
                gamePane,
                "MALL",
                Color.CYAN,
                "PART-TIME",     // ชื่อปุ่มขวาล่าง เปลี่ยนเป็น Part-time ให้เข้ากับห้าง
                "#00ffff",       // สีปุ่ม
                workAction,      // Logic หาเงิน
                refreshUI,
                staminaLabel, healthLabel, moneyLabel
        );

        // ===== 5. ส่วนตรงกลาง (รายการขายของ) =====
        HBox optionBox = new HBox(25);
        optionBox.setAlignment(Pos.CENTER);
        optionBox.setPadding(new Insets(40));

        for (MallItem item : MallItem.values()) {
            Button btn = popup.createShopButton(item, gamePane, refreshUI);
            btn.setPrefSize(180, 140);
            optionBox.getChildren().add(btn);
        }

        root.setCenter(optionBox);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}