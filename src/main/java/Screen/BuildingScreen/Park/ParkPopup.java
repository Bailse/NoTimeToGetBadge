package Screen.BuildingScreen.Park;


import Logic.GamePane;
import Screen.BuildingScreen.Normal;
import Screen.BuildingScreen.ShopItem;
import Screen.BuildingScreen.Shopable;
import Character.BasePlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ParkPopup implements Shopable, Normal {


    private enum ParkAction implements ShopItem {
        WALK("WALK 🚶\n-7 ⚡", 0, "#00cc66", 7, 5,2),
        RELAX("RELAX 🍃\n-5 ⚡", 0, "#00cc66", 5, 5,0);

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int happinessGain;
        private final int healthGain;

        ParkAction(String name, int price, String color, int staminaCost, int happinessGain, int healthGain) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.happinessGain = happinessGain;
            this.healthGain = healthGain;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        @Override
        public void execute(GamePane gamePane) {
            BasePlayer p = gamePane.getPlayer();

            if (p.getStamina() >= staminaCost) {
                p.setStamina(p.getStamina() - staminaCost);
                p.setHappiness(p.getHappiness() + happinessGain);
                p.setHealth(p.getHealth() + healthGain);
            }
        }
    }

    public static void show(GamePane gamePane) {
        BasePlayer p = gamePane.getPlayer();

        ParkPopup popup = new ParkPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);



        Label staminaLabel = new Label("STAMINA: " + p.getStamina());
        Label happinessLabel = new Label("HAPPINESS: " + p.getHappiness());
        Label healthLabel = new Label("HEALTH: " + p.getHealth());


        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px; -fx-font-weight: bold;");
        happinessLabel.setStyle("-fx-text-fill: #FF69B4; -fx-font-size: 18px; -fx-font-weight: bold;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px; -fx-font-weight: bold;");


        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + p.getStamina());
            happinessLabel.setText("HAPPINESS: " + p.getHappiness());
            healthLabel.setText("HEALTH: " + p.getHealth());
        };


        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "PARK", Color.web("#00cc66"),
                null, null, null,
                refreshUI,
                staminaLabel, happinessLabel,healthLabel
        );


        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (ParkAction park : ParkAction.values()) {
            Button btn = popup.createShopButton(park, gamePane, refreshUI);
            btn.setPrefSize(220, 160);
            optionsBox.getChildren().add(btn);
        }

        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}