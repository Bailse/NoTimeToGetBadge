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
     * Opens the Chula popup screen.
     */
    @Override
    public void interact(GamePane gamePane) {
        ChulaPopup.show(gamePane);
    }
}