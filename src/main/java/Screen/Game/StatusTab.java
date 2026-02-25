package Screen.Game;

import Character.Player;
import Logic.GameSession;
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

    public StatusTab() {
        Player player = GameSession.getPlayer();

        setSpacing(25);
        setPadding(new Insets(30));
        setAlignment(Pos.TOP_CENTER);
        setPrefWidth(450);

        // ===== ตกแต่งพื้นหลังสไตล์ PIXEL BOX =====
        // ใช้ขอบเหลี่ยม 0px และเส้นขอบหนาแบบ Retro
        setStyle("""
            -fx-background-color: #1a1a1a; 
            -fx-border-color: #3d3d3d;
            -fx-border-width: 4;
            -fx-border-style: solid;
            -fx-background-insets: 0;
        """);

        Label title = new Label("GAME STATUS");
        // แนะนำให้ใช้ Font "Courier New" หรือ Font แนว Monospace เพื่อให้ดูเป็น Pixel
        title.setFont(Font.font("Courier New", FontWeight.BLACK, 32));
        title.setTextFill(Color.WHITE); // สี Cyan นีออน

        // ===== Avatar Section (เปลี่ยนจากวงกลมเป็นกรอบสี่เหลี่ยมพิกเซล) =====
        StackPane avatarPane = new StackPane();
        ImageView avatar = new ImageView();

        try {
            String path = (player != null && player.getImagePath() != null) ? player.getImagePath() : "/player.png";
            avatar.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        } catch (Exception e) {
            System.out.println("Pixel Avatar not found");
        }

        avatar.setFitWidth(180);
        avatar.setFitHeight(180);

        // ใช้สี่เหลี่ยมตัดรูปแทนวงกลมเพื่อให้เข้ากับธีม Pixel
        Rectangle clip = new Rectangle(180, 180);
        avatar.setClip(clip);

        // กรอบสี่เหลี่ยมซ้อนกัน 2 ชั้นแบบสไตล์เกมเก่า
        Rectangle borderOut = new Rectangle(190, 190);
        borderOut.setFill(Color.TRANSPARENT);
        borderOut.setStroke(Color.web("#ffffff"));
        borderOut.setStrokeWidth(4);

        avatarPane.getChildren().addAll(borderOut, avatar);

        // ===== Bars Section (สไตล์พิกเซลเหลี่ยมจัด) =====
        VBox staminaBox = createBar("STAMINA", "#ffff00"); // เหลืองพิกเซล
        VBox healthBox = createBar("HEALTH", "#ff0000");  // แดงจัด
        VBox educationBox = createBar("EDUCATION", "#0000ff"); // น้ำเงินเข้ม
        VBox moneyBox = createBar("MONEY", "#00ff00");    // เขียวนีออน

        updateStatus();

        getChildren().addAll(title, avatarPane, staminaBox, healthBox, educationBox, moneyBox);
    }

    private VBox createBar(String name, String color) {
        Label nameLabel = new Label("> " + name);
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 16));

        ProgressBar bar = new ProgressBar();
        bar.setPrefWidth(280);
        bar.setPrefHeight(25);

        // สไตล์ Pixel Bar: ลบความโค้ง (radius 0), เพิ่มเส้นขอบดำหนา
        bar.setStyle("-fx-accent: " + color + "; " +
                "-fx-control-inner-background: #333333; " +
                "-fx-background-radius: 0; " +
                "-fx-border-color: #000000; " +
                "-fx-border-width: 2; " +
                "-fx-text-box-border: transparent;");

        Label valueLabel = new Label();
        valueLabel.setTextFill(Color.WHITE);
        valueLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        valueLabel.setStyle("-fx-effect: dropshadow(one-pass-box, black, 2, 1, 1, 1);");

        StackPane stack = new StackPane(bar, valueLabel);
        stack.setAlignment(Pos.CENTER);

        VBox box = new VBox(5, nameLabel, stack);
        box.setAlignment(Pos.CENTER_LEFT);

        switch (name) {
            case "STAMINA" -> { staminaBar = bar; staminaLabel = valueLabel; }
            case "HEALTH" -> { healthBar = bar; healthLabel = valueLabel; }
            case "EDUCATION" -> { educationBar = bar; educationLabel = valueLabel; }
            case "MONEY" -> { moneyBar = bar; moneyLabel = valueLabel; }
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

        staminaLabel.setText(player.getStamina() + "/" + 200);
        healthLabel.setText(player.getHealth() + "/" + 200);
        educationLabel.setText(player.getEducation() + "/" + 200);
        moneyLabel.setText("$" + player.getMoney());
    }

    private void animateBar(ProgressBar bar, double newValue) {
        // อนิเมชั่นแบบสั้นๆ กระตุกนิดๆ ให้ฟีลลิ่งแบบเกมย้อนยุค
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200), new KeyValue(bar.progressProperty(), newValue))
        );
        timeline.play();
    }
}