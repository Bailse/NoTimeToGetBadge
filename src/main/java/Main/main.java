package Main;

import Audio.SoundManager;
import Screen.UI.ScreenManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class to start application
 */

public class main extends Application {

    /**
     * initialize start screen to begin game
     */
    @Override
    public void start(Stage TitleStage) throws Exception {

        Scene scene = new Scene(new VBox(), 1200, 1200);
        TitleStage.setScene(scene);

        ScreenManager manager = new ScreenManager(TitleStage);
        manager.showTitle();

        TitleStage.setWidth(1200);
        TitleStage.setHeight(800);
        TitleStage.setTitle("No time to get badge");
        TitleStage.setResizable(false);
        TitleStage.show();

        SoundManager.playBackground("background2.mp3");
    }

    public static void main(String[] args) {
        launch(args);
    }
}