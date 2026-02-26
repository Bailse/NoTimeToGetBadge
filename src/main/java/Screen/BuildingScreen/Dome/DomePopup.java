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
        SLEEP("SLEEP 💤", 0, "#ffaa00", -40, 0),  // Stamina ติดลบคือการเพิ่ม (ตาม logic setPlayerStamina)
        READ("READ 📚", 0, "#00FFAA", 10, 5),    // เสีย Stamina 10 ได้ Edu 5
        RELAX("RELAX 🎮", 0, "#ff66ff", -15, -2); // เพิ่ม Stamina แต่ลด Edu เล็กน้อย

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int eduGain;

        DomeAction(String name, int price, String color, int staminaCost, int eduGain) {
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
            // Logic: Stamina ติดลบหมายถึงการพักผ่อน (เพิ่มค่า), บวกหมายถึงใช้ค่าพลัง
            if (staminaCost > 0 && gamePane.getPlayerStamina() < staminaCost) {
                System.out.println("Not enough stamina to " + name);
                return;
            }
            gamePane.setPlayerStamina(gamePane.getPlayerStamina() - staminaCost);
            gamePane.setPlayerEducation(gamePane.getPlayerEducation() + eduGain);
            System.out.println("Action: " + name);
        }
    }

    public static void show(GamePane gamePane) {
        DomePopup popup = new DomePopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // ===== Labels สำหรับแสดงสถานะ =====
        Label staminaLabel = new Label("STAMINA: " + gamePane.getPlayerStamina());
        Label eduLabel = new Label("EDUCATION: " + gamePane.getPlayerEducation());
        Label moneyLabel = new Label("MONEY: " + gamePane.getPlayerMoney());

        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 14px;");
        eduLabel.setStyle("-fx-text-fill: #ff66ff; -fx-font-size: 14px;");
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 14px;");

        // ฟังก์ชัน Refresh UI เมื่อกดปุ่ม
        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            eduLabel.setText("EDUCATION: " + gamePane.getPlayerEducation());
            moneyLabel.setText("MONEY: " + gamePane.getPlayerMoney());
        };

        // ใช้ createBaseLayout จาก Interface Normal
        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "DORMITORY", Color.web("#ffaa00"),
                "QUICK NAP", "#ffaa00",
                () -> { // กิจกรรมปุ่มขวาล่าง (Quick Nap)
                    gamePane.setPlayerStamina(gamePane.getPlayerStamina() + 5);
                    refreshUI.run();
                },
                refreshUI,
                staminaLabel, eduLabel, moneyLabel
        );

        // ส่วนกลาง: สร้างปุ่มกิจกรรมจาก Enum DomeAction
        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (DomeAction action : DomeAction.values()) {
            // ใช้ createShopButton จาก Interface Shopable
            Button btn = popup.createShopButton(action, gamePane, refreshUI);
            btn.setPrefSize(200, 150);

            // ปรับแต่ง Style เพิ่มเติมให้ปุ่มโค้งตามที่คุณต้องการ
            String currentStyle = btn.getStyle();
            btn.setStyle(currentStyle + "-fx-background-radius: 15; -fx-border-radius: 15;");

            // เพิ่มการ Override Hover Exited เพื่อรักษาความโค้ง
            btn.setOnMouseExited(e -> {
                btn.setStyle(currentStyle + "-fx-background-radius: 15; -fx-border-radius: 15;");
                btn.setEffect(null);
            });

            optionsBox.getChildren().add(btn);
        }

        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}