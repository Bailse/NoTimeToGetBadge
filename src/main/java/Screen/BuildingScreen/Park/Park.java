package Screen.BuildingScreen.Park;

import Logic.GamePaneImage;
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
     * Opens the Park popup screen.
     */
    @Override
    public void interact(GamePaneImage gamePane) {
        ParkPopup.show(gamePane);
    }
}