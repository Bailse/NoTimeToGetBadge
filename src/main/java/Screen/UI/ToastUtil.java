package Screen.UI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ToastUtil {
    private static Stage currentToastStage = null;
    private static javafx.animation.FadeTransition currentFade = null;

    // เพิ่ม parameter String hexColor เข้าไป
    public static void showToast(String message, String hexColor, double width, double height, boolean shouldBlink) {
        javafx.application.Platform.runLater(() -> {
            if (currentFade != null) currentFade.stop();

            if (currentToastStage == null) {
                currentToastStage = new Stage();
                currentToastStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
                currentToastStage.setAlwaysOnTop(true);

                Label label = new Label();
                label.setId("toastLabel");
                label.setWrapText(true); // ให้ตัดบรรทัดถ้าข้อความยาวเกินความกว้าง
                label.setAlignment(javafx.geometry.Pos.CENTER);
                label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

                // สไตล์พื้นฐาน
                label.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85); " +
                        "-fx-padding: 10px; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-border-width: 3px;");

                javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane(label);
                root.setStyle("-fx-background-color: transparent;");
                Scene scene = new Scene(root);
                scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
                currentToastStage.setScene(scene);
            }
            StackPane root = (StackPane) currentToastStage.getScene().getRoot();
            Label label = (Label) root.lookup("#toastLabel");

            // 1. อัปเดตข้อความและสี
            label.setText(message);
            label.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85); " +
                    "-fx-padding: 10px; -fx-font-size: 18px; -fx-font-weight: bold; " +
                    "-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-width: 3px; " +
                    "-fx-text-fill: " + hexColor + "; -fx-border-color: " + hexColor + ";");

            // 2. บังคับขนาดใหม่ให้เป๊ะ
            label.setPrefSize(width, height);
            label.setMinSize(width, height);
            label.setMaxSize(width, height);

            // --- จุดที่เพิ่มเข้ามาเพื่อแก้ปัญหาขนาดไม่เปลี่ยน ---
            root.requestLayout();          // สั่งให้คำนวณตำแหน่งใหม่
            currentToastStage.sizeToScene(); // สั่งให้หน้าต่างยืดหดตามเนื้อหาข้างใน

            // 1. กำหนดขอบเขตการ Random (หน่วยเป็น Pixel)
            double randomRange = 200;
            java.util.Random random = new java.util.Random();

// 2. คำนวณ Offset แบบสุ่ม (จะได้ค่าระหว่าง -50 ถึง +50)
            double offsetX = (random.nextDouble() * randomRange) - (randomRange / 2);
            double offsetY = (random.nextDouble() * randomRange) - (randomRange / 2);

// 3. ตั้งค่าตำแหน่งกึ่งกลางจอ + ค่า Random
            double screenWidth = 1980; // ตามที่คุณกำหนดไว้เดิม
            double centerX = (screenWidth - width) / 2;
            double centerY = 230; // จุดเริ่มต้นเดิมของคุณ

            currentToastStage.setX(centerX + offsetX);
            currentToastStage.setY(centerY + offsetY);
            // -------------------------------------------

            root.setOpacity(1.0);
            if (!currentToastStage.isShowing()) currentToastStage.show();

            if (shouldBlink) {
                // --- แบบที่ 1: กะพริบวูบวาบ (Flash) ---
                currentFade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(150), root);
                currentFade.setFromValue(1.0);
                currentFade.setToValue(0.3);
                currentFade.setCycleCount(6); // กะพริบ ไป-กลับ รวม 6 ครั้ง (3 รอบ)
                currentFade.setAutoReverse(true);

                currentFade.setOnFinished(e -> {
                    // หลังจากกะพริบเสร็จ ให้ค่อยๆ จางหายไป
                    javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(javafx.util.Duration.millis(800), root);
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);
                    fadeOut.setDelay(javafx.util.Duration.millis(500));
                    fadeOut.setOnFinished(ev -> currentToastStage.hide());
                    fadeOut.play();
                });
            } else {
                // --- แบบที่ 2: จางหายปกติ (Normal Fade) ---
                currentFade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(1500), root);
                currentFade.setFromValue(1.0);
                currentFade.setToValue(0.0);
                currentFade.setDelay(javafx.util.Duration.millis(350));
                currentFade.setOnFinished(e -> currentToastStage.hide());
            }

            currentFade.play();
        });
    }
}
