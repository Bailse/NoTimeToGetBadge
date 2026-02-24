package Screen.Game;

import Character.Player;
import Logic.GameSession;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;

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
import javafx.util.Duration;

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

        setSpacing(25);
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

        // ===== Bars =====
        VBox staminaBox = createBar("Stamina", 100, "#f4b400");
        VBox healthBox = createBar("Health", 100, "#e53935");
        VBox educationBox = createBar("Education", 100, "#1e88e5");
        VBox moneyBox = createBar("Money", 1000, "#43a047");

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

    private VBox createBar(String name, int max, String color){

        ProgressBar bar = new ProgressBar();
        bar.setPrefWidth(280);
        bar.setPrefHeight(22);

        bar.setStyle(
                "-fx-accent: " + color + ";" +
                        "-fx-control-inner-background: #eeeeee;"
        );

        Label valueLabel = new Label();
        valueLabel.setStyle("-fx-font-weight: bold;");

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

        animateBar(staminaBar, player.getStamina() / 200.0);
        animateBar(healthBar, player.getHealth() / 200.0);
        animateBar(educationBar, player.getEducation() / 200.0);
        animateBar(moneyBar, player.getMoney() / 2000.0);

        staminaLabel.setText(player.getStamina() + " / 200");
        healthLabel.setText(player.getHealth() + " / 200");
        educationLabel.setText(player.getEducation() + " / 200");
        moneyLabel.setText(player.getMoney() + " / 2000");
    }

    private void animateBar(ProgressBar bar, double newValue){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(bar.progressProperty(), newValue))
        );
        timeline.play();
    }

}