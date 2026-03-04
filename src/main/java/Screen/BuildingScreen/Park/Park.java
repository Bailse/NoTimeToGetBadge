package Screen.BuildingScreen.Park;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

/**
 * Building entry for "Park".
 * When interacted with, it opens the ParkPopup UI.
 */
public class Park extends Building {

    /**
     * Creates the Park building.
     */
    public Park() {
        super("Park");
    }

    /**
     * Opens the popup screen for this building.
     */
    @Override
    public void interact(GamePane gamePane) {
        ParkPopup.show(gamePane);
    }
}