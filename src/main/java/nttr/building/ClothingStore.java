package nttr.building;

import nttr.model.ClothingLevel;
import nttr.model.GameState;
import nttr.model.Player;

public class ClothingStore extends Building {

    private static final int TIME_COST = 6;

    public ClothingStore() {
        super("Clothing", "/images/clothes.png", TIME_COST);
    }

    @Override
    public void perform(Player player, GameState gameState) {
        spendTime(gameState);
        if (gameState.isGameOver()) return;

        ClothingLevel cur = player.getClothingLevel();
        ClothingLevel next = cur.next();
        if (next == cur) {
            gameState.addLog("Clothing: You already have the best outfit (Business).");
            return;
        }

        int cost = (cur == ClothingLevel.CASUAL) ? 25 : 45;

        if (player.getMoney() < cost) {
            gameState.addLog("Clothing: Not enough money to upgrade outfit (need " + cost + ").");
            player.addStress(3);
            return;
        }

        player.addMoney(-cost);
        player.setClothingLevel(next);
        player.addHappiness(3);
        player.addStress(-2);

        gameState.addLog("Clothing: Upgraded outfit to " + next.getLabel() + " (-" + cost + ").");
    }
}
