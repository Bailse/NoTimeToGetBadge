package Screen.BuildingScreen.Gym;

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

public class GymPopup implements Shopable, Normal {

    private enum GymService implements ShopItem {
        WORKOUT("WORK OUT", 25, "#e94560", 5, 5),
        POWER("POWER", 100, "#e94560", 10, 15),
        BEAST("BEAST", 250, "#e94560", 20, 40),
        PROTEIN("PROTEIN", 90, "#e94560", 5, 10);

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int healthGain;

        GymService(String name, int price, String color, int staminaCost, int healthGain) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.healthGain = healthGain;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            // Logic การซื้อของในยิม
            if (gamePane.getPlayerStamina() >= staminaCost) {
                gamePane.setPlayerStamina(gamePane.getPlayerStamina() - staminaCost);
                gamePane.setPlayerHealth(gamePane.getPlayerHealth() + healthGain);
            }
        }
    }

    public static void show(GamePane gamePane) {
        GymPopup popup = new GymPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // ===== 1. เตรียม Labels สำหรับ Status =====
        Label staminaLabel = new Label("STAMINA: " + gamePane.getPlayerStamina());
        Label moneyLabel = new Label("$" + gamePane.getPlayerMoney());
        Label healthLabel = new Label("HEALTH: " + gamePane.getPlayerHealth());

        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px;");

        // ===== 2. ฟังก์ชัน Refresh UI (ส่งให้ Shopable และปุ่ม WORK) =====
        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            moneyLabel.setText("MONEY: " + gamePane.getPlayerMoney());
            healthLabel.setText("HEALTH: " + gamePane.getPlayerHealth());
        };

        // ===== 3. Logic สำหรับปุ่ม WORK (ล่างขวา) =====
        Runnable workAction = () -> {
            if (gamePane.getPlayerStamina() >= 20) {
                gamePane.setPlayerStamina(gamePane.getPlayerStamina() - 20);
                gamePane.setPlayerMoney(gamePane.getPlayerMoney() + 100);
                System.out.println("Working as Trainer... +$100");
            } else {
                System.out.println("Not enough stamina to work!");
            }
        };

        // ===== 4. สร้าง Layout หลักจาก Interface Normal =====
        // createBaseLayout จะสร้าง Header, Footer, ปุ่ม Exit และปุ่ม Work ให้เสร็จสรรพ
        BorderPane root = popup.createBaseLayout(
                stage,
                gamePane,
                "GYM",
                Color.web("#00FFAA"),
                "WORK",          // ข้อความปุ่มขวาล่าง
                "#00FFAA",       // สีปุ่มขวาล่าง
                workAction,      // Logic หาเงิน
                refreshUI,       // ฟังก์ชันอัปเดตหน้าจอ
                staminaLabel, healthLabel, moneyLabel // Status Labels
        );

        // ===== 5. สร้างส่วนตรงกลาง (Shop Items) =====
        HBox optionsBox = new HBox(25);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(40));

        for (GymService service : GymService.values()) {
            Button btn = popup.createShopButton(service, gamePane, refreshUI);
            btn.setPrefSize(180, 140);
            optionsBox.getChildren().add(btn);
        }

        // เอา Shop Items ไปใส่ตรงกลางของ Layout ที่ได้มา
        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}