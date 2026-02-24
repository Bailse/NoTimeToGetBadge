package Screen.Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Gamebar extends BorderPane {

    private Button go_back;
    private Button end_round;
    private Button action;
    private Label roundLabel;

    public Gamebar(){

        go_back = new Button("go Back");
        end_round = new Button("end round");

        roundLabel = new Label("Round: 1/10");
        roundLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        action = new Button("ACTION");
        action.setDisable(true);

        setPadding(new Insets(20));

        setLeft(go_back);
        setRight(end_round);

        VBox centerBox = new VBox(6, roundLabel, action);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);
    }

    public void updateRound(int current, int max){
        roundLabel.setText("Round: " + current + "/" + max);
    }

    public Button getGo_back() {
        return go_back;
    }

    public Button getEnd_round() {
        return end_round;
    }

    public Button getAction() {
        return action;
    }

    public void setActionVisible(boolean visible){
        action.setDisable(!visible);
    }

    public void setOnActionClick(Runnable r) {
        action.setOnAction(e -> r.run());
    }
}
