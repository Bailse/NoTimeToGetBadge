package Screen.BuildingScreen.Dome;


import Logic.GamePane;
import Character.BasePlayer;
import Screen.BuildingScreen.Normal;
import Screen.BuildingScreen.ShopItem;
import Screen.BuildingScreen.Shopable;
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

/**
 * Popup screen for dormitory actions (sleep/relax).
 */

public class DomePopup implements Shopable, Normal {

    /**
     * Actions inside the dormitory.
     */
    private enum DomeAction implements ShopItem {
        SLEEP("SLEEP 💤\n-10 ⚡", 0, "#ffaa00", 10, 10),  // เพิ่ม Stamina 40, เพิ่ม Happiness 5
        RELAX("RELAX 🎮\n-15 ⚡", 0, "#ff66ff", 15, 15); // เพิ่ม Stamina 15, เพิ่ม Happiness 15

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int happinessGain;

        /**
         * Constructs a DomeAction object with the specified values.
         */
        DomeAction(String name, int price, String color, int staminaCost, int happinessGain) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.happinessGain = happinessGain;
        }

        /**
         * @return the display name shown on the button
         */
        @Override
        public String getName() {
            return name;
        }

        /**
         * @return the price cost
         */
        @Override
        public int getPrice() {
            return price;
        }

        /**
         * @return the button color (hex string)
         */
        @Override
        public String getColor() {
            return color;
        }

        /**
         * Applies the selected dormitory action to the player.
         */
        @Override
        public void execute(GamePane gamePane) {
            BasePlayer p = gamePane.getPlayer();
            if (p.getStamina() >= staminaCost) {
                if(p.getHappiness() < 500){
                    p.setStamina(p.getStamina() - staminaCost);
                    p.setHappiness(p.getHappiness() + happinessGain);
                }
                else {
                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    alert.setTitle("Happiness Status");
                    alert.setHeaderText(null);
                    alert.setContentText("Your Happiness is already full! (Max: 500)");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Opens the dOME popup window and allows the player to sleep or relax.
     */
    public static void show(GamePane gamePane) {
        BasePlayer p = gamePane.getPlayer();

        DomePopup popup = new DomePopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);


        Label staminaLabel = new Label("STAMINA: " + p.getStamina());
        Label happinessLabel = new Label("HAPPINESS: " + p.getHappiness());


        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px; -fx-font-weight: bold;");
        happinessLabel.setStyle("-fx-text-fill: #FF69B4; -fx-font-size: 18px; -fx-font-weight: bold;"); // สีชมพูสำหรับความสุข


        Runnable refreshUI = () -> {
            staminaLabel.setText("STAMINA: " + p.getStamina());
            happinessLabel.setText("HAPPINESS: " + p.getHappiness());
        };


        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "DORMITORY", Color.web("#ffaa00"),
                null, null, null,
                refreshUI,
                staminaLabel, happinessLabel
        );


        HBox optionsBox = new HBox(30);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(30));

        for (DomeAction dome : DomeAction.values()) {
            Button btn = popup.createShopButton(dome, gamePane, refreshUI);
            btn.setPrefSize(220, 160);
            optionsBox.getChildren().add(btn);
        }

        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 900, 550);
        stage.setScene(scene);
        stage.showAndWait();
    }
}