package Item;

import Character.BasePlayer;

public class HealthThing extends BaseItem {

    private double healthDecreaseReduction; // เช่น 0.25 = ลด health cost 25%

    public HealthThing(String name, String image, double healthDecreaseReduction) {
        super(name, image);
        setHealthDecreaseReduction(healthDecreaseReduction);
    }

    @Override
    public void applyEffect(BasePlayer player) {
        player.setHealthDecreaseMultiplier(
                player.getHealthDecreaseMultiplier() * (1 - healthDecreaseReduction)
        );
    }

    @Override
    public void removeEffect(BasePlayer player) {
        player.setHealthDecreaseMultiplier(
                player.getHealthDecreaseMultiplier() / (1 - healthDecreaseReduction)
        );
    }

    @Override
    public int getSlotIndex() {
        return 2;
    }

    public double getHealthDecreaseReduction() {
        return healthDecreaseReduction;
    }

    public void setHealthDecreaseReduction(double value) {
        healthDecreaseReduction = value;
    }
}
