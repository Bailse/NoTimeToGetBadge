
package Character;

/**
 * this class use to initialize player to connect with gamePane in package logic to create character
 */
public class PlayerCreate {

    /**
     * create character by using ENUM(characterType) in choosing screen
     */

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
