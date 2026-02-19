package nttr.building;

import nttr.model.GameState;
import nttr.model.Player;

public class Home extends Building {
    private static final int TIME_COST = 6;
    private static final int HEALTH_GAIN = 10;
    private static final int HAPPY_GAIN = 6;

    public Home() {
        super("Home", "/images/home.png", TIME_COST);
    }

    @Override
    public void perform(Player player, GameState gameState) {
        spendTime(gameState);
        if (gameState.isGameOver()) return;

        player.addHealth(HEALTH_GAIN);
        player.addHappiness(HAPPY_GAIN);
        player.addStress(-8);

        // If you have food, you can eat at home as well
        if (!player.isAteToday()) {
            boolean ate = player.consumeMealIfAny();
            if (ate) {
                gameState.addLog("Home: Rested + ate at home. Meals left: " + player.getFridgeMeals());
            } else {
                gameState.addLog("Home: Rested. No meals to eat.");
            }
        } else {
            gameState.addLog("Home: Rested. (+Health, +Happiness, -Stress)");
        }
    }
}
