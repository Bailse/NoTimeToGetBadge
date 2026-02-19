package Screen.Game;

import Logic.GamePane;
import Screen.ScreenManager;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class GameScreen extends VBox {

    public GameScreen(ScreenManager game) {

        // ให้ VBox เต็มจอ
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ------------------ TOP BAR ------------------
        Gamebar topbar = new Gamebar();

        topbar.getGo_back().setOnAction(e -> game.showTitle());
        topbar.getEnd_round().setOnAction(e->game.showTitle());


        this.getChildren().add(topbar);

        // ------------------ MAIN AREA ------------------
        GridPane mainArea = new GridPane();
        VBox.setVgrow(mainArea, Priority.ALWAYS);
        mainArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // กำหนด 70 / 30
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(70);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);

        mainArea.getColumnConstraints().addAll(col1, col2);

        // ------------------ GAME AREA (70%) ------------------

        GamePane gamePane = new GamePane();
        gamePane.setMaxSize(Double.MAX_VALUE , Double.MAX_VALUE);

        mainArea.add(gamePane,0,0);


        // ------------------ STATUS AREA (30%) ------------------
        VBox statusArea = new VBox();
        statusArea.setStyle("-fx-background-color: lightgray;");
        statusArea.setPadding(new Insets(10));
        statusArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


        mainArea.add(statusArea, 1, 0);

        this.getChildren().add(mainArea);
    }
}
