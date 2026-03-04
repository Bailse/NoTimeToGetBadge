package Screen.BuildingScreen.Gym;

import Logic.GamePaneImage;
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
     * Opens the Gym popup screen.
     */
    @Override
    public void interact(GamePaneImage gamePane) {
        GymPopup.show(gamePane);
    }
}
