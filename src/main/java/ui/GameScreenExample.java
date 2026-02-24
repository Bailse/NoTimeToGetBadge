package ui;

import Character.Player;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class GameScreenExample extends BorderPane {

    public GameScreenExample(Player player) {

        GameHUD hud = new GameHUD(player);
        AnimatedAvatar avatar = new AnimatedAvatar(player.getImagePath());

        Pane map = new Pane();
        map.getChildren().add(avatar);

        setLeft(hud);
        setCenter(map);
    }
}