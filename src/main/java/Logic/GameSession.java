package Logic;

import Character.BasePlayer;

/**
 * this class connect between PlayerCreate and GamePane
 */

public class GameSession {

    private static BasePlayer player;

    // ===== Round system =====
    private static final int MAX_ROUNDS = 10;
    private static int round = 1; // starts at 1/10

    // ===== Player baseline =====
    private static int initialStamina = 0;

    /**
     * set BasePlayer and set round of this game
     */

    public static void setBasePlayer(BasePlayer p){
        player = p;
        round = 1;
        initialStamina = (p != null) ? p.getStamina() : 0;
    }
    /**
     * get BasePlayer
     */
    public static BasePlayer getPlayer(){
        return player;
    }

    /**
     * get Round
     */

    public static int getRound(){
        return round;
    }
    /**
     * get getMaxRound
     */
    public static int getMaxRounds(){
        return MAX_ROUNDS;
    }
    /**
     * get initialStamina
     */
    public static int getInitialStamina(){
        return initialStamina;
    }

    /**
     * check condition round
     */

    public static void advanceRound(){
        if(round < MAX_ROUNDS){
            round++;
        }
    }

    /**
     * check last round
     */

    public static boolean isLastRound(){
        return round >= MAX_ROUNDS;
    }
}
