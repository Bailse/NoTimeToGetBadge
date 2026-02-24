package Screen.Game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import Character.BasePlayer;

public class StatusTab extends VBox {

    private ProgressBar staminaBar;
    private ProgressBar healthBar;
    private ProgressBar educationBar;
    private ProgressBar moneyBar;

    public StatusTab(BasePlayer player) {

        setSpacing(20);
        setPadding(new Insets(40));

        Label title = new Label("Status");
        title.setStyle("-fx-font-size: 40;");

        staminaBar = createBar("Stamina", player.getStamina(), 100);
        healthBar = createBar("Health", player.getHealth(), 100);
        educationBar = createBar("Education", player.getEducation(), 100);
        moneyBar = createBar("Money", player.getMoney(), 1000);

        getChildren().addAll(title, staminaBar, healthBar, educationBar, moneyBar);
    }

    private ProgressBar createBar(String name, int value, int max) {
        Label label = new Label(name + ": " + value);
        ProgressBar bar = new ProgressBar((double)value / max);
        bar.setPrefWidth(300);
        getChildren().add(label);
        return bar;
    }

    public void update(BasePlayer player) {
        staminaBar.setProgress(player.getStamina() / 100.0);
        healthBar.setProgress(player.getHealth() / 100.0);
        educationBar.setProgress(player.getEducation() / 100.0);
        moneyBar.setProgress(player.getMoney() / 1000.0);
    }
}