package Logic;

import Character.Player;

public class GameSession {

    private static Player player;

    // ===== Round system =====
    private static final int MAX_ROUNDS = 10;
    private static int round = 1; // starts at 1/10

    // ===== Player baseline =====
    private static int initialStamina = 0;

    public static void setPlayer(Player p){
        player = p;
        // reset session state when choosing a new player
        round = 1;
        initialStamina = (p != null) ? p.getStamina() : 0;
    }

    public static Player getPlayer(){
        return player;
    }

    public static int getRound(){
        return round;
    }

    public static int getMaxRounds(){
        return MAX_ROUNDS;
    }

    public static int getInitialStamina(){
        return initialStamina;
    }

    /** Increase round by 1 (but never exceed MAX_ROUNDS). */
    public static void advanceRound(){
        if(round < MAX_ROUNDS){
            round++;
        }
    }

    /** True if the game has reached the last round (10/10). */
    public static boolean isLastRound(){
        return round >= MAX_ROUNDS;
    }
}
