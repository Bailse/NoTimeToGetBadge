package ui;

import Character.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameHUD extends VBox {

    private Label staminaLabel = new Label();
    private Label moneyLabel = new Label();
    private Label educationLabel = new Label();
    private Label healthLabel = new Label();

    private Player player;

    public GameHUD(Player player) {

        this.player = player;

        setSpacing(8);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-background-radius: 10;");

        staminaLabel.setStyle("-fx-text-fill: white;");
        moneyLabel.setStyle("-fx-text-fill: white;");
        educationLabel.setStyle("-fx-text-fill: white;");
        healthLabel.setStyle("-fx-text-fill: white;");

        getChildren().addAll(staminaLabel, moneyLabel, educationLabel, healthLabel);

        refresh();
    }

    public void refresh() {

        player.clampAllStats();

        staminaLabel.setText("Stamina: " + player.getStamina());
        moneyLabel.setText("Money: " + player.getMoney());
        educationLabel.setText("Education: " + player.getEducation());
        healthLabel.setText("Health: " + player.getHealth());
    }
}