package nttr.model;

/**
 * An interface required by the assignment.
 * Items can provide passive bonuses by applying effects to the player.
 */
public interface BonusProvider {
    void applyTo(Player player);
}
