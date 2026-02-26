package Screen.BuildingScreen.Park;


import Logic.GamePane;
import Screen.BuildingScreen.Normal;
import Screen.BuildingScreen.ShopItem;
import Screen.BuildingScreen.Shopable;
import Screen.BuildingScreen.Travel.TravelPopup;
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

public class ParkPopup implements Shopable, Normal {

    // Enum สำหรับกิจกรรมในสวนสาธารณะ
    private enum ParkAction implements ShopItem {
        WALK("WALK 🚶\n-7 ⚡", 0, "#00cc66", 7, 5,2),
        RELAX("RELAX 🍃\n-5 ⚡", 0, "#00cc66", 5, 5,0);

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int happinessGain;
        private final int healthGain;

        ParkAction(String name, int price, String color, int staminaCost, int happinessGain, int healthGain) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.happinessGain = happinessGain;
            this.healthGain = healthGain;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            // ถ้าค่า staminaCost เป็นบวก คือต้องใช้ค่าพลัง (เช็คว่าพอไหม)
            if (gamePane.getPlayerStamina() >= staminaCost) {
                gamePane.setPlayerStamina(gamePane.getPlayerStamina() - staminaCost);
                gamePane.setPlayerHappiness(gamePane.getPlayerHappiness() + happinessGain);
                gamePane.setPlayerHealth(gamePane.getPlayerHealth() + healthGain);
            }
        }
    }

    public static void show(GamePane gamePane) {
        ParkPopup popup = new ParkPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // Labels สำหรับแสดงสถานะ (Stamina, Edu, Money)
        // ===== Labels สำหรับแสดงสถานะ =====
        Label staminaLabel = new Label("STAMINA: " + gamePane.getPlayerStamina());
        Label happinessLabel = new Label("HAPPINESS: " + gamePane.getPlayerHappiness());
        Label healthLabel = new Label("HEALTH: " + gamePane.getPlayerHealth());

        // ตกแต่ง Style
        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px; -fx-font-weight: bold;");
        happinessLabel.setStyle("-fx-text-fill: #FF69B4; -fx-font-size: 18px; -fx-font-weight: bold;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px; -fx-font-weight: bold;");

        // ฟังก์ชัน Refresh UI
        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            happinessLabel.setText("HAPPINESS: " + gamePane.getPlayerHappiness());
            healthLabel.setText("HEALTH: " + gamePane.getPlayerHealth());
        };

        // ใช้ createBaseLayout จาก Interface Normal
        // ส่ง moneyLabel เป็นตัวสุดท้าย เพื่อให้มันไปปรากฏที่มุมซ้ายบน
        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "PARK", Color.web("#00cc66"),
                null, null, null,
                refreshUI,
                staminaLabel, happinessLabel,healthLabel
        );

        // ส่วนปุ่มตรงกลาง
        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (ParkAction park : ParkAction.values()) {
            Button btn = popup.createShopButton(park, gamePane, refreshUI);
            btn.setPrefSize(220, 160);
            optionsBox.getChildren().add(btn);
        }

        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}