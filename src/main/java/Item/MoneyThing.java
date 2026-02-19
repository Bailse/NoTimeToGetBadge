package Item;

import Character.BasePlayer;

public class MoneyThing extends BaseItem {

    private double moneyMultiplier; // เช่น 0.90 = ซื้อถูกลง 10%

    public MoneyThing(String name, String image, double moneyMultiplier) {
        super(name, image);
        setMoneyMultiplier(moneyMultiplier);
    }

    @Override
    public void applyEffect(BasePlayer player) {
        player.setMoneyMultiplier(
                player.getMoneyMultiplier() * moneyMultiplier
        );
    }

    @Override
    public void removeEffect(BasePlayer player) {
        player.setMoneyMultiplier(
                player.getMoneyMultiplier() / moneyMultiplier
        );
    }

    @Override
    public int getSlotIndex() {
        return 3;
    }

    public double getMoneyMultiplier() {
        return moneyMultiplier;
    }

    public void setMoneyMultiplier(double value) {
        moneyMultiplier = value;
    }
}
