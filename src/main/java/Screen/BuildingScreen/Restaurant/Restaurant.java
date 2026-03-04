package Screen.BuildingScreen.Restaurant;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

/**
 * Building entry for "Restaurant".
 * When interacted with, it opens the RestaurantPopup UI.
 */
public class Restaurant extends Building {

    /**
     * Creates the Restaurant building.
     */
    public Restaurant() {
        super("Restaurant");
    }

    /**
     * Opens the popup screen for this building.
     */
    @Override
    public void interact(GamePane gamePane) {
        RestaurantPopup.show(gamePane);
    }
}