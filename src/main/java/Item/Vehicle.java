package Item;

import Character.BasePlayer;

public class Vehicle extends BaseItem {

    private double staminaReductionPercent; // เช่น 0.20 = ลด cost 20%

    public Vehicle(String name, String image, double staminaReductionPercent) {
        super(name, image);
        setStaminaReductionPercent(staminaReductionPercent);
    }

    @Override
    public void applyEffect(BasePlayer player) {
        player.setStaminaDecreaseMultiplier(
                player.getStaminaDecreaseMultiplier() * (1 - staminaReductionPercent)
        );
    }

    @Override
    public void removeEffect(BasePlayer player) {
        player.setStaminaDecreaseMultiplier(
                player.getStaminaDecreaseMultiplier() / (1 - staminaReductionPercent)
        );
    }

    @Override
    public int getSlotIndex() {
        return 0;
    }

    public double getStaminaReductionPercent() {
        return staminaReductionPercent;
    }

    public void setStaminaReductionPercent(double value) {
        staminaReductionPercent = value;
    }
}
