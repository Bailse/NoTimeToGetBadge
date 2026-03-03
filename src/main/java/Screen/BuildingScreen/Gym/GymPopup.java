package Screen.BuildingScreen.Gym;

import Item.HealthThing;
import Item.Item;
import Item.WheyProtein;
import Logic.GameSession;
import Screen.BuildingScreen.Mall.MallPopup;
import Screen.BuildingScreen.ShopItem;
import Logic.GamePane;
import Character.BasePlayer;
import Character.GymBro;
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

public class GymPopup implements Shopable, Normal {
    public static void resetAllGymService() {
        GymService.resetGymService();
    }
    private enum GymService implements ShopItem {
        WORKOUT("WORK OUT", 250, "#e94560", 5, 10, false),
        POWER("POWER", 500, "#e94560", 10, 25, false),
        BEAST("BEAST", 1000, "#e94560", 20, 40, false),
        PROTEIN("WHEY\nPROTEIN", 900, "#e94560", 0, 0, true);

        private final String name;
        private final int price;
        private final String color;
        private final int staminaCost;
        private final int healthGain;
        private final boolean isUnique;
        private boolean isSoldOut = false;

        GymService(String name, int price, String color, int staminaCost, int healthGain, boolean isUnique) {
            this.name = name;
            this.price = price;
            this.color = color;
            this.staminaCost = staminaCost;
            this.healthGain = healthGain;
            this.isUnique = isUnique;
        }

        @Override public String getName() { return name; }
        @Override public int getPrice() { return price; }
        @Override public String getColor() { return color; }

        public static void resetGymService() {
            for (GymService item : GymService.values()) {
                item.isSoldOut = false;
            }
        }

        @Override
        public void execute(GamePane gamePane) {
            BasePlayer p = gamePane.getPlayer();
            if (p.getMoney() < this.price) return;

            if (this == PROTEIN) {
                if (!isSoldOut) {
                    p.setMoney(p.getMoney() - price);
                    gamePane.addItem("Whey Protein");
                    this.isSoldOut = true;
                }
            } else {
                if (p.getStamina() >= staminaCost ) {
                    if(p.getHealth() < 200) {
                        int bonus = 0;

                        if (p.getItemManager().getInventory().get(0) != null) {
                            bonus = (healthGain * 20 / 100);
                        }

                        p.setMoney(p.getMoney() - price);
                        p.setStamina(p.getStamina() - staminaCost);
                        p.setHealth(p.getHealth() + healthGain + bonus);
                    }else{
                        showToast("💪 ULTRA INSTINCT HEALTH! (MAX)", "orange", 400, 50, true);
                    }
                }
            }
            gamePane.notifyUpdate();
        }
    }

    public static void show(GamePane gamePane) {
        BasePlayer p = gamePane.getPlayer();

        GymPopup popup = new GymPopup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);


        Label staminaLabel = new Label();
        Label healthLabel = new Label();
        Label moneyLabel = new Label();

        staminaLabel.setStyle("-fx-text-fill: #00FFAA; -fx-font-size: 18px;");
        healthLabel.setStyle("-fx-text-fill: #ff4d4d; -fx-font-size: 18px;");
        moneyLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px;");


        HBox optionsBox = new HBox(20);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(20));


        Runnable refreshUI = () -> {

            staminaLabel.setText("STAMINA: " + p.getStamina());
            healthLabel.setText("HEALTH: " + p.getHealth());
            moneyLabel.setText("$" + p.getMoney());


            optionsBox.getChildren().clear();
            for (GymService service : GymService.values()) {
                Button btn = popup.createShopButton(service, gamePane, null);
                btn.setPrefSize(170, 140);


                if (service.isUnique && service.isSoldOut) {
                    btn.setText("SOLD OUT");
                    btn.setDisable(true);
                    btn.setStyle("-fx-background-color: #333333; -fx-text-fill: #777777;");
                } else {

                    btn.setOnAction(e -> {
                        service.execute(gamePane);

                        Platform.runLater(() -> {
                            staminaLabel.setText("STAMINA: " + p.getStamina());
                            healthLabel.setText("HEALTH: " + p.getHealth());
                            moneyLabel.setText("$" + p.getMoney());
                            if (service.isUnique && service.isSoldOut) {
                                btn.setText("SOLD OUT");
                                btn.setDisable(true);
                                btn.setStyle("-fx-background-color: #333333; -fx-text-fill: #777777;");
                            }
                        });
                    });
                }
                optionsBox.getChildren().add(btn);
            }
        };


        Runnable workAction = () -> {
            int staminaCost = (p instanceof GymBro) ? 7 : 10;
            int moneyGain = 200;


            boolean isSuccess = p.work(staminaCost, moneyGain);

            if (isSuccess) {

                if (p instanceof GymBro) {
                    String bonusStatus = ((GymBro) p).earnWorkBonus();


                    if (bonusStatus.equals("GYM_BONUS_ACTIVATED")) {
                        showToast("💪 GYMBRO BONUS: +$500", "gold", 300, 70,false);
                    }
                }
            }

            gamePane.notifyUpdate();
            refreshUI.run();
        };


        BorderPane root = popup.createBaseLayout(
                stage, gamePane, "GYM", Color.web("#00FFAA"),
                "WORK", "#00FFAA", workAction, refreshUI,
                staminaLabel, healthLabel, moneyLabel
        );

        refreshUI.run();
        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 850, 520);
        stage.setScene(scene);
        stage.showAndWait();
    }
}