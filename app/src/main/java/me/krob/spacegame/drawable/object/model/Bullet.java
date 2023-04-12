package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import me.krob.spacegame.MainActivity;
import me.krob.spacegame.drawable.Drawable;
import me.krob.spacegame.drawable.object.GameObjectType;
import me.krob.spacegame.handler.GameObjectHandler;
import me.krob.spacegame.handler.ScoreHandler;
import me.krob.spacegame.util.Direction;
import me.krob.spacegame.view.SpaceGameView;
import me.krob.spacegame.drawable.object.GameObject;

public class Bullet extends GameObject {
    private static final float MOVEMENT_CORRECTOR = MainActivity.MOVEMENT_CORRECTOR;
    private static final int MOVEMENT_SPEED = 950;

    private static int BULLETS = 1;
    private final int id = BULLETS++;

    private final GameObject owner;
    private final SpaceGameView view;

    private final Paint paint;

    private Direction direction = Direction.UP;
    private boolean active;
    private int movementSpeed = MOVEMENT_SPEED;

    public Bullet(GameObject owner, SpaceGameView view) {
        super(GameObjectType.BULLET, 0f, 0f);
        this.owner = owner;
        this.view = view;

        paint = new Paint();

        switch (owner.getType()) {
            case DEFENDER:
                paint.setColor(Color.GREEN);
                break;
            case INVADER:
                paint.setColor(Color.RED);
                break;
        }

        createBitmap(view.getContext());
    }

    private void destroy() {
        active = false;
        view.getObjectHandler().removeBullet(this);
    }

    public void handleCollisions() {
        if (getImpactY() < 0 ||
                getImpactY() > view.getScreenY() ||
                getImpactX() < 0 ||
                getImpactX() > view.getScreenX()) {
            destroy();
            return;
        }

        GameObjectHandler objectHandler = view.getObjectHandler();
        ScoreHandler scoreHandler = view.getScoreHandler();

        // TODO: Animation/Sound?
        switch (owner.getType()) {
            case DEFENDER:
                Invader invader = objectHandler.getInvader();
                if (intersects(invader)) {
                    destroy();

                    scoreHandler.incrementScore(20);

                    invader.damage();
                    return;
                }

                objectHandler.getBulletsByOwner(GameObjectType.INVADER).forEach(bullet -> {
                    if (intersects(bullet)) {
                        destroy();
                        bullet.destroy();

                        scoreHandler.incrementScore(5);
                    }
                });
                break;
            case INVADER:
                if (intersects(objectHandler.getDefender())) {
                    destroy();

                    scoreHandler.decrementHealth(1);
                }
                break;
        }

    }

    public void shoot(float startX, float startY, Direction direction) {
        if (active) {
            return;
        }

        locX = startX;
        locY = startY;

        active = true;

        updateDirection(direction);
    }

    private void updateDirection(Direction direction) {
        this.direction = direction;

        switch (owner.getType()) {
            case INVADER:
                height = 25;
                width = 25;
                break;
            case DEFENDER:
                height = view.getScreenY() / 25f;
                width = 5f;
                break;
        }
    }

    public void createBitmap(Context context) {
        // TODO: Switch to an image rather than drawing the rect
    }

    public void draw(Canvas canvas) {
        switch (owner.getType()) {
            case INVADER:
                canvas.drawCircle(rect.centerX(), rect.centerY(), 25, paint);
                break;
            case DEFENDER:
                canvas.drawRect(rect, paint);
                break;
        }
        canvas.drawRect(rect, paint);
    }

    public void update(long framesPerSecond) {
        handleCollisions();

        long movement = movementSpeed / framesPerSecond;

        switch (direction) {
            case UP:
                locY -= movement;
                break;
            case UP_RIGHT:
                locY -= movement * MOVEMENT_CORRECTOR;
                locX += movement * MOVEMENT_CORRECTOR;
                break;
            case UP_LEFT:
                locY -= movement * MOVEMENT_CORRECTOR;
                locX -= movement * MOVEMENT_CORRECTOR;
                break;
            case DOWN:
                locY += movement;
                break;
            case DOWN_RIGHT:
                locY += movement * MOVEMENT_CORRECTOR;
                locX += movement * MOVEMENT_CORRECTOR;
                break;
            case DOWN_LEFT:
                locY += movement * MOVEMENT_CORRECTOR;
                locX -= movement * MOVEMENT_CORRECTOR;
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

    public void setMovementSpeed(int movementSpeed){
        this.movementSpeed = movementSpeed;
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

    public GameObject getOwner() {
        return owner;
    }
}
