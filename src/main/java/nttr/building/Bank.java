package nttr.building;

import nttr.model.GameState;
import nttr.model.Player;

public class Bank extends Building {

    private static final int TIME_COST = 5;

    public Bank() {
        super("Bank", "/images/bank.png", TIME_COST);
    }

    @Override
    public void perform(Player player, GameState gameState) {
        spendTime(gameState);
        if (gameState.isGameOver()) return;

        // Default action: deposit 30 if possible, else withdraw 30
        if (player.getMoney() >= 30) {
            player.depositToBank(30);
            gameState.addLog("Bank: Deposited 30. Cash=" + player.getMoney() + ", Bank=" + player.getBankBalance());
        } else if (player.getBankBalance() > 0) {
            player.withdrawFromBank(30);
            gameState.addLog("Bank: Withdrew 30. Cash=" + player.getMoney() + ", Bank=" + player.getBankBalance());
        } else {
            gameState.addLog("Bank: Not enough cash to deposit, and bank is empty.");
        }

        player.addStress(-2);
    }
}
