package Screen.BuildingScreen.Dome;

import Logic.GamePane;
import Screen.BuildingScreen.Building;


public class Dome extends Building {

    public Dome() {
        super("Dome");
    }

    @Override
    public void interact(GamePane gamePane) {
        DomePopup.show(gamePane);
    }
}