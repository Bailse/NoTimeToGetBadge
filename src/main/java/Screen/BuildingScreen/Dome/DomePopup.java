package Screen.BuildingScreen.Dome;

import Logic.GamePane;
import Screen.BuildingScreen.Normal;
import Screen.BuildingScreen.ShopItem;
import Screen.BuildingScreen.Shopable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DomePopup implements Shopable, Normal {

    // ===== Enum จัดการ Logic ของกิจกรรม (อ้างอิงค่าจากตัวมันเอง) =====
    private enum DomeAction implements ShopItem {
        SLEEP("SLEEP 💤", 0, "#ffaa00", 40, 0, "Recover +40 Stamina"),
        READ("READ 📚", 0, "#00FFAA", 10, 5, "Use 10 Stamina\nGet +5 Education"),
        RELAX("RELAX 🎮", 0, "#ff66ff", 15, -2, "Recover +15 Stamina\nLose -2 Education");

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int eduGain;
        private final String description;

        DomeAction(String name, int price, String color, int staminaCost, int eduGain, String description) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.eduGain = eduGain;
            this.description = description;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            // ดึงค่าปัจจุบันจาก gamePane
            int currentStamina = gamePane.getPlayerStamina();
            int currentEdu = gamePane.getPlayerEducation();

            // เช็คเงื่อนไข Stamina (ถ้าค่าใช้จ่ายเป็นบวก คือต้องใช้พลังงาน)
            if (staminaCost > 0 && currentStamina < staminaCost) {
                showWarning("Stamina ไม่เพียงพอ! กรุณาพักผ่อน");
                return;
            }

            // set ค่าใหม่กลับไปที่ gamePane (ปัจจุบัน - cost)
            // ถ้าพักผ่อน cost คือ -40 จะกลายเป็น -(-40) = +40
            gamePane.setPlayerStamina(currentStamina - staminaCost);
            gamePane.setPlayerEducation(currentEdu + eduGain);

            System.out.println("Action Executed: " + name + " | Stamina updated via GamePane");
        }

        private void showWarning(String message) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    public static void show(GamePane gamePane) {
        DomePopup popup = new DomePopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("DORMITORY");
        stage.setResizable(false);

        // ===== Labels แสดงสถานะ =====
        Label staminaLabel = new Label();
        Label eduLabel = new Label();
        Label moneyLabel = new Label();

        // ฟังก์ชัน Refresh UI บนหน้าจอ Popup
        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + gamePane.getPlayerStamina());
            eduLabel.setText("EDUCATION: " + gamePane.getPlayerEducation());
            moneyLabel.setText("MONEY: " + gamePane.getPlayerMoney());
        };

        refreshUI.run(); // โหลดค่าครั้งแรก

        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 14px; -fx-font-weight: bold;");
        eduLabel.setStyle("-fx-text-fill: #ff66ff; -fx-font-size: 14px; -fx-font-weight: bold;");
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 14px; -fx-font-weight: bold;");

        // ===== สร้าง Layout หลักจาก Normal Interface =====
        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "DORMITORY", Color.web("#ffaa00"),
                "QUICK NAP", "#ffaa00",
                () -> {
                    // ปุ่มพิเศษมุมล่างขวา
                    gamePane.setPlayerStamina(gamePane.getPlayerStamina() + 5);
                    refreshUI.run();
                },
                refreshUI,
                staminaLabel, eduLabel, moneyLabel
        );

        // ===== ส่วนกลาง: สร้างปุ่มกิจกรรม (วนลูปสร้างปุ่มแบบเดียวกับ Chula) =====
        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (DomeAction action : DomeAction.values()) {
            // สร้างปุ่มจาก Shopable
            Button btn = popup.createShopButton(action, gamePane, refreshUI);
            btn.setPrefSize(220, 160);

            // *** สำคัญ: กำหนด Event เมื่อกดปุ่ม เพื่อให้เรียกใช้ Logic และ Refresh UI ***
            btn.setOnAction(e -> {
                action.execute(gamePane); // รัน Logic เพิ่ม/ลดค่า
                refreshUI.run();          // อัปเดตตัวเลขใน Popup
            });

            // ตกแต่งเนื้อหาข้างในปุ่ม (Graphic)
            VBox btnContent = new VBox(10);
            btnContent.setAlignment(Pos.CENTER);

            Label nameLbl = new Label(action.getName());
            nameLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label descLbl = new Label(action.description);
            descLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #eee; -fx-text-alignment: center;");

            btnContent.getChildren().addAll(nameLbl, descLbl);
            btn.setGraphic(btnContent);
            btn.setText("");

            // สไตล์ของปุ่ม
            String baseStyle = "-fx-background-color: " + action.getColor() + ";" +
                    "-fx-background-radius: 10; -fx-border-radius: 10;" +
                    "-fx-border-color: white; -fx-border-width: 2;";
            btn.setStyle(baseStyle);

            // เอฟเฟกต์เมื่อเมาส์ชี้
            btn.setOnMouseEntered(e -> {
                btn.setStyle(baseStyle + "-fx-brightness: 1.2; -fx-scale-x: 1.05; -fx-cursor: hand;");
            });
            btn.setOnMouseExited(e -> {
                btn.setStyle(baseStyle);
            });

            optionsBox.getChildren().add(btn);
        }

        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}