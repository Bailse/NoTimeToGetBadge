package Screen.BuildingScreen.Chula;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

public class Chula extends Building {

    public Chula() {
        super("Chula");
    }

    @Override
    public void interact(GamePane gamePane) {
        ChulaPopup.show(gamePane);
    }
}