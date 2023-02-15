package me.krob.spacegame.drawable.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.krob.spacegame.util.Direction;
import me.krob.spacegame.view.SpaceGameView;
import me.krob.spacegame.drawable.Drawable;

public class Bullet extends Drawable {
    private static final int MOVEMENT_SPEED = 650;

    private final SpaceGameView view;

    private Direction direction = Direction.NONE;
    private boolean active;
    private int movementSpeed = MOVEMENT_SPEED;

    public Bullet(SpaceGameView view, Context context) {
        super(0f, 0f);
        this.view = view;

        createBitmap(context);
    }

    public boolean shoot(float startX, float startY, Direction direction) {
        if (active) {
            return false;
        }

        locX = startX;
        locY = startY;

        active = true;

        updateDirection(direction);

        return true;
    }

    private void updateDirection(Direction direction) {
        this.direction = direction;

        if (direction.isHorizontal()) {
            width = view.getScreenX() / 20f;
            height = 1f;
        } else {
            height = view.getScreenY() / 20f;
            width = 1f;
        }
    }

    public void createBitmap(Context context) {

    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(rect, paint);
    }

    public void update(long framesPerSecond) {
        long movement = movementSpeed / framesPerSecond;

        switch (direction) {
            case UP:
                locY -= movement;
                break;
            case DOWN:
                locY += movement;
                break;
            case RIGHT:
                locX += movement;
                break;
            case LEFT:
                locX -= movement;
                break;
        }

        updateRect();
    }

    public float getImpactY() {
        if (direction == Direction.DOWN) {
            return locY + height;
        }
        return locY;
    }

    public float getImpactX() {
        return locX;
    }

    public boolean isActive() {
        return active;
    }
}
