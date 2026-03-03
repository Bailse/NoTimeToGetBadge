package Screen.BuildingScreen.Chula;

import Screen.BuildingScreen.ShopItem;
import Logic.GamePane;
import Logic.GameSession;
import Character.*;
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

public class ChulaPopup implements Shopable, Normal {

    private enum StudyLevel implements ShopItem {
        HIGH_SCHOOL("High School", 0, "#ff66ff", 2, 0),
        BACHELOR("Bachelor's\nDegree", 500, "#ff66ff", 5, 1),
        MASTER("Master's\nDegree", 1500, "#ff66ff", 7, 2),
        DOCTORATE("Doctorate\nDegree", 5000, "#ff66ff", 15, 3);

        private final String name;
        private final int price;
        private final String color;
        private final int eduGain;
        private final int levelIndex;

        StudyLevel(String name, int price, String color, int eduGain, int levelIndex) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.eduGain = eduGain;
            this.levelIndex = levelIndex;
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
        }
    }

    public static void show(GamePane gamePane) {
        BasePlayer p = gamePane.getPlayer();

        ChulaPopup popup = new ChulaPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        Label staminaLabel = new Label("STAMINA: " + p.getStamina());
        Label eduLabel = new Label("EDUCATION: " + p.getEducation());
        Label moneyLabel = new Label("MONEY: " + p.getMoney());

        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px;");
        eduLabel.setStyle("-fx-text-fill: #ff66ff; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px;");

        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + p.getStamina());
            eduLabel.setText("EDUCATION: " + p.getEducation());
            moneyLabel.setText("MONEY: " + p.getMoney());
        };

        Runnable studyAction = () -> {
            int playerMaxLevel = GameSession.getPlayer().getMaxUnlockedLevel();
            int currentEduGain = 0;
            for (StudyLevel level : StudyLevel.values()) {
                if (level.levelIndex == playerMaxLevel) {
                    currentEduGain = level.eduGain;
                    break;
                }
            }
            if (p instanceof Nerd) {
                if (p.getStamina() >= 10 && p.getEducation() < 200) {
                    // โค้ดใน Popup เมื่อจบการเรียน 1 ครั้ง
                    String status = ((Nerd) p).earnStudyBonus();

                    if (status.equals("BONUS_ACTIVATED")) {
                        // โชว์ Toast ขนาดใหญ่ สีเขียวสว่าง สำหรับ Nerd Power
                        showToast("🤓 NERD POWER! Bonus Edu +20 ", "#00FF7F", 500, 70,false);
                    }
                }
            }
            String result = p.study(currentEduGain, 20);

            switch (result) {
                case "EDU_MAX":
                    showToast("🎓 EDUCATION MAXED OUT! (200/200)", "#00FFFF", 400, 50, true);
                    break;
            }

            gamePane.notifyUpdate();
            refreshUI.run();
        };

        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "CHULA UNIVERSITY", Color.PINK,
                "SELF STUDY", "#ff66ff", studyAction, refreshUI,
                staminaLabel, eduLabel, moneyLabel
        );

        HBox optionsBox = new HBox(15);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(20));

        Runnable renderButtons = new Runnable() {
            @Override
            public void run() {
                optionsBox.getChildren().clear();
                int playerMaxLevel = GameSession.getPlayer().getMaxUnlockedLevel();

                for (StudyLevel level : StudyLevel.values()) {
                    Button btn;

                    if (level.levelIndex <= playerMaxLevel) {
                        btn = popup.createShopButton(level, gamePane, refreshUI);

                        if (level.levelIndex == playerMaxLevel) {
                            btn.setText(level.getName() + "\n★ CURRENT ★");

                            // 1. กำหนด Style ปกติให้เป็นขอบสีเหลืองทอง
                            String goldBorderStyle =
                                    "-fx-background-color: #0f3460;" +
                                            "-fx-border-color: #FFD700;" + // สีทอง
                                            "-fx-border-width: 4;" +
                                            "-fx-background-radius: 0;" +
                                            "-fx-border-radius: 10;" +
                                            "-fx-text-fill: white;";

                            // เซตค่าเริ่มต้นเป็นสีทอง
                            btn.setStyle(goldBorderStyle);

                            // 2. ปล่อยให้ OnMouseEntered ทำงานตามปกติ (จาก createShopButton)
                            // แต่เราจะเขียนทับ OnMouseExited เพื่อให้มัน "กลับมาเป็นสีทอง"
                            btn.setOnMouseExited(e -> {
                                btn.setStyle(goldBorderStyle);
                            });

                        } else {
                            // ระดับที่ผ่านมาแล้ว (PASSED)
                            btn.setText(level.getName() + "\n(PASSED)");
                            btn.setOpacity(1);
                        }
                    } else if (level.levelIndex == playerMaxLevel + 1) {
                        btn = new Button("UNLOCK\n" + level.getName() + "\n$" + level.getPrice());
                        popup.applyPixelStyle(btn, "#888888");
                        btn.setOnAction(e -> {
                            if (p.getMoney() >= level.getPrice()) {
                                p.setMoney((int) (p.getMoney() - level.getPrice()));
                                GameSession.getPlayer().setMaxUnlockedLevel(level.levelIndex);
                                gamePane.updateEducationItem(level.levelIndex);
                                gamePane.notifyUpdate();
                                // อัปเดตหน้าจอโดยไม่ต้องเปิดใหม่ (แก้กระพริบ)
                                javafx.application.Platform.runLater(() -> {
                                    this.run(); // วาดปุ่มใหม่
                                    refreshUI.run(); // อัปเดต Text Status
                                });
                            }
                        });
                    } else {
                        btn = new Button("LOCKED\n(Finish Previous)");
                        popup.applyPixelStyle(btn, "#333333");
                        btn.setOpacity(0.4);
                        btn.setDisable(true);
                    }

                    btn.setPrefSize(180, 140);
                    optionsBox.getChildren().add(btn);
                }
            }
        };

        renderButtons.run();
        root.setCenter(optionsBox);
        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}