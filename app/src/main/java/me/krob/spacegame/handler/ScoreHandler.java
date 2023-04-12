package me.krob.spacegame.handler;

public class ScoreHandler {
    private long health = 100;
    private long score = 10;
    private long lastDamage;

    public void saveData() {
        // TODO: Save to a file?
    }

    public void incrementHealth(int amount) {
        if (health < 100) {
            health += amount;
        }
    }

    public void decrementHealth(int amount) {
        health -= amount;
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
