package nttr.building;

import nttr.model.GameState;
import nttr.model.Player;

/**
 * Base building. Each building costs time and changes stats.
 */
public abstract class Building implements Actionable {
    private final String name;
    private final String imagePath;
    private final int timeCostSeconds;

    protected Building(String name, String imagePath, int timeCostSeconds) {
        this.name = name;
        this.imagePath = imagePath;
        this.timeCostSeconds = timeCostSeconds;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getTimeCostSeconds() {
        return timeCostSeconds;
    }

    protected void spendTime(GameState gameState) {
        gameState.consumeTime(timeCostSeconds);
    }

    @Override
    public abstract void perform(Player player, GameState gameState);
}
