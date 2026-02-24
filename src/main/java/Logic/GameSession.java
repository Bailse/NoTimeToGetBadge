
package Logic;

import Character.Player;

public class GameSession {

    private static Player player;

    public static void setPlayer(Player p){
        player = p;
    }

    public static Player getPlayer(){
        return player;
    }
}
