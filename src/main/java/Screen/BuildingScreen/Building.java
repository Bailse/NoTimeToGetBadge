package Screen.BuildingScreen;

import Logic.GamePane;

/**
 * Base class for all buildings in the game.
 */
public abstract class Building {

    protected String name;

    /**
     * Creates a building with the given display name.
     */
    public Building(String name) {
        this.name = name;
    }

    /**
     * Called when the player interacts with this building.
     */
    public abstract void interact(GamePane gamePane);
}