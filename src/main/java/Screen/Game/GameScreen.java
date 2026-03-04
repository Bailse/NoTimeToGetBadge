package Screen.Game;

import Character.BasePlayer;
import Logic.GamePane;
import Logic.GameSession;
import Screen.BuildingScreen.Building;
import Screen.BuildingScreen.Gym.GymPopup;
import Screen.BuildingScreen.Mall.MallPopup;
import Screen.ScreenManager;
import Audio.SoundManager;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
/**
 * GameScreen set Layer and placing of this game
 */
public class GameScreen extends VBox {

    /**
     * constructor initialize object in GameScreen and update gameScreen when action
     */


    public GameScreen(ScreenManager game) {
        GymPopup.resetAllGymService();
        MallPopup.resetAllMallItems();
        SoundManager.playBackground("background.mp3");

        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ------------------ TOP BAR ------------------
        Gamebar topbar = new Gamebar();
        topbar.updateRound(GameSession.getRound(), GameSession.getMaxRounds());
        topbar.getGo_back().setOnAction(e -> { SoundManager.stopBackground(); game.showTitle(); });

        getChildren().add(topbar);

        // ------------------ MAIN AREA ------------------
        GridPane mainArea = new GridPane();
        VBox.setVgrow(mainArea, Priority.ALWAYS);
        mainArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(55);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(45);

        mainArea.getColumnConstraints().addAll(col1, col2);

        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.ALWAYS);
        mainArea.getRowConstraints().add(row);

        // GamePane (map)
        GamePane gamePane = new GamePane(GameSession.getPlayer() != null ? GameSession.getPlayer().getImagePath() : null);

        // Status
        VBox statusArea = new VBox();
        statusArea.setStyle("-fx-background-color: lightgray;");
        GridPane.setHgrow(statusArea, Priority.ALWAYS);
        GridPane.setVgrow(statusArea, Priority.ALWAYS);

        StatusTab statusTab = new StatusTab();
        statusArea.getChildren().add(statusTab);

        // --- Hook status refresh ---
        gamePane.setOnStatusChange(statusTab::updateStatus);

        // --- Hook action enable/disable ---
        gamePane.setOnReachBuilding(() -> topbar.setActionVisible(true));
        gamePane.setOnLeaveBuilding(() -> topbar.setActionVisible(false));

        // --- Action button (kept from original) ---
        topbar.setOnActionClick(() -> {

            Building building = gamePane.getCurrentBuilding();

            if (building != null) {
                building.interact(gamePane);
            }

        });

        // --- End round logic (NEW) ---
        topbar.getEnd_round().setOnAction(e -> handleEndRound(game, topbar, gamePane, statusTab));

        GridPane.setHgrow(gamePane, Priority.ALWAYS);
        GridPane.setVgrow(gamePane, Priority.ALWAYS);
        mainArea.add(gamePane, 0, 0);
        mainArea.add(statusArea, 1, 0);

        getChildren().add(mainArea);
    }
    /**
     * Control Screen when logic is active
     */
    private void handleEndRound(ScreenManager game, Gamebar topbar, GamePane gamePane, StatusTab statusTab){
        BasePlayer player = GameSession.getPlayer();
        if(player == null) return;

        if(gamePane.isPlayerMoving()){
            return;
        }

        if(GameSession.getRound() == GameSession.getMaxRounds()){
            game.showResult();
            return;
        }

        // 1) increase round counter (max 10)
        GameSession.advanceRound();

        // 2) reset player position to start
        gamePane.resetPlayerToStart();

        // 3) restore stamina to initial value
        player.setStamina(GameSession.getInitialStamina());

        // 4) refresh UI
        topbar.updateRound(GameSession.getRound(), GameSession.getMaxRounds());
        statusTab.updateStatus();
    }
}