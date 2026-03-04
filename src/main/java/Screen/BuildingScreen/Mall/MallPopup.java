package Screen.BuildingScreen.Mall;

import Character.BasePlayer;
import Logic.GameSession;
import Screen.BuildingScreen.ShopItem;
import Logic.GamePane;
import Character.Otaku;
import Screen.BuildingScreen.Normal;
import Screen.BuildingScreen.Shopable;
import javafx.application.Platform;
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

import static Screen.UI.ToastUtil.showToast;

/**
 * Popup screen for buying mall items and working for money.
 */
public class MallPopup implements Shopable, Normal {

    /**
     * Resets the sold-out state for all mall items.
     */
    public static void resetAllMallItems() {
        MallItem.resetMallItems();
    }

    /**
     * Items that can be bought in the mall.
     */
    private enum MallItem implements ShopItem {
        PILLOW("PILLOW", 250, "#00ffff",  10, false),
        TUNG_BED("BED SET", 700, "#ffd700",  20, false),
        CAR("CAR", 999, "#ff007f",  0, true);

        private final String name;
        private final int price;
        private final String color;
        private final int healthGain;
        private final boolean isUnique;
        private boolean soldOut = false;

        /**
         * Constructs a MallItem object with the specified values.
         */
        MallItem(String name, int price, String color, int healthGain, boolean isUnique) {
            this.name = name;
            this.price = price;
            this.color = color;

            this.healthGain = healthGain;
            this.isUnique = isUnique;
        }

        /**
         * @return the display name shown on the button
         */
        @Override public String getName() { return name; }

        /**
         * @return the price cost
         */
        @Override public int getPrice() { return price; }

        /**
         * @return the button color (hex string)
         */
        @Override public String getColor() { return color; }

        /**
         * Marks every item as available again (not sold out).
         */
        public static void resetMallItems() {
            for (MallItem item : MallItem.values()) {
                item.soldOut = false;
            }
        }

        /**
         * Applies the selected mall item to the player.
         */
        @Override
        public void execute(GamePane gamePane) {
            BasePlayer player = gamePane.getPlayer();
            if (player.getMoney() >= price) {
                if (this.isUnique) {
                    if (!soldOut) {
                        player.setMoney(player.getMoney() - price);
                        gamePane.addItem(this.name);
                        this.soldOut = true;
                    }
                } else {
                    player.setHealth(player.getHealth() + healthGain);
                    player.setMoney(player.getMoney() - price);
                }
                gamePane.notifyUpdate();
            }
        }
    }

    /**
     * Opens the popup window.
     */
    public static void show(GamePane gamePane) {
        BasePlayer p = gamePane.getPlayer();
        MallPopup popup = new MallPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        Label staminaLabel = new Label("STAMINA: " + p.getStamina());
        Label healthLabel = new Label("HEALTH: " + p.getHealth());
        Label moneyLabel = new Label("MONEY: " + p.getMoney());

        staminaLabel.setStyle("-fx-text-fill: #00ff99; -fx-font-size: 18px;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 18px;");

        HBox optionBox = new HBox(25);
        optionBox.setAlignment(Pos.CENTER);
        optionBox.setPadding(new Insets(40));


        final Runnable[] refreshUI = new Runnable[1];

        refreshUI[0] = () -> {
            staminaLabel.setText("STAMINA: " + p.getStamina());
            healthLabel.setText("HEALTH: " + p.getHealth());
            moneyLabel.setText("MONEY: " + p.getMoney());

            optionBox.getChildren().clear();
            for (MallItem item : MallItem.values()) {

                Button btn = popup.createShopButton(item, gamePane, null);
                btn.setPrefSize(180, 140);

                if (item.isUnique && item.soldOut) {
                    btn.setText("SOLD OUT");
                    btn.setDisable(true);
                    btn.setStyle("-fx-background-color: #333333; -fx-text-fill: gray; -fx-border-radius: 10;");
                } else {
                    btn.setOnAction(e -> {
                        item.execute(gamePane);

                        Platform.runLater(refreshUI[0]);
                    });
                }
                optionBox.getChildren().add(btn);
            }
        };

        Runnable workAction = () -> {

            int staminaCost = (p instanceof Otaku) ? 5 : 10;
            int moneyGain = 200;


            boolean isSuccess = p.work(staminaCost, moneyGain);

            if (isSuccess) {

                if (p instanceof Otaku) {
                    String bonusStatus = ((Otaku) p).earnWorkBonus();


                    if (bonusStatus.equals("OTAKU_BONUS_ACTIVATED")) {
                        showToast("🌸 OTAKU POWER! Bonus: +$700", "pink", 450, 80,false);
                    }
                }
            }

            gamePane.notifyUpdate();
            refreshUI[0].run();
        };

        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "MALL", Color.CYAN, "WORK", "#00ffff",
                workAction, refreshUI[0], staminaLabel, healthLabel, moneyLabel
        );

        refreshUI[0].run();
        root.setCenter(optionBox);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}