package Screen.Game;

import Audio.SoundManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Gamebar on top screen to use method of this game
 */


public class Gamebar extends BorderPane {

    private Button go_back;
    private Button end_round;
    private Button action;
    private Button musicToggle;
    private Label roundLabel;

    /**
     * constructor initialize object in Gamebar
     */

    public Gamebar(){

        go_back = new Button("Go Back");
        end_round = new Button("End Round");

        musicToggle = new Button("🔊");
        musicToggle.setOnAction(e -> {
            SoundManager.toggleMute();
            musicToggle.setText(SoundManager.isMuted() ? "🔇" : "🔊");
        });

        roundLabel = new Label("Round: 1/10");
        roundLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        action = new Button("ACTION");
        action.setDisable(true);

        setPadding(new Insets(20));

        setLeft(go_back);

        HBox rightBox = new HBox(10, musicToggle, end_round);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        setRight(rightBox);

        VBox centerBox = new VBox(6, roundLabel, action);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);
    }
    /**
     * update game round when endRound
     */
    public void updateRound(int current, int max){
        roundLabel.setText("Round: " + current + "/" + max);
    }

    /**
     * get go_back button
     */
    public Button getGo_back() {
        return go_back;
    }

    /**
     * get End_round button
     */

    public Button getEnd_round() {
        return end_round;
    }

    /**
     * get Action button
     */

    public Button getAction() {
        return action;
    }

    /**
     * set ActionVisible button
     */

    public void setActionVisible(boolean visible){
        action.setDisable(!visible);
    }

    /**
     * set thread Action button
     */

    public void setOnActionClick(Runnable r) {
        action.setOnAction(e -> r.run());
    }
}
