package nttr.building;

import nttr.model.GameState;
import nttr.model.Player;

public class Gym extends Building {

    private static final int TIME_COST = 7;
    private static final int MONEY_COST = 10;

    public Gym() {
        super("Gym", "/images/gym.png", TIME_COST);
    }

    @Override
    public void perform(Player player, GameState gameState) {
        if (player.getMoney() < MONEY_COST) {
            gameState.addLog("Gym: Not enough money for membership (" + MONEY_COST + ").");
            player.addStress(2);
            return;
        }

        spendTime(gameState);
        if (gameState.isGameOver()) return;

        int healthGain = (int) Math.round(12 * player.getExerciseHealthMultiplier());
        player.addHealth(healthGain);
        player.addMoney(-MONEY_COST);
        player.addStress(-4);

        gameState.addLog("Gym: Exercised. +" + healthGain + " health, -" + MONEY_COST + " money.");
    }
}
