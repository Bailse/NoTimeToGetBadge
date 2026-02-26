package Screen.BuildingScreen.Park;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

public class Park extends Building {

    public Park() {
        super("Park");
    }

    @Override
    public void interact(GamePane gamePane) {
        ParkPopup.show(gamePane);
    }
}