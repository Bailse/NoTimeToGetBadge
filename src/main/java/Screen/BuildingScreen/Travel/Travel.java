package Screen.BuildingScreen.Travel;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

public class Travel extends Building {

    public Travel() {
        super("Travel");
    }

    @Override
    public void interact(GamePane gamePane) {
        TravelPopup.show(gamePane);
    }
}