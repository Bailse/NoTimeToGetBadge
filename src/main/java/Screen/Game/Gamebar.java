package Screen.Game;

import Screen.ScreenManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;



public class Gamebar extends BorderPane {

    private Button go_back;
    private Button end_round;
    private Button action;

    public Gamebar(){

        this.go_back = new Button("go Back");
        this.end_round = new Button("end round");
        this.action = new Button("ACTION");
        this.action.setDisable(true);
        setPadding(new Insets(20));
        this.setLeft(go_back);
        this.setRight(end_round);
        this.setCenter(action);
    }

    public Button getGo_back() {
        return go_back;
    }

    public void setGo_back(Button go_back) {
        this.go_back = go_back;
    }

    public Button getEnd_round() {
        return end_round;
    }

    public void setEnd_round(Button end_round) {
        this.end_round = end_round;
    }

    public Button getAction() {
        return action;
    }

    public void setAction(Button action) {
        this.action = action;
    }
}




