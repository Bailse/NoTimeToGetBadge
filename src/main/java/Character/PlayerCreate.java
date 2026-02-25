
package Character;

public class PlayerCreate {

    public static BasePlayer create(CharacterType type){

        switch(type){

            case GYMBRO:
                return new GymBro();

            case NERD:
                return new Nerd();

            case OTAKU:
                return new Otaku();

            case NORMAL:
                return new NormalGuy();

            default:
                return null;
        }

    }
}
