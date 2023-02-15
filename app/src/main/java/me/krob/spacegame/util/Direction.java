package me.krob.spacegame.util;

public enum Direction {
    NONE, UP, DOWN, LEFT, RIGHT;

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }
}
