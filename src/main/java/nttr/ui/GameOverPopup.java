package nttr.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameOverPopup {

    public static Node showFullScreen(StackPane host, boolean win, int score, Runnable onRestartToTitle) {
        host.getChildren().removeIf(n -> "gameover-overlay".equals(n.getId()));

        Label title = new Label(win ? "YOU WIN!" : "YOU LOSE");
        title.setStyle("-fx-font-size: 64px; -fx-font-weight: 1000;");
        title.setTextFill(win ? Color.web("#00ff88") : Color.web("#ff4d4d"));

        Label scoreLbl = new Label("Final Score: " + score);
        scoreLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: 900; -fx-text-fill: white;");

        Button toTitle = new Button("Back to Title");
        toTitle.getStyleClass().add("btn-primary");
        toTitle.setOnAction(e -> {
            host.getChildren().removeIf(n -> "gameover-overlay".equals(n.getId()));
            if (onRestartToTitle != null) onRestartToTitle.run();
        });

        HBox btnRow = new HBox(12, toTitle);
        btnRow.setAlignment(Pos.CENTER);

        VBox panel = new VBox(18, title, scoreLbl, btnRow);
        panel.setAlignment(Pos.CENTER);
        panel.setMaxWidth(720);
        panel.setStyle(
                "-fx-background-color: rgba(20,20,20,0.92);" +
                "-fx-padding: 40;" +
                "-fx-background-radius: 22;" +
                "-fx-border-radius: 22;" +
                "-fx-border-color: rgba(255,255,255,0.14);"
        );

        StackPane overlay = new StackPane(panel);
        overlay.setId("gameover-overlay");
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.82);");
        overlay.setPickOnBounds(true);
        overlay.prefWidthProperty().bind(host.widthProperty());
        overlay.prefHeightProperty().bind(host.heightProperty());

        host.getChildren().add(overlay);

        FadeTransition ft = new FadeTransition(Duration.millis(220), overlay);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        return overlay;
    }
}
