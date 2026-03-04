package Screen.BuildingScreen.Travel;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

/**
 * Building entry for "Travel".
 * When interacted with, it opens the TravelPopup UI.
 */
public class Travel extends Building {

    /**
     * Creates the Travel building.
     */
    public Travel() {
        super("Travel");
    }

    /**
     * Opens the travel popup screen.
     */
    @Override
    public void interact(GamePane gamePane) {
        TravelPopup.show(gamePane);
    }
}