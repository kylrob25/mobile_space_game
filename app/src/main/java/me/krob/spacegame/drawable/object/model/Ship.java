package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import me.krob.spacegame.MainActivity;
import me.krob.spacegame.handler.GameObjectHandler;
import me.krob.spacegame.util.Direction;
import me.krob.spacegame.R;
import me.krob.spacegame.view.SpaceGameView;
import me.krob.spacegame.drawable.object.GameObject;

public class Ship extends GameObject {
    private static final double MOVEMENT_CORRECTOR = MainActivity.MOVEMENT_CORRECTOR;
    private static final int MOVEMENT_SPEED = 350;
    private static final long BULLET_DELAY = 750;

    private final SpaceGameView view;

    private Bitmap bitmapUp;
    private Bitmap bitmapLeft;
    private Bitmap bitmapRight;
    private Bitmap bitmapDown;

    public Direction direction = Direction.NONE;
    private int movementSpeed = MOVEMENT_SPEED;

    private long bulletDelay = BULLET_DELAY;
    private long lastBulletTime;

    public Ship(SpaceGameView view){
        super(view.getScreenY() / 10f, view.getScreenY() / 10f);
        this.view = view;

        locX = view.getScreenX() / 2f;
        locY = view.getScreenY() / 2f;

        createBitmap(view.getContext());
    }

    /**
     * Creating our bitmap images
     * @param context
     */
    public void createBitmap(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) width, (int) height, false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        bitmapUp = Bitmap.createScaledBitmap(decoded, (int) (width), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        bitmapRight = Bitmap.createScaledBitmap(decoded, (int) (width), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipleft);
        bitmapLeft = Bitmap.createScaledBitmap(decoded, (int) (width), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipdown);
        bitmapDown = Bitmap.createScaledBitmap(decoded, (int) (width), (int) (height),false);
    }

    /**
     * Draw the object
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255,  255, 255, 255));
        canvas.drawBitmap(bitmap, locX, locY , paint);
    }

    public void handleCollisions() {
        GameObjectHandler objectHandler = view.getObjectHandler();

        if (intersects(objectHandler.getJoypad())) {
            teleportToCenter();
        }
    }

    /**
     * Updating the location
     * @param framesPerSecond
     */
    public void update(long framesPerSecond) {
        handleCollisions();

        long movement = movementSpeed / framesPerSecond;

        switch (direction) {
            case LEFT:
                locX -= movement;
                setBitmap(bitmapLeft);

                shoot(locX, locY + height / 2f, direction);

                if (locX <= 0) {
                    locX = view.getScreenX() - width;
                }
                break;
            case UP_LEFT:
                locX -= movement * MOVEMENT_CORRECTOR;
                locY -= movement * MOVEMENT_CORRECTOR;

                setBitmap(bitmapLeft);

                shoot(locX, locY + height / 2f, direction);

                if (locX <= 0) {
                    locX = view.getScreenX() - width;
                } else if (locY + height <= 0) {
                    locY = view.getScreenY();
                }
                break;
            case DOWN_LEFT:
                locX -= movement * MOVEMENT_CORRECTOR;
                locY += movement * MOVEMENT_CORRECTOR;

                setBitmap(bitmapLeft);

                shoot(locX, locY + height / 2f, direction);

                if (locX <= 0) {
                    locX = view.getScreenX() - width;
                } else if (locY >= view.getScreenY()) {
                    locY = 0 - height;
                }
                break;
            case RIGHT:
                locX += movement;

                setBitmap(bitmapRight);

                shoot(locX + width, locY + height / 2f, direction);

                if ((locX + width) >= view.getScreenX()) {
                    locX = 0;
                }
                break;
            case DOWN_RIGHT:
                locX += movement * MOVEMENT_CORRECTOR;
                locY += movement * MOVEMENT_CORRECTOR;

                setBitmap(bitmapRight);

                shoot(locX + width, locY + height / 2f, direction);

                if ((locX + width) >= view.getScreenX()) {
                    locX = 0;
                } else if (locY >= view.getScreenY()) {
                    locY = 0 - height;
                }
                break;
            case UP_RIGHT:
                locX += movement * MOVEMENT_CORRECTOR;
                locY -= movement * MOVEMENT_CORRECTOR;

                setBitmap(bitmapRight);

                shoot(locX + width, locY + height / 2f, direction);

                if ((locX + width) >= view.getScreenX()) {
                    locX = 0;
                } else if (locY + height <= 0) {
                    locY = view.getScreenY();
                }
                break;
            case UP:
                locY -= movement;

                setBitmap(bitmapUp);

                shoot(locX + width / 2f, locY, direction);

                if (locY + height <= 0) {
                    locY = view.getScreenY();
                }
                break;
            case DOWN:
                locY += movement;
                setBitmap(bitmapDown);

                shoot(locX + width / 2f, locY + height, direction);

                if (locY >= view.getScreenY()) {
                    locY = 0 - height;
                }
                break;
        }

        updateRect();
    }

    public void shoot(float startX, float startY, Direction direction) {
        long now = System.currentTimeMillis();

        if (now - lastBulletTime > bulletDelay) {
            Bullet bullet = new Bullet(view);

            view.getObjectHandler().addBullet(bullet);
            bullet.shoot(startX, startY, direction);

            lastBulletTime = now;
        }
    }

    private void teleportToCenter() {
        locX = view.getScreenX() / 2f;
        locY = view.getScreenY() / 2f;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
