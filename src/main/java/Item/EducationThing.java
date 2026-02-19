package Item;

import Character.BasePlayer;

public class EducationThing extends BaseItem {

    private double educationMultiplier; // เช่น 1.20 = เรียนได้ +20%

    public EducationThing(String name, String image, double educationMultiplier) {
        super(name, image);
        setEducationMultiplier(educationMultiplier);
    }

    @Override
    public void applyEffect(BasePlayer player) {
        player.setEducationMultiplier(
                player.getEducationMultiplier() * educationMultiplier
        );
    }

    @Override
    public void removeEffect(BasePlayer player) {
        player.setEducationMultiplier(
                player.getEducationMultiplier() / educationMultiplier
        );
    }

    @Override
    public int getSlotIndex() {
        return 1;
    }

    public double getEducationMultiplier() {
        return educationMultiplier;
    }

    public void setEducationMultiplier(double value) {
        educationMultiplier = value;
    }
}
