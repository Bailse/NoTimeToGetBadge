package Building;

import java.util.Random;
import Character.BasePlayer;

public class Dome extends BaseBuilding {

    public Dome() {
        super("Dome");
    }

    @Override
    public void interact(BasePlayer character) {

        Random rand = new Random();
        int event = rand.nextInt(3);

        if (event == 0) {
            character.setEducation(character.getEducation()+15);
        } else if (event == 1) {
            character.setHealth(character.getHealth()+15);
        } else {
            character.setMoney(character.getMoney()-50);
        }
    }
}