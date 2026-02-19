package nttr.model;

/**
 * Money item: increases happiness gained from relaxing (less stress about cash).
 */
public class MoneyItem extends Item {
    private final double relaxBoost;

    public MoneyItem(String name, String imagePath, double relaxBoost) {
        super(name, imagePath, ItemType.MONEY, "Financial cushion → relaxing feels better.");
        this.relaxBoost = relaxBoost;
    }

    @Override
    public void applyTo(Player player) {
        player.setRelaxHappinessMultiplier(player.getRelaxHappinessMultiplier() * relaxBoost);
    }
}
