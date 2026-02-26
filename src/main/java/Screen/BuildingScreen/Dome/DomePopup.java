package Screen.BuildingScreen.Dome;


import Logic.GamePane;
import Screen.BuildingScreen.Normal;
import Screen.BuildingScreen.ShopItem;
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

public class DomePopup implements Shopable, Normal {

    // ใช้ Enum เพื่อจัดการกิจกรรมในหอพัก โดยอ้างอิง ShopItem (เพื่อให้ใช้ createShopButton ได้)
    private enum DomeAction implements ShopItem {
        SLEEP("SLEEP 💤\n-10 ⚡", 0, "#ffaa00", 10, 10),  // เพิ่ม Stamina 40, เพิ่ม Happiness 5
        RELAX("RELAX 🎮\n-15 ⚡", 0, "#ff66ff", 15, 15); // เพิ่ม Stamina 15, เพิ่ม Happiness 15

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int happinessGain;

        DomeAction(String name, int price, String color, int staminaCost, int happinessGain) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.happinessGain = happinessGain;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getPrice() {
            return price;
        }

        @Override
        public String getColor() {
            return color;
        }

        @Override
        public void execute(GamePane gamePane) {
            if (gamePane.getPlayerStamina() >= staminaCost) {
                if(gamePane.getPlayerHappiness() < 500){
                    gamePane.setPlayerStamina(gamePane.getPlayerStamina() - staminaCost);
                    gamePane.setPlayerHappiness(gamePane.getPlayerHappiness() + happinessGain);
                }
                else {
                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    alert.setTitle("Happiness Status");
                    alert.setHeaderText(null);
                    alert.setContentText("Your Happiness is already full! (Max: 500)");
                    alert.showAndWait();
                }
            }
        }
    }

    public static void show(GamePane gamePane) {
        DomePopup popup = new DomePopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // ===== Labels สำหรับแสดงสถานะ =====
        Label staminaLabel = new Label("STAMINA: " + gamePane.getPlayerStamina());
        Label happinessLabel = new Label("HAPPINESS: " + gamePane.getPlayerHappiness());

        // ตกแต่ง Style
        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px; -fx-font-weight: bold;");
        happinessLabel.setStyle("-fx-text-fill: #FF69B4; -fx-font-size: 18px; -fx-font-weight: bold;"); // สีชมพูสำหรับความสุข

        // ฟังก์ชัน Refresh UI
        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            happinessLabel.setText("HAPPINESS: " + gamePane.getPlayerHappiness());
        };

        // ใช้ createBaseLayout จาก Interface Normal
        // ส่ง moneyLabel เป็นตัวสุดท้าย เพื่อให้มันไปปรากฏที่มุมซ้ายบน
        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "DORMITORY", Color.web("#ffaa00"),
                null, null, null,
                refreshUI,
                staminaLabel, happinessLabel
        );

        // ส่วนกลาง: สร้างปุ่มกิจกรรม
        HBox optionsBox = new HBox(30);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (DomeAction dome : DomeAction.values()) {
            Button btn = popup.createShopButton(dome, gamePane, refreshUI);
            btn.setPrefSize(220, 160);
            optionsBox.getChildren().add(btn);
        }

        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 900, 550);
        stage.setScene(scene);
        stage.showAndWait();
    }
}