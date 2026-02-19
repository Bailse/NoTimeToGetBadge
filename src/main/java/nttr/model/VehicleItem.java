package nttr.model;

/**
 * Vehicle item:
 * - Boosts work money (as before, so Office stays consistent)
 * - ALSO reduces travel time (multiplier < 1) by roughly the same percentage.
 */
public class VehicleItem extends Item {
    private final double workMoneyBoost;

    public VehicleItem(String name, String imagePath, double workMoneyBoost) {
        super(name, imagePath, ItemType.VEHICLE, "Commute smarter → earn more + travel faster.");
        this.workMoneyBoost = workMoneyBoost;
    }

    @Override
    public void applyTo(Player player) {
        // Work money boost
        player.setWorkMoneyMultiplier(player.getWorkMoneyMultiplier() * workMoneyBoost);

        // Travel time reduction: if boost is 1.05 (5%),
        // travel multiplier becomes 0.95 (5% faster).
        double travelMult = 2.0 - workMoneyBoost;
        if (travelMult < 0.60) travelMult = 0.60; // safety
        if (travelMult > 1.40) travelMult = 1.40; // safety
        player.setTravelTimeMultiplier(player.getTravelTimeMultiplier() * travelMult);
    }
}
