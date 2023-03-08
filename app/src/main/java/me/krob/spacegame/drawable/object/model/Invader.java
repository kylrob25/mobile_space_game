package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.krob.spacegame.R;
import me.krob.spacegame.drawable.object.GameObject;
import me.krob.spacegame.drawable.object.GameObjectType;
import me.krob.spacegame.util.Direction;
import me.krob.spacegame.view.SpaceGameView;

public class Invader extends GameObject {
    private static final int MOVEMENT_SPEED = 650;
    private static final long BULLET_DELAY = 1000;

    private final SpaceGameView view;

    private Direction direction = Direction.RIGHT;

    private int movementSpeed = MOVEMENT_SPEED;
    private long bulletDelay = BULLET_DELAY;

    private long lastBulletTime;

    public Invader(SpaceGameView view) {
        super(GameObjectType.INVADER, view.getBorderY() * 0.6f, view.getBorderY() * 0.5f);
        this.view = view;

        locX = view.getScreenX() / 2f;
        locY = view.getBorderY() / 2f;

        createBitmap(view.getContext());
    }

    public void createBitmap(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.invader1);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) width, (int) height, false);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, locX, locY, null);
    }

    public void update(long framesPerSecond) {
        long movement = movementSpeed / framesPerSecond;

        Defender defender = view.getObjectHandler().getDefender();

        Direction bulletDirection;
        if (Math.abs(defender.getLocX() - locX) < 50) {
            bulletDirection = Direction.DOWN;
        } else if (defender.getLocX() >= locX) {
            bulletDirection = Direction.DOWN_RIGHT;
        } else {
            bulletDirection = Direction.DOWN_LEFT;
        }

        switch (direction) {
            case RIGHT:
                shoot(locX + width, locY + height / 2f, bulletDirection);
                if (locX + width >= view.getScreenX()) {
                    direction = Direction.LEFT;
                    return;
                }
                locX += movement;
                break;
            case LEFT:
                shoot(locX, locY + height / 2f, bulletDirection);
                if (locX <= 0) {
                    direction = Direction.RIGHT;
                    return;
                }
                locX -= movement;
                break;
        }

        updateRect();
    }

    public void shoot(float startX, float startY, Direction direction) {
        long now = System.currentTimeMillis();

        if (now - lastBulletTime > bulletDelay) {
            Bullet bullet = new Bullet(this, view);
            bullet.setMovementSpeed(300);

            view.getObjectHandler().addBullet(bullet);
            bullet.shoot(startX, startY, direction);

            lastBulletTime = now;
        }
    }

    public void handleCollisions() {

    }
}
