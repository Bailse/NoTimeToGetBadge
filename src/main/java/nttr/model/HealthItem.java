package nttr.model;

/**
 * Health item: increases health gained from exercise/rest.
 */
public class HealthItem extends Item {
    private final double healthBoost;

    public HealthItem(String name, String imagePath, double healthBoost) {
        super(name, imagePath, ItemType.HEALTH, "Better recovery → gain more health.");
        this.healthBoost = healthBoost;
    }

    @Override
    public void applyTo(Player player) {
        player.setExerciseHealthMultiplier(player.getExerciseHealthMultiplier() * healthBoost);
    }
}
