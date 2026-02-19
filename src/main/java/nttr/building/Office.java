package nttr.building;

import nttr.model.ClothingLevel;
import nttr.model.GameState;
import nttr.model.Player;

public class Office extends Building {

    private static final int TIME_COST = 7;
    private static final int HEALTH_LOSS = 8;
    private static final int HAPPY_LOSS = 6;

    public Office() {
        super("Office", "/images/office.png", TIME_COST);
    }

    private int basePay(Player p) {
        int h = p.getWorkHours();
        // Promotions at 100, 200, 400 hours (simplified)
        if (h >= 400) return 45;
        if (h >= 200) return 32;
        if (h >= 100) return 24;
        return 18;
    }

    private ClothingLevel requiredClothes(Player p) {
        int h = p.getWorkHours();
        if (h >= 400) return ClothingLevel.BUSINESS;
        if (h >= 200) return ClothingLevel.SEMI_CASUAL;
        return ClothingLevel.CASUAL;
    }

    @Override
    public void perform(Player player, GameState gameState) {
        ClothingLevel need = requiredClothes(player);
        if (player.getClothingLevel().ordinal() < need.ordinal()) {
            gameState.addLog("Office: Outfit too low. Need " + need.getLabel() + ". Go to Clothing store.");
            player.addStress(4);
            return;
        }

        spendTime(gameState);
        if (gameState.isGameOver()) return;

        int pay = (int) Math.round(basePay(player) * player.getWorkMoneyMultiplier());
        player.addMoney(pay);
        player.addWorkHours(25);

        player.addHealth(-HEALTH_LOSS);
        player.addHappiness(-HAPPY_LOSS);
        player.addStress(10);

        gameState.addLog("Office: Worked. +" + pay + " money, +25 work hours (total " + player.getWorkHours() + ").");
    }
}
