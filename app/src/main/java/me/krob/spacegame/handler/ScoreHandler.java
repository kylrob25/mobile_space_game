package me.krob.spacegame.handler;

import me.krob.spacegame.view.SpaceGameView;

public class ScoreHandler {
    private static final long HEALTH = 100L, SCORE = 10L;
    private final SpaceGameView view;

    private long health = 10;
    private long score = SCORE;

    public ScoreHandler(SpaceGameView view) {
        this.view = view;
    }

    public void saveData() {
        // TODO: Save to a file?
    }

    /**
     * Restarting the scores
     */
    public void restart() {
        health = HEALTH;
        score = SCORE;
    }

    /**
     * Incrementing the health value
     * @param amount - to be added
     */
    public void incrementHealth(int amount) {
        if (health < 100) {
            health += amount;
        }
    }

    /**
     * Decrementing the health value
     * @param amount - to be removed
     */
    public void decrementHealth(int amount) {
        health -= amount;
        if (health <= 0) {
            view.restartGame();
        }
    }

    /**
     * Incrementing the score
     * @param amount - to be added
     */
    public void incrementScore(int amount) {
        score += amount;
    }

    /**
     * Decrementing the score
     * @param amount - to be removed
     */
    public void decrementScore(int amount) {
        score -= amount;
    }

    public long getHealth() {
        return health;
    }

    public long getScore() {
        return score;
    }
}
