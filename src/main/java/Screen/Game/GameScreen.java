package Screen.Game;

import Logic.GamePane;
import Screen.ScreenManager;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;

public class GameScreen extends VBox {


    public GameScreen(ScreenManager game) {

        // ให้ VBox เต็มจอ
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ------------------ TOP BAR ------------------
        Gamebar topbar = new Gamebar();

        topbar.getGo_back().setOnAction(e -> game.showTitle());
        topbar.getEnd_round().setOnAction(e->game.showResult());


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

        GamePane gamePane = new GamePane(Logic.GameSession.getPlayer()!=null ? Logic.GameSession.getPlayer().getImagePath() : null);

// ผูก action
        topbar.setOnActionClick(() -> {

            int r = gamePane.getPlayerRow();
            int c = gamePane.getPlayerCol();

            //System.out.println();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player Location");
            alert.setHeaderText(null);
            alert.setContentText("You are at row: " + r + " col: " + c);
            alert.showAndWait();
        });

// ผูก enable/disable
        gamePane.setOnReachBuilding(() -> {
            topbar.setActionVisible(true);
        });

        gamePane.setOnLeaveBuilding(() -> {
            topbar.setActionVisible(false);
        });

        GridPane.setHgrow(gamePane, Priority.ALWAYS);
        GridPane.setVgrow(gamePane, Priority.ALWAYS);
        mainArea.add(gamePane, 0, 0);
        //mainArea.add(gamePane, 0, 0);
        //mainArea.add(gamebar, 0, 1);

// Status
        VBox statusArea = new VBox();
        statusArea.setStyle("-fx-background-color: lightgray;");
        GridPane.setHgrow(statusArea, Priority.ALWAYS);
        GridPane.setVgrow(statusArea, Priority.ALWAYS);

        StatusTab statusTab = new StatusTab();
        statusArea.getChildren().add(statusTab);

        gamePane.setOnStatusChange(() -> {
            statusTab.updateStatus();
        });

        mainArea.add(statusArea, 1, 0);

        this.getChildren().add(mainArea);
    }
}
