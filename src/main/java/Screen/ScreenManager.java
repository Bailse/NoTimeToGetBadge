package Screen;

import Screen.Choosing.ChoosingScreen;
import Screen.Game.GameScreen;
import Screen.HowToPlay.HowToPlayScreen;
import Screen.Result.ResultScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {

    private Stage stage;

    public ScreenManager(Stage stage){
        setStage(stage);
    }

    public void showTitle(){
        getStage().setScene(new Scene(new TitleScreen(this), 1200,1200));
    }

    public void showChoose(){
        getStage().setScene(new Scene(new ChoosingScreen(this), 1200 , 1200));
    }

    public void endGame(){
        getStage().close();
    }

    public void showGame(){
        getStage().setScene(new Scene(new GameScreen(this),1200,1200));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showHowToPlay(){
        getStage().setScene(new Scene(new HowToPlayScreen(this),1200,1200));
    }
    public void showResult(){
        getStage().setScene(new Scene(new ResultScreen(this),1200,1200));
    }
}
