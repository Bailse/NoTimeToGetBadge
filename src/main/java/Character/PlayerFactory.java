
package Character;

public class PlayerFactory {

    public static Player create(CharacterType type){

        Player p;

        switch(type){

            case HUSTLER:
                p = new Player(type,120,300,0,100,0.8,1.0,2,1);
                p.setImagePath("/Annie_Zheng.jpg");
                break;

            case GENIUS:
                p = new Player(type,100,200,20,100,1.0,1.25,2,1);
                p.setImagePath("/Huh.jpg");
                break;

            case ATHLETE:
                p = new Player(type,140,100,0,130,1.0,1.0,1,1);
                p.setImagePath("/Lily.jpg");
                break;

            case SOCIALITE:
                p = new Player(type,100,500,0,100,1.0,1.0,2,1);
                p.setImagePath("/deku_nerd.jpg");
                break;

            default:
                p = new Player(type,100,500,0,100,1.0,1.0,2,1);
                p.setImagePath("/player.png");
        }

        return p;
    }
}
