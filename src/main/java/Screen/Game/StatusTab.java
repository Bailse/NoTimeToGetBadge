package Screen.Game;

import Character.Player;
import Logic.GameSession;
import Item.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.Objects;

public class StatusTab extends VBox {

    private ProgressBar staminaBar, healthBar, moneyBar, educationBar;
    private Label staminaLabel, healthLabel, moneyLabel, educationLabel;
    private ImageView[] itemSlots;

    public StatusTab() {
        // ตั้งค่า Spacing ให้กว้างขึ้นเพื่อกระจายองค์ประกอบ
        setSpacing(20);
        setPadding(new Insets(25));
        setAlignment(Pos.TOP_CENTER);
        setPrefWidth(450); // ความกว้าง Tab

        setStyle("""
            -fx-background-color: #1a1a1a; 
            -fx-border-color: #3d3d3d;
            -fx-border-width: 4;
            -fx-border-style: solid;
        """);

        Label title = new Label("GAME STATUS");
        title.setFont(Font.font("Courier New", FontWeight.BLACK, 32));
        title.setTextFill(Color.WHITE);

        // ===== Avatar Section (ขนาดกำลังดี ไม่เล็กเกินไป) =====
        StackPane avatarPane = new StackPane();
        ImageView avatar = new ImageView();

        try {
            Player p = GameSession.getPlayer();
            String path = (p != null && p.getImagePath() != null) ? p.getImagePath() : "/player.png";
            avatar.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        } catch (Exception e) {
            System.out.println("Avatar not found");
        }

        avatar.setFitWidth(160);
        avatar.setFitHeight(160);
        avatar.setClip(new Rectangle(160, 160));

        Rectangle borderOut = new Rectangle(170, 170);
        borderOut.setFill(Color.TRANSPARENT);
        borderOut.setStroke(Color.WHITE);
        borderOut.setStrokeWidth(4);

        avatarPane.getChildren().addAll(borderOut, avatar);

        // ===== Bars Section (ขยายขนาดแถบให้ยาวขึ้น) =====
        VBox staminaBox = createBar("STAMINA", "#ffff00");
        VBox healthBox = createBar("HEALTH", "#ff0000");
        VBox educationBox = createBar("EDUCATION", "#0000ff");
        VBox moneyBox = createBar("MONEY", "#00ff00");

        // ===== Spacer (สำคัญ: ช่วยดัน Equipment ลงไปชนขอบล่างพอดี) =====
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // ===== Equipment Section (ขยายขนาด Slot ให้ใหญ่ขึ้น) =====
        Label invTitle = new Label("> EQUIPMENT");
        invTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 15));
        invTitle.setTextFill(Color.WHITE);

        HBox inventoryBox = new HBox(20); // ระยะห่างช่องละ 15
        inventoryBox.setAlignment(Pos.CENTER);
        itemSlots = new ImageView[4];

        for (int i = 0; i < 4; i++) {
            StackPane slot = new StackPane();
            Rectangle bg = new Rectangle(70, 70); // ขยายช่องเป็น 75x75
            bg.setFill(Color.web("#2d2d2d"));
            bg.setStroke(Color.web("#555555"));
            bg.setStrokeWidth(3);

            itemSlots[i] = new ImageView();
            itemSlots[i].setFitWidth(60); // ขนาดไอเทมข้างในใหญ่ขึ้น
            itemSlots[i].setFitHeight(60);
            itemSlots[i].setPreserveRatio(true);

            slot.getChildren().addAll(bg, itemSlots[i]);
            inventoryBox.getChildren().add(slot);
        }

        // เพิ่มทุกอย่างลงใน StatusTab
        getChildren().addAll(title, avatarPane, staminaBox, healthBox, educationBox, moneyBox, spacer, invTitle, inventoryBox);
        updateStatus();
    }

    private VBox createBar(String name, String color) {
        Label nameLabel = new Label("> " + name);
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 16));

        ProgressBar bar = new ProgressBar();
        bar.setPrefWidth(320); // ขยายความกว้างของแถบ
        bar.setPrefHeight(22);
        bar.setStyle("-fx-accent: " + color + "; -fx-control-inner-background: #333333; -fx-background-radius: 0; -fx-border-color: #000000;");

        Label valLabel = new Label();
        valLabel.setTextFill(Color.WHITE);
        valLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 13));

        StackPane stack = new StackPane(bar, valLabel);
        VBox box = new VBox(5, nameLabel, stack);

        switch (name) {
            case "STAMINA" -> { staminaBar = bar; staminaLabel = valLabel; }
            case "HEALTH" -> { healthBar = bar; healthLabel = valLabel; }
            case "EDUCATION" -> { educationBar = bar; educationLabel = valLabel; }
            case "MONEY" -> { moneyBar = bar; moneyLabel = valLabel; }
        }
        return box;
    }

    public void updateStatus() {
        Player player = GameSession.getPlayer();
        if (player == null) return;

        animateBar(staminaBar, player.getStamina() / 200.0);
        animateBar(healthBar, player.getHealth() / 200.0);
        animateBar(educationBar, player.getEducation() / 200.0);
        animateBar(moneyBar, player.getMoney() / 2000.0);

        staminaLabel.setText(player.getStamina() + "/200");
        healthLabel.setText(player.getHealth() + "/200");
        educationLabel.setText(player.getEducation() + "/200");
        moneyLabel.setText("$" + player.getMoney());

        //updateInventoryDisplay(player.getItemManager());
    }

    private void updateInventoryDisplay(Item itemManager) {
        if (itemManager == null) return;
        for (int i = 0; i < 4; i++) {
            BaseItem item = itemManager.getInventory().get(i);
            ImageView view = itemSlots[i];

//            if (item != null) {
                try {
                    // Path รูปภาพใน resources
                    String path = "/resources/" + item.getImage();
                    view.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));

                    // เช็คสถานะ Active ตามโจทย์: สว่าง 100% หรือ จาง 20%
                    if (item.isActive()) {
                        view.setOpacity(1.0);
                    } else {
                        view.setOpacity(1.0); // แก้จาก 1.0 เป็น 0.2 แล้วครับ
                    }
                } catch (Exception e) {
                    System.out.println("Image missing: " + item.getImage());
                }
//            } else {
//                view.setImage(null);
//            }
        }
    }

    private void animateBar(ProgressBar bar, double newValue) {
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(300), new KeyValue(bar.progressProperty(), newValue)));
        tl.play();
    }
}