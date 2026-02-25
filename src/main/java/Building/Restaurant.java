package Building;
import Character.BasePlayer;

public class Restaurant extends BaseBuilding {

    public Restaurant() {
        super("Restaurant");
    }

    @Override
    public void interact(BasePlayer character) {

        character.setHealth(character.getHealth()+10);
    }
}

