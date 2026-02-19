package nttr.building;

import nttr.model.GameState;
import nttr.model.Player;

public class Party extends Building {

    private static final int TIME_COST = 6;
    private static final int MONEY_COST = 12;
    private static final int HEALTH_LOSS = 3;

    public Party() {
        super("Party", "/images/party.png", TIME_COST);
    }

    @Override
    public void perform(Player player, GameState gameState) {
        if (player.getMoney() < MONEY_COST) {
            gameState.addLog("Party: Not enough money to relax (" + MONEY_COST + ").");
            player.addStress(2);
            return;
        }

        spendTime(gameState);
        if (gameState.isGameOver()) return;

        int happyGain = (int) Math.round(14 * player.getRelaxHappinessMultiplier());
        player.addHappiness(happyGain);
        player.addMoney(-MONEY_COST);
        player.addHealth(-HEALTH_LOSS);
        player.addStress(-10);

        gameState.addLog("Party: Relaxed. +" + happyGain + " happiness, -" + MONEY_COST + " money.");
    }
}
