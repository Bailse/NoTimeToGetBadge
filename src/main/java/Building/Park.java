package Building;

import Character.BasePlayer;

public class Park extends BaseBuilding {

    public Park() {
        super("Park");
    }

    @Override
    public void interact(BasePlayer character) {

        character.setHealth(character.getHealth()+10);
        character.setStamina(character.getStamina() + 20);
    }
}
