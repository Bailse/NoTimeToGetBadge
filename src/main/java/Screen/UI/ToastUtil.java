package Screen.UI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ToastUtil {
    private static Stage currentToastStage = null;
    private static javafx.animation.FadeTransition currentFade = null;


    public static void showToast(String message, String hexColor, double width, double height, boolean shouldBlink) {
        javafx.application.Platform.runLater(() -> {
            if (currentFade != null) currentFade.stop();

            if (currentToastStage == null) {
                currentToastStage = new Stage();
                currentToastStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
                currentToastStage.setAlwaysOnTop(true);

                Label label = new Label();
                label.setId("toastLabel");
                label.setWrapText(true);
                label.setAlignment(javafx.geometry.Pos.CENTER);
                label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);


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


            label.setText(message);
            label.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85); " +
                    "-fx-padding: 10px; -fx-font-size: 18px; -fx-font-weight: bold; " +
                    "-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-width: 3px; " +
                    "-fx-text-fill: " + hexColor + "; -fx-border-color: " + hexColor + ";");


            label.setPrefSize(width, height);
            label.setMinSize(width, height);
            label.setMaxSize(width, height);


            root.requestLayout();
            currentToastStage.sizeToScene();


            javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();


            double centerX = (screenBounds.getWidth() - width) / 2;
            double centerY = (screenBounds.getHeight() - height) / 2;


            currentToastStage.setX(centerX);
            currentToastStage.setY(centerY);


            root.setOpacity(1.0);
            if (!currentToastStage.isShowing()) currentToastStage.show();

            if (shouldBlink) {

                currentFade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(500), root);
                currentFade.setFromValue(1.0);
                currentFade.setToValue(0.3);
                currentFade.setCycleCount(4);
                currentFade.setAutoReverse(true);

                currentFade.setOnFinished(e -> {

                    javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(javafx.util.Duration.millis(800), root);
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);
                    fadeOut.setDelay(javafx.util.Duration.millis(350));
                    fadeOut.setOnFinished(ev -> currentToastStage.hide());
                    fadeOut.play();
                });
            } else {

                currentFade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(800), root);
                currentFade.setFromValue(1.0);
                currentFade.setToValue(0.0);
                currentFade.setDelay(javafx.util.Duration.millis(500));
                currentFade.setOnFinished(e -> currentToastStage.hide());
            }

            currentFade.play();
        });
    }
}
