package nttr.building;

import nttr.model.GameState;
import nttr.model.Player;

public class Market extends Building {

    private static final int TIME_COST = 6;
    private static final int COST = 12;

    public Market() {
        super("Market", "/images/market.png", TIME_COST);
    }

    @Override
    public void perform(Player player, GameState gameState) {
        spendTime(gameState);
        if (gameState.isGameOver()) return;

        if (player.getMoney() < COST) {
            gameState.addLog("Market: Not enough money to buy groceries.");
            player.addStress(2);
            return;
        }

        player.addMoney(-COST);
        player.addFridgeMeals(2);
        player.setAteToday(true); // you eat something while shopping
        player.addHealth(2);
        player.addHappiness(1);
        player.addStress(-1);

        gameState.addLog("Market: Bought groceries (-" + COST + "), +2 meals (fridge now " + player.getFridgeMeals() + ").");
    }
}
