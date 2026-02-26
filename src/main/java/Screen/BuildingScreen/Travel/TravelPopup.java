package Screen.BuildingScreen.Travel;


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

public class TravelPopup implements Shopable, Normal {

    // Enum สำหรับสถานที่ท่องเที่ยว
    private enum Destination implements ShopItem {
        BEACH("BEACH 🌊", 1000, "#00ccff", -50, 0),   // จ่าย 1000, เพิ่ม Stamina 50
        JAPAN("JAPAN 🗾", 5000, "#00ccff", -100, 10), // จ่าย 5000, เพิ่ม Stamina 100, ได้ Edu 10
        PARIS("PARIS 🗼", 10000, "#00ccff", -150, 20); // จ่าย 10000, เพิ่ม Stamina 150, ได้ Edu 20

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int eduGain;

        Destination(String name, int price, String color, int staminaCost, int eduGain) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.eduGain = eduGain;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            // เช็คเงิน (ระบบ Shopable มักเช็คให้ใน createShopButton แต่เช็คเผื่อไว้ได้)
            if (gamePane.getPlayerMoney() >= price) {
                gamePane.setPlayerMoney(gamePane.getPlayerMoney() - price);
                gamePane.setPlayerStamina(gamePane.getPlayerStamina() - staminaCost); // ลบด้วยค่าติดลบ = เพิ่ม
                gamePane.setPlayerEducation(gamePane.getPlayerEducation() + eduGain);
                System.out.println("Traveling to " + name);
            }
        }
    }

    public static void show(GamePane gamePane) {
        TravelPopup popup = new TravelPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // Labels สถานะ
        Label staminaLabel = new Label("STAMINA: " + gamePane.getPlayerStamina());
        Label eduLabel = new Label("EDUCATION: " + gamePane.getPlayerEducation());
        Label moneyLabel = new Label("MONEY: " + gamePane.getPlayerMoney());

        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 14px;");
        eduLabel.setStyle("-fx-text-fill: #ff66ff; -fx-font-size: 14px;");
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 14px;");

        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            eduLabel.setText("EDUCATION: " + gamePane.getPlayerEducation());
            moneyLabel.setText("MONEY: " + gamePane.getPlayerMoney());
        };

        // ใช้ Base Layout (Header สีฟ้า #00ccff)
        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "AIRPORT / TRAVEL", Color.web("#00ccff"),
                "WALK AROUND", "#00ccff",
                () -> { // กิจกรรมปุ่มขวาล่าง
                    if (gamePane.getPlayerStamina() >= 5) {
                        gamePane.setPlayerStamina(gamePane.getPlayerStamina() - 5);
                        gamePane.setPlayerEducation(gamePane.getPlayerEducation() + 1);
                        refreshUI.run();
                    }
                },
                refreshUI,
                staminaLabel, eduLabel, moneyLabel
        );

        // ส่วนปุ่มเลือกจุดหมาย
        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (Destination dest : Destination.values()) {
            Button btn = popup.createShopButton(dest, gamePane, refreshUI);
            btn.setPrefSize(220, 160);
            btn.setText(dest.getName() + "\n$" + dest.getPrice());

            // ปรับแต่งปุ่มโค้ง (Radius 15)
            String normalStyle = "-fx-background-color: #0f3460; " +
                    "-fx-border-color: #00ccff; " +
                    "-fx-border-width: 4; " +
                    "-fx-background-radius: 15; " +
                    "-fx-border-radius: 15; " +
                    "-fx-text-fill: white; " +
                    "-fx-alignment: center; " +
                    "-fx-text-alignment: center;";

            btn.setStyle(normalStyle);

            // รักษาความโค้งเมื่อเมาส์ออก
            btn.setOnMouseExited(e -> {
                btn.setStyle(normalStyle);
                btn.setEffect(null);
            });

            optionsBox.getChildren().add(btn);
        }

        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 900, 550);
        stage.setScene(scene);
        stage.showAndWait();
    }
}