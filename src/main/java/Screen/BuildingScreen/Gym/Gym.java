package Screen.BuildingScreen.Gym;

import Logic.GamePane;
import Screen.BuildingScreen.Building;


/**
 * Building entry for "Gym".
 * When interacted with, it opens the GymPopup UI.
 */
public class Gym extends Building {

    /**
     * Creates the Gym building.
     */
    public Gym() {
        super("Gym");
    }

    /**
     * Opens the popup screen for this building.
     */
    @Override
    public void interact(GamePane gamePane) {
        GymPopup.show(gamePane);
    }
}
