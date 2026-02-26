package Screen.BuildingScreen;

import Logic.GamePane;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public interface Normal { // สืบทอด Shopable มาด้วยเลยเพื่อให้ใช้ applyPixelStyle ได้

    default BorderPane createBaseLayout(Stage stage, GamePane gamePane, String titleText, Color titleColor,
                                        String actionBtnText, String actionBtnColor, Runnable actionEffect,
                                        Runnable refreshUI, Label... statsLabels) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #16213e;");

        // ===== HEADER =====
        Label title = new Label(titleText);
        title.setTextFill(titleColor);
        title.setStyle("-fx-font-size: 50px; -fx-font-family: 'Courier New'; -fx-font-weight: bold;");

        StackPane header = new StackPane(title);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #1a1a2e; -fx-border-color: " + toHex(titleColor) + "; -fx-border-width: 0 0 4 0;");

        // ===== FOOTER =====
        // ล่างซ้าย: EXIT
        Button exitBtn = new Button("EXIT");
        applyPixelStyle(exitBtn, "#ff4444");
        exitBtn.setPrefSize(120, 50);
        exitBtn.setOnAction(e -> stage.close());

        // ตรงกลาง: STATS
        HBox statsBox = new HBox(30);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.getChildren().addAll(statsLabels);

        // ล่างขวา: ACTION (เช่น WORK เพื่อหาเงิน)
        Button actionBtn = new Button(actionBtnText);
        applyPixelStyle(actionBtn, actionBtnColor);
        actionBtn.setPrefSize(120, 50);
        actionBtn.setOnAction(e -> {
            if (actionEffect != null) {
                actionEffect.run(); // รัน Logic การทำงาน (เช่น เพิ่มเงิน/ลดพลัง)
                refreshUI.run();    // อัปเดตตัวเลขบนหน้าจอทันที
            }
        });

        BorderPane footer = new BorderPane();
        footer.setPadding(new Insets(20));
        footer.setLeft(exitBtn);
        footer.setCenter(statsBox);
        footer.setRight(actionBtn);
        footer.setStyle("-fx-background-color: #0f0f0f; -fx-border-color: #333; -fx-border-width: 3 0 0 0;");

        root.setTop(header);
        root.setBottom(footer);

        return root;
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X", (int)(color.getRed()*255), (int)(color.getGreen()*255), (int)(color.getBlue()*255));
    }

    default void applyPixelStyle(Button btn, String borderColor) {
        // --- 1. ตั้งค่าพื้นฐาน ---
        btn.setPrefSize(160, 130);
        btn.setFont(Font.font("Courier New", 14));
        btn.setTextFill(Color.WHITE);

        String normalStyle = "-fx-background-color: #0f3460; -fx-border-color: " + borderColor +
                "; -fx-border-width: 4; -fx-background-radius: 10; -fx-border-radius: 10;";
        String hoverStyle = "-fx-background-color: " + borderColor +
                "; -fx-border-color: white; -fx-border-width: 4; -fx-background-radius: 10; -fx-border-radius: 10;";

        btn.setStyle(normalStyle);

        // --- 2. Hover Effect ---
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));

        // --- 3. Animation Logic (ยกมาจาก GymPopup เลย) ---
        ScaleTransition bounce = new ScaleTransition(Duration.millis(120), btn);
        bounce.setToX(1);
        bounce.setToY(1);

        btn.setOnMousePressed(e -> {
            bounce.stop();
            btn.setScaleX(0.9);
            btn.setScaleY(0.9);
        });

        btn.setOnMouseReleased(e -> bounce.playFromStart());
    }
}