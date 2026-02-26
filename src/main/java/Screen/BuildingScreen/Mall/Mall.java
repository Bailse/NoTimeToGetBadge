package Screen.BuildingScreen.Mall;

import Logic.GamePane;
import Screen.BuildingScreen.Building;

public class Mall extends Building {

    public Mall() {
        super("Mall");
    }

    @Override
    public void interact(GamePane gamePane) {
        MallPopup.show(gamePane);
    }
}