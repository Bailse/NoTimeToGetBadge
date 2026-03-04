package Screen;

import Screen.Choosing.ChoosingScreen;
import Screen.Game.GameScreen;
import Screen.HowToPlay.HowToPlayScreen;
import Screen.Result.ResultScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages switching between different screens in the game.
 */
public class ScreenManager {

    /**
     * The main window of the application.
     */
    private Stage stage;

    /**
     * Creates a ScreenManager with the given main stage.
     */
    public ScreenManager(Stage stage){
        setStage(stage);
    }

    /**
     * Shows the title screen.
     */
    public void showTitle(){
        getStage().setScene(new Scene(new TitleScreen(this), 1200,1200));

    }

    /**
     * Shows the choosing screen (character/option selection screen).
     */
    public void showChoose(){
        getStage().setScene(new Scene(new ChoosingScreen(this), 1200 , 1200));
    }

    /**
     * Closes the game window and ends the application.
     */
    public void endGame(){
        getStage().close();
    }

    /**
     * Shows the main game screen.
     */
    public void showGame(){
        getStage().setScene(new Scene(new GameScreen(this),1200,1200));
    }

    /**
     * Returns the main stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the main stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Shows the "How To Play" screen.
     */
    public void showHowToPlay(){
        getStage().setScene(new Scene(new HowToPlayScreen(this),1200,1200));
    }

    /**
     * Shows the result screen (game ending / summary).
     */
    public void showResult(){
        getStage().setScene(new Scene(new ResultScreen(this),1200,1200));
    }
}
