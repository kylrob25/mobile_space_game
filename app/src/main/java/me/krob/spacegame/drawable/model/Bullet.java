package me.krob.spacegame.drawable.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.krob.spacegame.util.Direction;
import me.krob.spacegame.view.SpaceGameView;
import me.krob.spacegame.drawable.Drawable;

public class Bullet extends Drawable {
    private static final int MOVEMENT_SPEED = 1650;

    private static int BULLETS = 1;
    private final int id = BULLETS++;

    private final SpaceGameView view;

    private Direction direction = Direction.NONE;
    private boolean active;
    private int movementSpeed = MOVEMENT_SPEED;

    public Bullet(SpaceGameView view) {
        super(0f, 0f);
        this.view = view;

        createBitmap(view.getContext());
    }

    public void handleCollisions() {
        // TODO: Check for opponents
        if (getImpactY() < 0 ||
                getImpactY() > view.getScreenY() ||
                getImpactX() < 0 ||
                getImpactX() > view.getScreenX()) {
            active = false;
            view.removeBullet(this);
            //view.removeBullet(id);
        }
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

        float size = view.getScreenY() / 20f;

        if (direction.isHorizontal()) {
            width = size;
            height = 2f;
        } else {
            height = size;
            width = 2f;
        }
    }

    public void createBitmap(Context context) {
        // TODO: Switch to an image rather than drawing the rect
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

    public int getId() {
        return id;
    }
}