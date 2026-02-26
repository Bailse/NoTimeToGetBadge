package Screen.BuildingScreen.Park;


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

public class ParkPopup implements Shopable, Normal {

    // Enum สำหรับกิจกรรมในสวนสาธารณะ
    private enum ParkAction implements ShopItem {
        WALK("WALK 🚶", 0, "#00cc66", 10, 2),  // เสีย Stamina 10 ได้ Edu 2 (เรียนรู้ธรรมชาติ)
        RELAX("RELAX 🍃", 0, "#00cc66", -20, 0), // พักผ่อนเพิ่ม Stamina 20
        SIT("SIT 🪑", 0, "#00cc66", -5, 1);    // พักเล็กน้อย เพิ่ม Stamina 5 ได้ Edu 1

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int eduGain;

        ParkAction(String name, int price, String color, int staminaCost, int eduGain) {
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
            // ถ้าค่า staminaCost เป็นบวก คือต้องใช้ค่าพลัง (เช็คว่าพอไหม)
            if (staminaCost > 0 && gamePane.getPlayerStamina() < staminaCost) {
                System.out.println("Too tired to walk!");
                return;
            }
            gamePane.setPlayerStamina(gamePane.getPlayerStamina() - staminaCost);
            gamePane.setPlayerEducation(gamePane.getPlayerEducation() + eduGain);
        }
    }

    public static void show(GamePane gamePane) {
        ParkPopup popup = new ParkPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // Labels สำหรับแสดงสถานะ (Stamina, Edu, Money)
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

        // ใช้ Base Layout จาก Normal (Header/Footer พื้นฐาน)
        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "GREEN PARK", Color.web("#00cc66"),
                "TAKE A BREATH", "#00cc66",
                () -> { // กิจกรรมปุ่มขวาล่าง
                    gamePane.setPlayerStamina(gamePane.getPlayerStamina() + 2);
                    refreshUI.run();
                },
                refreshUI,
                staminaLabel, eduLabel, moneyLabel
        );

        // ส่วนปุ่มตรงกลาง
        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (ParkAction action : ParkAction.values()) {
            Button btn = popup.createShopButton(action, gamePane, refreshUI);
            btn.setPrefSize(200, 150);

            // ปรับแต่งปุ่มให้โค้งมน (Radius 15) และสีสันสดใส
            String normalStyle = "-fx-background-color: #0f3460; " +
                    "-fx-border-color: #00cc66; " +
                    "-fx-border-width: 4; " +
                    "-fx-background-radius: 15; " +
                    "-fx-border-radius: 15; " +
                    "-fx-text-fill: white;";

            btn.setStyle(normalStyle);

            // แก้ไขให้ Hover แล้วไม่เสียรูปทรงความโค้ง
            btn.setOnMouseExited(e -> {
                btn.setStyle(normalStyle);
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