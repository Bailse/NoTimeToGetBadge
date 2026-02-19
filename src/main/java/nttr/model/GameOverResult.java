package nttr.model;

public class GameOverResult {
    private final boolean win;
    private final int score;

    public GameOverResult(boolean win, int score) {
        this.win = win;
        this.score = score;
    }

    public boolean isWin() {
        return win;
    }

    public int getScore() {
        return score;
    }
}
