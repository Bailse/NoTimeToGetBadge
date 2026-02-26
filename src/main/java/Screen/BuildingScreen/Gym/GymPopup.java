package Screen.BuildingScreen.Gym;

import Screen.BuildingScreen.ShopItem;
import Logic.GamePane;
import Screen.BuildingScreen.Normal;
import Screen.BuildingScreen.Shopable;
import javafx.application.Platform;
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
        WORKOUT("WORK OUT", 25, "#e94560", 5, 5, false),
        POWER("POWER", 100, "#e94560", 10, 15, false),
        BEAST("BEAST", 250, "#e94560", 20, 40, false),
        PROTEIN("WHEY\nPROTEIN", 90, "#e94560", 0, 0, true);

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int healthGain;
        private final boolean isUnique;
        private boolean isSoldOut = false;

        GymService(String name, int price, String color, int staminaCost, int healthGain, boolean isUnique) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.healthGain = healthGain;
            this.isUnique = isUnique;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            if (gamePane.getPlayerMoney() < this.price) return;

            if (this == PROTEIN) {
                if (!isSoldOut) {
                    gamePane.setPlayerMoney(gamePane.getPlayerMoney() - price);
                    gamePane.addItem("Whey Protein");
                    this.isSoldOut = true;
                }
            } else {
                if (gamePane.getPlayerStamina() >= staminaCost) {
                    gamePane.setPlayerMoney(gamePane.getPlayerMoney() - price);
                    gamePane.setPlayerStamina(gamePane.getPlayerStamina() - staminaCost);
                    gamePane.setPlayerHealth(gamePane.getPlayerHealth() + healthGain);
                }
            }
        }
    }

    public static void show(GamePane gamePane) {
        GymPopup popup = new GymPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // 1. สร้าง Labels
        Label staminaLabel = new Label();
        Label healthLabel = new Label();
        Label moneyLabel = new Label();

        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px;");

        // 2. กล่องใส่ปุ่ม
        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(20));

        // 3. ฟังก์ชัน Refresh UI (แบบไม่ปิดหน้าจอ)
        Runnable refreshUI = () -> {
            // อัปเดตข้อความ Label
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            healthLabel.setText("HEALTH: " + gamePane.getPlayerHealth());
            moneyLabel.setText("$" + gamePane.getPlayerMoney());

            // เคลียร์และสร้างปุ่มใหม่ในกล่องเดิม (ไม่กระพริบ)
            optionsBox.getChildren().clear();
            for (GymService service : GymService.values()) {
                Button btn = popup.createShopButton(service, gamePane, null);
                btn.setPrefSize(170, 140);

                // เช็คสถานะ Sold Out
                if (service.isUnique && service.isSoldOut) {
                    btn.setText("SOLD OUT");
                    btn.setDisable(true);
                    btn.setStyle("-fx-background-color: #333333; -fx-text-fill: #777777;");
                } else {
                    // Logic เมื่อคลิก: รัน Logic -> สั่ง Refresh UI ทันที
                    btn.setOnAction(e -> {
                        service.execute(gamePane);
                        // ใช้คำสั่งนี้เพื่อบังคับ Update UI ในรอบถัดไปของ JavaFX
                        Platform.runLater(() -> {
                            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
                            healthLabel.setText("HEALTH: " + gamePane.getPlayerHealth());
                            moneyLabel.setText("$" + gamePane.getPlayerMoney());
                            if (service.isUnique && service.isSoldOut) {
                                btn.setText("SOLD OUT");
                                btn.setDisable(true);
                                btn.setStyle("-fx-background-color: #333333; -fx-text-fill: #777777;");
                            }
                        });
                    });
                }
                optionsBox.getChildren().add(btn);
            }
        };

        // 4. Logic ปุ่ม WORK
        Runnable workAction = () -> {
            if (gamePane.getPlayerStamina() >= 20) {
                gamePane.setPlayerStamina(gamePane.getPlayerStamina() - 20);
                gamePane.setPlayerMoney(gamePane.getPlayerMoney() + 100);
                Platform.runLater(refreshUI); // อัปเดตค่าหลังทำงาน

            }
        };

        // 5. ประกอบ Layout
        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "GYM", Color.web("#00FFAA"),
                "WORK", "#00FFAA", workAction, refreshUI,
                staminaLabel, healthLabel, moneyLabel
        );

        refreshUI.run(); // เรียกครั้งแรกเพื่อเตรียมปุ่ม
        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 850, 520);
        stage.setScene(scene);
        stage.showAndWait();
    }
}