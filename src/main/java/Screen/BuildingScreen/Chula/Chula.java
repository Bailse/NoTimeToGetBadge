package Screen.BuildingScreen.Chula;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

/**
 * Building entry for "Chula".
 * When interacted with, it opens the ChulaPopup UI.
 */
public class Chula extends Building {

    /**
     * Creates the Chula building.
     */
    public Chula() {
        super("Chula");
    }

    /**
     * Opens the popup screen for this building.
     */
    @Override
    public void interact(GamePane gamePane) {
        ChulaPopup.show(gamePane);
    }
}