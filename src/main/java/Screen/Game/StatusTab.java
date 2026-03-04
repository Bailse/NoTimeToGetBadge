package Screen.Game;

import Character.BasePlayer;
import Logic.GameSession;
import Item.*;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.util.Duration;
/**
 * StatusTab on Right screen to see status of this game
 */
public class StatusTab extends VBox {

    private ProgressBar staminaBar, healthBar, educationBar, moneyBar,happinessBar;
    private Label staminaLabel, healthLabel, educationLabel, moneyLabel,happinessLabel;
    private ImageView[] itemSlots;
    /**
     * constructor initialize object in StatusTab and update StatusTab when action
     */
    public StatusTab() {

        setSpacing(15);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);
        setPrefWidth(480);
        setStyle("-fx-background-color: #1a1a1a; -fx-border-color: #3d3d3d; -fx-border-width: 4;");


        Label title = new Label("GAME STATUS");
        title.setFont(Font.font("Courier New", FontWeight.BLACK, 32));
        title.setTextFill(Color.WHITE);

        StackPane avatarPane = createAvatarSection();

        // --- ส่วน Progress Bars ---
        VBox sBox = createBar("STAMINA", "#ffff00");
        VBox hBox = createBar("HEALTH", "#ff0000");
        VBox eBox = createBar("EDUCATION", "#0000ff");
        VBox mBox = createBar("MONEY", "#00ff00");
        VBox qBox = createBar("HAPPINESS", "#ffff00");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);


        Label invTitle = new Label("> EQUIPMENT");
        invTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 22));
        invTitle.setTextFill(Color.WHITE);

        HBox inventoryBox = new HBox(20);
        inventoryBox.setAlignment(Pos.CENTER);
        itemSlots = new ImageView[3];

        for (int i = 0; i < 3; i++) {
            StackPane slot = new StackPane();
            Rectangle bg = new Rectangle(100, 100);
            bg.setFill(Color.web("#252525"));
            bg.setStroke(Color.web("#555555"));
            bg.setStrokeWidth(3);

            itemSlots[i] = new ImageView();
            itemSlots[i].setFitWidth(80);
            itemSlots[i].setFitHeight(80);

            slot.getChildren().addAll(bg, itemSlots[i]);
            inventoryBox.getChildren().add(slot);
        }

        getChildren().addAll( avatarPane, sBox, hBox, eBox, mBox, qBox,spacer, invTitle, inventoryBox);
        updateStatus();
    }

    /**
     * show avatar image on right screen
     *
     */

    private StackPane createAvatarSection() {
        StackPane pane = new StackPane();
        ImageView avatar = new ImageView();
        BasePlayer p = GameSession.getPlayer();
        if (p != null && p.getImagePath() != null) {
            try {
                avatar.setImage(new Image(getClass().getResourceAsStream(p.getImagePath())));
            } catch (Exception e) {  }
        }
        avatar.setFitWidth(160);
        avatar.setFitHeight(160);
        avatar.setClip(new Rectangle(160, 160));

        Rectangle border = new Rectangle(172, 172);
        border.setStroke(Color.WHITE);
        border.setFill(Color.TRANSPARENT);
        border.setStrokeWidth(3);
        pane.getChildren().addAll(border, avatar);
        return pane;
    }
    /**
     * for create bar on every status
     *
     */
    private VBox createBar(String name, String color) {
        Label nLabel = new Label("> " + name);
        nLabel.setTextFill(Color.WHITE);
        nLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 15));

        ProgressBar bar = new ProgressBar(0);
        bar.setPrefWidth(360);
        bar.setPrefHeight(24);
        bar.setStyle("-fx-accent: " + color + "; -fx-control-inner-background: #333333; -fx-background-radius: 0;");

        Label vLabel = new Label();
        vLabel.setTextFill(Color.WHITE);
        vLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 13));

        StackPane stack = new StackPane(bar, vLabel);
        VBox box = new VBox(5, nLabel, stack);

        if (name.equals("STAMINA")) { staminaBar = bar; staminaLabel = vLabel; }
        else if (name.equals("HEALTH")) { healthBar = bar; healthLabel = vLabel; }
        else if (name.equals("EDUCATION")) { educationBar = bar; educationLabel = vLabel; }
        else if (name.equals("MONEY")) { moneyBar = bar; moneyLabel = vLabel; }
        else if (name.equals("HAPPINESS")){happinessBar = bar; happinessLabel = vLabel;}

        return box;
    }
    /**
     * for update status bar for every status
     *
     */
    public void updateStatus() {
        BasePlayer player = GameSession.getPlayer();
        if (player == null) return;

        animateBar(staminaBar, player.getStamina() / 200.0);
        animateBar(healthBar, player.getHealth() / 200.0);
        animateBar(educationBar, player.getEducation() / 200.0);
        animateBar(moneyBar, player.getMoney() / 10000.0);
        // //
        animateBar(happinessBar, player.getHappiness() / 500.0);



        staminaLabel.setText(player.getStamina() + "/200");
        healthLabel.setText(player.getHealth() + "/200");
        educationLabel.setText(player.getEducation() + "/200");
        moneyLabel.setText("$" + player.getMoney());
        happinessLabel.setText(player.getHappiness() + "/500");

        updateInventoryDisplay(player.getItemManager());
    }

    /**
     * for update Inventory Display bar for every Item
     *
     */

    private void updateInventoryDisplay(Item itemManager) {
        if (itemManager == null) return;

        // รูปเงาจางๆ (Placeholders) เมื่อยังไม่มีของ
        String[] placeholders = {"Item/WheyProtein.png", "Item/book1.png", "Item/Vehicle.png"};


        for (int i = 0; i < 3; i++) {
            BaseItem item = itemManager.getInventory().get(i);
            ImageView slotView = itemSlots[i];

            if (item != null) {

                try {
                    slotView.setImage(new Image(getClass().getResourceAsStream("/" + item.getImage())));
                    slotView.setOpacity(1.0);
                } catch (Exception e) { /* image error */ }
            } else {

                try {
                    slotView.setImage(new Image(getClass().getResourceAsStream("/" + placeholders[i])));
                    slotView.setOpacity(0.2);
                } catch (Exception e) { /* image error */ }
            }
        }
    }

    /**
     * for control increase and decrease bar on status tab
     * @param bar
     * @param newValue
     */

    private void animateBar(ProgressBar bar, double newValue) {
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(300), new KeyValue(bar.progressProperty(), newValue)));
        tl.play();
    }
}