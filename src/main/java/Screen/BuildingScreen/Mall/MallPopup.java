package Screen.BuildingScreen.Mall;

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

public class MallPopup implements Shopable, Normal {

    private enum MallItem implements ShopItem {
        PILLOW("PILLOW", 50, "#00ffff", 10, 0, false),
        TUNG_BED("BED SET", 676, "#ffd700", 100, 20, false),
        CAR("CAR", 999, "#ff007f", 40, 5, true); // เปลี่ยนเป็น true เพื่อให้ซื้อได้ครั้งเดียว

        private final String name;
        private final int price;
        private final String color;
        private final int staminaGain;
        private final int healthGain;
        private final boolean isUnique;
        private boolean soldOut = false;

        MallItem(String name, int price, String color, int staminaGain, int healthGain, boolean isUnique) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaGain = staminaGain;
            this.healthGain = healthGain;
            this.isUnique = isUnique;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            if (gamePane.getPlayerMoney() >= price) {
                if (this == CAR) {
                    if (!soldOut) {
                        gamePane.setPlayerMoney(gamePane.getPlayerMoney() - price);
                        gamePane.addItem("CAR"); // เพิ่มไอเทมรถเข้าตัว
                        this.soldOut = true;
                    }
                } else {
                    gamePane.setPlayerStamina(gamePane.getPlayerStamina() + staminaGain);
                    gamePane.setPlayerHealth(gamePane.getPlayerHealth() + healthGain);
                    gamePane.setPlayerMoney(gamePane.getPlayerMoney() - price);
                }
            }
        }
    }

    public static void show(GamePane gamePane) {
        MallPopup popup = new MallPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        Label staminaLabel = new Label("STAMINA: " + gamePane.getPlayerStamina());
        Label healthLabel = new Label("HEALTH: " + gamePane.getPlayerHealth());
        Label moneyLabel = new Label("MONEY: " + gamePane.getPlayerMoney());

        staminaLabel.setStyle("-fx-text-fill: #00ff99; -fx-font-size: 18px;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 18px;");

        HBox optionBox = new HBox(25);
        optionBox.setAlignment(Pos.CENTER);
        optionBox.setPadding(new Insets(40));

        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            healthLabel.setText("HEALTH: " + gamePane.getPlayerHealth());
            moneyLabel.setText("MONEY: " + gamePane.getPlayerMoney());

            optionBox.getChildren().clear();
            for (MallItem item : MallItem.values()) {
                Button btn = popup.createShopButton(item, gamePane, null);
                btn.setPrefSize(180, 140);

                if (item.isUnique && item.soldOut) {
                    btn.setText("SOLD OUT");
                    btn.setDisable(true);
                    btn.setStyle("-fx-background-color: #333333; -fx-text-fill: gray;");
                } else {
                    btn.setOnAction(e -> {
                        item.execute(gamePane);
                        Platform.runLater(() -> {
                            // อัปเดต UI ทันทีโดยไม่ปิดหน้าต่าง (แก้ปัญหากระพริบ)
                            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
                            healthLabel.setText("HEALTH: " + gamePane.getPlayerHealth());
                            moneyLabel.setText("MONEY: " + gamePane.getPlayerMoney());
                            if (item.isUnique && item.soldOut) {
                                btn.setText("SOLD OUT");
                                btn.setDisable(true);
                                btn.setStyle("-fx-background-color: #333333; -fx-text-fill: gray;");
                            }
                        });
                    });
                }
                optionBox.getChildren().add(btn);
            }
        };

        Runnable workAction = () -> {
            if (gamePane.getPlayerStamina() >= 10) {
                gamePane.setPlayerStamina(gamePane.getPlayerStamina() - 10);
                gamePane.setPlayerMoney(gamePane.getPlayerMoney() + 20000);
                Platform.runLater(refreshUI);
            }
        };

        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "MALL", Color.CYAN, "PART-TIME", "#00ffff",
                workAction, refreshUI, staminaLabel, healthLabel, moneyLabel
        );

        refreshUI.run();
        root.setCenter(optionBox);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}