package Screen.BuildingScreen.Restaurant;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

public class Restaurant extends Building {

    public Restaurant() {
        super("Restaurant");
    }

    @Override
    public void interact(GamePane gamePane) {
        RestaurantPopup.show(gamePane);
    }
}