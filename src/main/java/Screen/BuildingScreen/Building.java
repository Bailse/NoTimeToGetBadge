package Screen.BuildingScreen;

import Logic.GamePane;

public abstract class Building {

    protected String name;

    public Building(String name) {
        this.name = name;
    }

    public abstract void interact(GamePane gamePane);
}