package Logic;

import Character.BasePlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionTest {

    static class TestPlayer extends BasePlayer {
        public TestPlayer(int stamina) {
            super(stamina, 0, 0, 0, 1.0, 1.0, 0, 0, 0);
        }
    }

    @Test
    void setBasePlayerResetsRoundAndInitialStamina() {
        BasePlayer p = new TestPlayer(123);

        GameSession.setBasePlayer(p);

        assertSame(p, GameSession.getPlayer());
        assertEquals(1, GameSession.getRound());
        assertEquals(123, GameSession.getInitialStamina());
        assertEquals(10, GameSession.getMaxRounds());
        assertFalse(GameSession.isLastRound());
    }

    @Test
    void advanceRoundStopsAtMaxRounds() {
        GameSession.setBasePlayer(new TestPlayer(1));

        for (int i = 0; i < 100; i++) {
            GameSession.advanceRound();
        }

        assertEquals(GameSession.getMaxRounds(), GameSession.getRound());
        assertTrue(GameSession.isLastRound());

        // advancing further stays at max
        GameSession.advanceRound();
        assertEquals(GameSession.getMaxRounds(), GameSession.getRound());
    }

    @Test
    void setBasePlayerWithNullIsSafe() {
        GameSession.setBasePlayer(null);
        assertNull(GameSession.getPlayer());
        assertEquals(1, GameSession.getRound());
        assertEquals(0, GameSession.getInitialStamina());
    }
}
