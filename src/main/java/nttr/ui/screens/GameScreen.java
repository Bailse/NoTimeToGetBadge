package nttr.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import nttr.model.GameState;
import nttr.ui.GameView;

/** Wraps {@link GameView} and adds a top-level "Back to Title" control. */
public class GameScreen {

    private final BorderPane root;

    public GameScreen(GameState gameState, Runnable onExitToTitle) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #161616; -fx-font-family: 'Segoe UI';");

        GameView view = new GameView(gameState);

        // Simple navigation bar
        HBox nav = new HBox(10);
        nav.setPadding(new Insets(10));
        nav.setAlignment(Pos.CENTER_LEFT);

        Button back = new Button("← Title");
        back.setStyle("-fx-background-color: #2a2a2a; -fx-text-fill: white; -fx-background-radius: 10;");
        back.setOnAction(e -> {
            // stop timer thread cleanly
            view.stop();
            onExitToTitle.run();
        });

        nav.getChildren().add(back);

        root.setTop(nav);
        root.setCenter(view.getRoot());
    }

    public Parent getRoot() {
        return root;
    }
}
