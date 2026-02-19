package nttr.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/** Simple title screen with a Start button. */
public class TitleScreen {

    private final BorderPane root;

    public TitleScreen(Runnable onStart) {
        root = new BorderPane();
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: #161616; -fx-font-family: 'Segoe UI';");

        VBox center = new VBox(14);
        center.setAlignment(Pos.CENTER);

        Label title = new Label("NO TIME TO RELAX");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 40px; -fx-font-weight: 800;");

        Label subtitle = new Label("A tiny life-sim: manage time, money, health, skill, and happiness.");
        subtitle.setStyle("-fx-text-fill: #cfcfcf; -fx-font-size: 14px;");

        Button start = new Button("Start Game");
        start.setPrefWidth(220);
        start.setPrefHeight(44);
        start.setStyle("""
                -fx-background-color: #2f7cff;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-background-radius: 12;
                """);
        start.setOnAction(e -> onStart.run());

        Label hint = new Label("Tip: press Start in-game to run the day timer.");
        hint.setStyle("-fx-text-fill: #9c9c9c; -fx-font-size: 12px;");

        center.getChildren().addAll(title, subtitle, start, hint);
        root.setCenter(center);
    }

    public Parent getRoot() {
        return root;
    }
}
