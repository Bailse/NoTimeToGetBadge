package Screen;

import Screen.Choosing.ChoosingScreen;
import Screen.Game.GameScreen;
import Screen.Result.ResultScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Character.BasePlayer;
import Character.Player;

public class ScreenManager{

    private BasePlayer player;
    private Stage stage;

    public ScreenManager(Stage stage){
        setStage(stage);
        this.player = new Player();
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
        getStage().setScene(new Scene(new GameScreen(this,player),1200,1200));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showResult() {getStage().setScene(new Scene(new ResultScreen(this), 1200, 1200));}
}
