package nttr.building;

import nttr.model.GameState;
import nttr.model.Player;

/**
 * Interface required by assignment. Buildings perform actions.
 */
public interface Actionable {
    void perform(Player player, GameState gameState);
}
