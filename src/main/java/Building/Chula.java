package Building;
import Character.BasePlayer;

public class Chula extends BaseBuilding {

    public Chula() {
        super("Chula");
    }

    @Override
    public void interact(BasePlayer character) {

        character.setEducation(character.getEducation()+20);
        character.setHealth(character.getHealth()-5);
    }
}
