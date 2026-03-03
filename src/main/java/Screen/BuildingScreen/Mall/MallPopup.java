package Screen.BuildingScreen.Mall;

import Character.BasePlayer;
import Logic.GameSession;
import Screen.BuildingScreen.ShopItem;
import Logic.GamePane;
import Character.Otaku;
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

import static Screen.UI.ToastUtil.showToast;

public class MallPopup implements Shopable, Normal {
    public static void resetAllMallItems() {
        MallItem.resetMallItems();
    }
    private enum MallItem implements ShopItem {
        PILLOW("PILLOW", 50, "#00ffff", 10, 0, false),
        TUNG_BED("BED SET", 676, "#ffd700", 100, 20, false),
        CAR("CAR", 999, "#ff007f", 40, 5, true);

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

        public static void resetMallItems() {
            for (MallItem item : MallItem.values()) {
                item.soldOut = false;
            }
        }

        @Override
        public void execute(GamePane gamePane) {
            BasePlayer p = gamePane.getPlayer();
            if (p.getMoney() >= price) {
                if (this.isUnique) {
                    if (!soldOut) {
                        p.setMoney(p.getMoney() - price);
                        gamePane.addItem(this.name);
                        this.soldOut = true;
                    }
                } else {
                    p.setStamina(p.getStamina() + staminaGain);
                    p.setHealth(p.getHealth() + healthGain);
                    p.setMoney(p.getMoney() - price);
                }
                gamePane.notifyUpdate(); // สำคัญ: เพื่อให้อัปเดตหลอดพลังงานหน้าจอหลัก
            }
        }
    }

    public static void show(GamePane gamePane) {
        BasePlayer p = gamePane.getPlayer();
        MallPopup popup = new MallPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        Label staminaLabel = new Label("STAMINA: " + p.getStamina());
        Label healthLabel = new Label("HEALTH: " + p.getHealth());
        Label moneyLabel = new Label("MONEY: " + p.getMoney());

        staminaLabel.setStyle("-fx-text-fill: #00ff99; -fx-font-size: 18px;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 18px;");

        HBox optionBox = new HBox(25);
        optionBox.setAlignment(Pos.CENTER);
        optionBox.setPadding(new Insets(40));

        // แก้ปัญหา Variable might not have been initialized ด้วยการใช้ Final Array
        final Runnable[] refreshUI = new Runnable[1];

        refreshUI[0] = () -> {
            staminaLabel.setText("STAMINA: " + p.getStamina());
            healthLabel.setText("HEALTH: " + p.getHealth());
            moneyLabel.setText("MONEY: " + p.getMoney());

            optionBox.getChildren().clear();
            for (MallItem item : MallItem.values()) {
                // สร้างปุ่มเบื้องต้น (ส่ง null ไปก่อนเพราะเราจะกำหนด setOnAction เองด้านล่าง)
                Button btn = popup.createShopButton(item, gamePane, null);
                btn.setPrefSize(180, 140);

                if (item.isUnique && item.soldOut) {
                    btn.setText("SOLD OUT");
                    btn.setDisable(true);
                    btn.setStyle("-fx-background-color: #333333; -fx-text-fill: gray; -fx-border-radius: 10;");
                } else {
                    btn.setOnAction(e -> {
                        item.execute(gamePane);
                        // เรียกใช้ผ่าน Array เพื่อให้ Lambda รู้จักตัวแปร
                        Platform.runLater(refreshUI[0]);
                    });
                }
                optionBox.getChildren().add(btn);
            }
        };

        Runnable workAction = () -> {
            // 1. กำหนดค่าพื้นฐาน (Otaku ทำงานที่ Mall จะเหนื่อยน้อยกว่าคนอื่น)
            int staminaCost = (p instanceof Otaku) ? 5 : 10;
            int moneyGain = 200;

            // 2. สั่งให้ทำงาน และตรวจสอบว่า Stamina พอหรือไม่จากผลลัพธ์ boolean
            boolean isSuccess = p.work(staminaCost, moneyGain);

            if (isSuccess) {
                // 3. ถ้าทำงานสำเร็จ และเป็น Otaku ให้คำนวณแต้มโบนัส
                if (p instanceof Otaku) {
                    String bonusStatus = ((Otaku) p).earnWorkBonus();

                    // 4. แสดง Toast ตามสถานะโบนัสที่ได้รับ (ใช้สี Pink)
                    if (bonusStatus.equals("OTAKU_BONUS_ACTIVATED")) {
                        showToast("🌸 OTAKU POWER! Bonus: +$700", "pink", 450, 80,false);
                    }
                }
            }

            gamePane.notifyUpdate();
            refreshUI[0].run();
        };

        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "MALL", Color.CYAN, "PART-TIME", "#00ffff",
                workAction, refreshUI[0], staminaLabel, healthLabel, moneyLabel
        );

        refreshUI[0].run();
        root.setCenter(optionBox);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}