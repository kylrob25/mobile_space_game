package me.krob.spacegame.handler;

import me.krob.spacegame.view.SpaceGameView;

public class ScoreHandler {
    private final SpaceGameView view;

    private long health = 10;
    private long score = 10;

    private long lastDamage;

    public ScoreHandler(SpaceGameView view) {
        this.view = view;
    }

    public void saveData() {
        // TODO: Save to a file?
    }

    public void restart() {
        health = 100;
        score = 10;
    }

    public void incrementHealth(int amount) {
        if (health < 100) {
            health += amount;
        }
    }

    public void decrementHealth(int amount) {
        health -= amount;
        if (health <= 0) {
            view.restartGame();
        }
        lastDamage = System.currentTimeMillis();
    }

    public void incrementScore(int amount) {
        score += amount;
    }

    public void decrementScore(int amount) {
        score -= amount;
    }

    public long getHealth() {
        return health;
    }

    public long getScore() {
        return score;
    }

    public long getLastDamage() {
        return lastDamage;
    }
}
