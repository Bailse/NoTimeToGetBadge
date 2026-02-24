package Screen.Game;

import Character.Player;
import Logic.GameSession;
import Screen.ScreenManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EndGameScreen extends VBox {

    public EndGameScreen(ScreenManager manager){
        setSpacing(18);
        setPadding(new Insets(40));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #111111; -fx-font-family: 'Segoe UI';");

        Label title = new Label("GAME OVER");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 64px; -fx-font-weight: 800;");

        Label round = new Label("Round: " + GameSession.getRound() + "/" + GameSession.getMaxRounds());
        round.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 22px;");

        Player p = GameSession.getPlayer();

        Label stats = new Label(buildStatsText(p));
        stats.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");
        stats.setAlignment(Pos.CENTER);

        Button back = new Button("Back to Title");
        back.setStyle("-fx-font-size: 18px; -fx-padding: 10 18 10 18;");
        back.setOnAction(e -> manager.showTitle());

        Button exit = new Button("Exit");
        exit.setStyle("-fx-font-size: 18px; -fx-padding: 10 18 10 18;");
        exit.setOnAction(e -> manager.endGame());

        getChildren().addAll(title, round, stats, back, exit);
    }

    private String buildStatsText(Player p){
        if(p == null){
            return "No player data.";
        }
        return "Stamina: " + p.getStamina() + "\n"
                + "Health: " + p.getHealth() + "\n"
                + "Money: " + p.getMoney() + "\n"
                + "Education: " + p.getEducation();
    }
}
