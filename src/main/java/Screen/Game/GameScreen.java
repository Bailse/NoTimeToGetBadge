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

        // Columns
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(55);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(45);

        mainArea.getColumnConstraints().addAll(col1, col2);

        // Row
        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.ALWAYS);
        mainArea.getRowConstraints().add(row);

// GamePane
        GamePane gamePane = new GamePane();
        GridPane.setHgrow(gamePane, Priority.ALWAYS);
        GridPane.setVgrow(gamePane, Priority.ALWAYS);
        mainArea.add(gamePane, 0, 0);

// Status
        VBox statusArea = new VBox();
        statusArea.setStyle("-fx-background-color: lightgray;");
        GridPane.setHgrow(statusArea, Priority.ALWAYS);
        GridPane.setVgrow(statusArea, Priority.ALWAYS);

        StatusTab s1 = new StatusTab();
        statusArea.getChildren().add(s1);

        mainArea.add(statusArea, 1, 0);

        this.getChildren().add(mainArea);
    }
}
