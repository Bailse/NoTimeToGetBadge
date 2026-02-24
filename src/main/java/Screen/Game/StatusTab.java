package Screen.Game;

import Character.Player;
import Logic.GameSession;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class StatusTab extends VBox {

    private ProgressBar staminaBar;
    private ProgressBar healthBar;
    private ProgressBar moneyBar;
    private ProgressBar educationBar;

    private Label staminaLabel;
    private Label healthLabel;
    private Label moneyLabel;
    private Label educationLabel;

    public StatusTab(){

        Player player = GameSession.getPlayer();

        setSpacing(20);
        setPadding(new Insets(30));
        setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Status");
        title.setFont(Font.font(40));

        // ===== Avatar =====
        ImageView avatar;

        if(player != null && player.getImagePath() != null){
            avatar = new ImageView(
                    new Image(getClass().getResourceAsStream(player.getImagePath()))
            );
        } else {
            avatar = new ImageView(
                    new Image(getClass().getResourceAsStream("/player.png"))
            );
        }

        avatar.setFitWidth(200);
        avatar.setFitHeight(200);

        Circle clip = new Circle(100,100,100);
        avatar.setClip(clip);

        //Create Bars
        VBox staminaBox = createBar("Stamina", 100);
        VBox healthBox = createBar("Health", 100);
        VBox educationBox = createBar("Education", 100);
        VBox moneyBox = createBar("Money", 1000);

        updateStatus();

        getChildren().addAll(
                title,
                avatar,
                staminaBox,
                healthBox,
                educationBox,
                moneyBox
        );
    }

    private VBox createBar(String name, int max){

        ProgressBar bar = new ProgressBar();
        bar.setPrefWidth(280);
        bar.setPrefHeight(20);

        Label valueLabel = new Label();

        StackPane stack = new StackPane(bar, valueLabel);
        stack.setAlignment(Pos.CENTER);

        VBox box = new VBox(5, new Label(name), stack);
        box.setAlignment(Pos.CENTER);

        switch (name){
            case "Stamina":
                staminaBar = bar;
                staminaLabel = valueLabel;
                break;
            case "Health":
                healthBar = bar;
                healthLabel = valueLabel;
                break;
            case "Education":
                educationBar = bar;
                educationLabel = valueLabel;
                break;
            case "Money":
                moneyBar = bar;
                moneyLabel = valueLabel;
                break;
        }

        return box;
    }

    public void updateStatus(){

        Player player = GameSession.getPlayer();
        if(player == null) return;

        staminaBar.setProgress(player.getStamina() / 100.0);
        healthBar.setProgress(player.getHealth() / 100.0);
        educationBar.setProgress(player.getEducation() / 100.0);
        moneyBar.setProgress(player.getMoney() / 1000.0);

        staminaLabel.setText(player.getStamina() + " / 100");
        healthLabel.setText(player.getHealth() + " / 100");
        educationLabel.setText(player.getEducation() + " / 100");
        moneyLabel.setText(player.getMoney() + " / 1000");
    }
}