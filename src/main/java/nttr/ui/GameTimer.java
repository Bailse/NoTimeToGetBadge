package nttr.ui;

import javafx.application.Platform;
import nttr.model.GameState;

/**
 * Background thread that ticks every second.
 * Requirement: UI updates must use Platform.runLater.
 */
public class GameTimer extends Thread {
    private final GameState gameState;
    private final Runnable afterTickUi;

    private volatile boolean stopRequested;

    public GameTimer(GameState gameState, Runnable afterTickUi) {
        this.gameState = gameState;
        this.afterTickUi = afterTickUi;
        setDaemon(true);
        setName("GameTimerThread");
    }

    public void requestStop() {
        stopRequested = true;
        interrupt();
    }

    @Override
    public void run() {
        while (!stopRequested) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                // allow loop to exit if stopRequested
            }

            if (stopRequested) {
                break;
            }

            Platform.runLater(() -> {
                gameState.tickOneSecond();
                afterTickUi.run();
            });
        }
    }
}
