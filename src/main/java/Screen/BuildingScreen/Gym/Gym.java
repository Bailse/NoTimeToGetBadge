package Screen.BuildingScreen.Gym;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

public class Gym extends Building {

    public Gym() {
        super("Gym");
    }

    @Override
    public void interact(GamePane gamePane) {
        GymPopup.show(gamePane);
    }
}
