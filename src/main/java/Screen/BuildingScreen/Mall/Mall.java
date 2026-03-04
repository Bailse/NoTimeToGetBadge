package Screen.BuildingScreen.Mall;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

/**
 * Building entry for "Mall".
 * When interacted with, it opens the MallPopup UI.
 */
public class Mall extends Building {

    /**
     * Creates the Mall building.
     */
    public Mall() {
        super("Mall");
    }


    /**
     * Opens the popup screen for this building.
     */
    @Override
    public void interact(GamePane gamePane) {
        MallPopup.show(gamePane);
    }
}