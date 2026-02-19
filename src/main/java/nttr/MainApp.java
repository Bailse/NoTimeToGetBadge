package nttr;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nttr.model.GameState;
import nttr.model.Player;
import nttr.ui.screens.CharacterSelectScreen;
import nttr.ui.screens.GameScreen;
import nttr.ui.screens.TitleScreen;

/**
 * Entry point for the simplified "No Time To Relax" project.
 *
 * Scene flow:
 * 1) Title screen
 * 2) Character selection
 * 3) Game screen
 */
public class MainApp extends Application {

    public static Runnable RESTART_TO_TITLE;


    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        RESTART_TO_TITLE = this::showTitle;
        stage.setTitle("No Time To Relax — JavaFX");
        showTitle();
        stage.show();
    }

    private void showTitle() {
        TitleScreen title = new TitleScreen(this::showCharacterSelect);
        Scene scene = new Scene(title.getRoot(), WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        stage.setScene(scene);
    }

    private void showCharacterSelect() {
        CharacterSelectScreen cs = new CharacterSelectScreen(
                this::startNewGame,
                this::showTitle
        );
        Scene scene = new Scene(cs.getRoot(), WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        stage.setScene(scene);
    }

    private void startNewGame(Player player) {
        GameState gameState = GameState.defaultGame(player);
        GameScreen gs = new GameScreen(gameState, this::showTitle);
        Scene scene = new Scene(gs.getRoot(), WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
