package Building;

import Character.BasePlayer;

public class Gym extends BaseBuilding{

        public Gym() {
            super("Gym");
        }

        @Override
        public void interact(BasePlayer character) {
            character.setHealth(character.getHealth()+25);
        }

}
