package me.krob.spacegame.util;

public enum Direction {
    NONE,
    UP, DOWN,
    UP_RIGHT, DOWN_RIGHT,
    UP_LEFT, DOWN_LEFT,
    LEFT, RIGHT;

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }

    public static Direction fromAngle(float angle) {
        if (angle >= 292.5 && angle <= 337.5) {
            return DOWN_LEFT;
        } else if (angle >= 247.5 && angle <= 292.5) {
            return LEFT;
        } else if (angle >= 202.5 && angle <= 247.5) {
            return UP_LEFT;
        } else if (angle >= 157.5 && angle <= 202.5) {
            return UP;
        } else if (angle >= 112.5 && angle <= 157.5) {
            return UP_RIGHT;
        } else if (angle >= 67.5 && angle <= 112.5) {
            return RIGHT;
        } else if (angle >= 22.5 && angle <= 67.5) {
            return DOWN_RIGHT;
        } else if (angle >= 337.5 && angle <= 360 ||
                angle >= 0 && angle <= 22.5) {
            return DOWN;
        }
        return null;
    }
}
