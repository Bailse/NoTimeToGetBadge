package Screen.BuildingScreen.Travel;


import Logic.GamePane;
import Character.BasePlayer;
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

public class TravelPopup implements Shopable, Normal {

    // Enum สำหรับสถานที่ท่องเที่ยว
    private enum Destination implements ShopItem {
        // ชื่อปุ่ม, ราคา, สีขอบ, (ไม่ได้ใช้), ค่า Happiness ที่จะได้รับ
        BEACH("BEACH 🌊", 1000, "#00ccff", 20, 20),
        JAPAN("JAPAN 🗾", 5000, "#00ccff", 30, 50),
        PARIS("PARIS 🗼", 10000, "#00ccff", 40, 100);

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int happinessGain;

        Destination(String name, int price, String color, int staminaCost, int happinessGain) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.happinessGain = happinessGain;
            this.staminaCost = staminaCost;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            BasePlayer p = gamePane.getPlayer();

            if(p.getStamina() >= staminaCost && p.getMoney() >= price){
                if(p.getHappiness() < 500){
                    p.setHappiness(p.getHappiness() + happinessGain);
                    p.setStamina(p.getStamina() - staminaCost);
                    p.setMoney(p.getMoney() - price);
                    System.out.println("Traveling to " + name + " | Happiness increased by " + happinessGain);
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
        BasePlayer p = gamePane.getPlayer();

        TravelPopup popup = new TravelPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // ===== 1. Labels สถานะ (สร้างตามลำดับที่ต้องการโชว์) =====
        Label staminaLabel = new Label("STAMINA: " + p.getStamina());
        Label happinessLabel = new Label("HAPPINESS: " + p.getHappiness());
        Label moneyLabel = new Label("MONEY: $" + p.getMoney());

// ตกแต่ง Style (ปรับเป็น 18px เพื่อความชัดเจน)
        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px; -fx-font-weight: bold;");
        happinessLabel.setStyle("-fx-text-fill: #FF69B4; -fx-font-size: 18px; -fx-font-weight: bold;"); // สีชมพู
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px; -fx-font-weight: bold;");    // สีทอง

// ===== 2. ฟังก์ชัน Refresh UI (ต้องอัปเดตให้ครบทุกค่า) =====
        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + p.getStamina());
            happinessLabel.setText("HAPPINESS: " + p.getHappiness());
            moneyLabel.setText("MONEY: $" + p.getMoney());
        };

// ===== 3. เรียก Base Layout =====
        BorderPane root = popup.createBaseLayout(
                stage,
                gamePane,
                "AIRPORT / TRAVEL",
                Color.web("#00ccff"),
                null, // No action button
                null,
                null,
                refreshUI,
                staminaLabel,moneyLabel,happinessLabel
        );

        // ส่วนปุ่มเลือกจุดหมาย
        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (Destination dest : Destination.values()) {
            Button btn = popup.createShopButton(dest, gamePane, refreshUI);
            btn.setPrefSize(220, 160);
            optionsBox.getChildren().add(btn);
        }

        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 900, 550);
        stage.setScene(scene);
        stage.showAndWait();
    }
}