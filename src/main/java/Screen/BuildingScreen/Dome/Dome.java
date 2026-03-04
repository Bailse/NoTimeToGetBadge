package Screen.BuildingScreen.Dome;

import Logic.GamePaneImage;
import Screen.BuildingScreen.Building;


/**
 * Building entry for "Dome".
 * When interacted with, it opens the DomePopup UI.
 */
public class Dome extends Building {

    /**
     * Creates the Dome building.
     */
    public Dome() {
        super("Dome");
    }

    /**
     * Opens the Dome popup screen.
     */
    @Override
    public void interact(GamePaneImage gamePane) {
        DomePopup.show(gamePane);
    }
}