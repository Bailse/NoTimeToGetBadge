package nttr.building;

import nttr.model.ClothingLevel;
import nttr.model.GameState;
import nttr.model.Player;

public class University extends Building {

    private static final int TIME_COST = 8;
    private static final int MONEY_COST = 14;
    private static final int HAPPY_LOSS = 5;

    public University() {
        super("University", "/images/university.png", TIME_COST);
    }

    @Override
    public void perform(Player player, GameState gameState) {
        // Require at least Semi-Casual to study (inspired by guide)
        if (player.getClothingLevel().ordinal() < ClothingLevel.SEMI_CASUAL.ordinal()) {
            gameState.addLog("University: Need at least Semi-Casual outfit to attend. Go to Clothing store.");
            player.addStress(4);
            return;
        }

        if (player.getMoney() < MONEY_COST) {
            gameState.addLog("University: Not enough money for course fee (" + MONEY_COST + ").");
            player.addStress(3);
            return;
        }

        spendTime(gameState);
        if (gameState.isGameOver()) return;

        int gain = (int) Math.round(10 * player.getStudySkillMultiplier());
        player.addSkill(gain);
        player.addMoney(-MONEY_COST);
        player.addHappiness(-HAPPY_LOSS);
        player.addStress(8);

        gameState.addLog("University: Studied. +" + gain + " skill, -" + MONEY_COST + " money.");
    }
}
