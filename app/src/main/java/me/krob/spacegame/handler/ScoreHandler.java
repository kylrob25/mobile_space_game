package me.krob.spacegame.handler;

public class ScoreHandler {
    private static final int LIVES = 4;
    private static final int SCORE = 10;

    private int lives = LIVES;
    private int score = SCORE;

    public void saveData() {
        // TODO: Save to a file?
    }

    public void incrementLives(int amount) {
        lives += amount;
    }

    public void decrementLives(int amount) {
        lives -= amount;
    }

    public void incrementScore(int amount) {
        score += amount;
    }

    public void decrementScore(int amount) {
        score -= amount;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }
}
