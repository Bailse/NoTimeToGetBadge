package Screen.BuildingScreen.Chula;

import Logic.GamePaneImage;
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
    public void interact(GamePaneImage gamePane) {
        ChulaPopup.show(gamePane);
    }
}