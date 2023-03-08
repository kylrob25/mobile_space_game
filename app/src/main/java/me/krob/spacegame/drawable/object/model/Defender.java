package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.krob.spacegame.MainActivity;
import me.krob.spacegame.drawable.object.GameObjectType;
import me.krob.spacegame.handler.GameObjectHandler;
import me.krob.spacegame.util.Direction;
import me.krob.spacegame.R;
import me.krob.spacegame.view.SpaceGameView;
import me.krob.spacegame.drawable.object.GameObject;

public class Defender extends GameObject {
    private static final float MOVEMENT_CORRECTOR = MainActivity.MOVEMENT_CORRECTOR;
    private static final int MOVEMENT_SPEED = 350;
    private static final long BULLET_DELAY = 450;

    private final SpaceGameView view;

    public Direction direction = Direction.NONE;
    private int movementSpeed = MOVEMENT_SPEED;

    private long bulletDelay = BULLET_DELAY;
    private long lastBulletTime;

    public Defender(SpaceGameView view){
        super(GameObjectType.DEFENDER, view.getBorderY() * 0.8f, view.getBorderY() * 0.8f);
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
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.defender);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) width, (int) height, false);
    }

    /**
     * Draw the object
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, locX, locY, null);
        //canvas.drawRect(rect, paint);
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
                if (locX > 0) {
                    locX -= movement;
                }

                shoot(locX, locY + height / 2f, Direction.UP);
                break;
            case UP_LEFT:
                if (locX > 0) {
                    locX -= movement * MOVEMENT_CORRECTOR;
                }

                if (locY > view.getBorderY()) {
                    locY -= movement * MOVEMENT_CORRECTOR;
                }

                shoot(locX, locY + height / 2f, Direction.UP);
                break;
            case DOWN_LEFT:
                if (locX > 0) {
                    locX -= movement * MOVEMENT_CORRECTOR;
                }

                if (locY + height < view.getBorderY1()) {
                    locY += movement * MOVEMENT_CORRECTOR;
                }

                shoot(locX, locY + height / 2f, Direction.UP);
                break;
            case RIGHT:
                if (locX + width < view.getScreenX()) {
                    locX += movement;
                }

                shoot(locX + width, locY + height / 2f, Direction.UP);
                break;
            case DOWN_RIGHT:
                if (locX + width < view.getScreenX()) {
                    locX += movement * MOVEMENT_CORRECTOR;
                }

                if (locY + height < view.getBorderY1()) {
                    locY += movement * MOVEMENT_CORRECTOR;
                }

                shoot(locX + width, locY + height / 2f, Direction.UP);
                break;
            case UP_RIGHT:
                if (locX + width < view.getScreenX()) {
                    locX += movement * MOVEMENT_CORRECTOR;
                }

                if (locY > view.getBorderY()) {
                    locY -= movement * MOVEMENT_CORRECTOR;
                }

                shoot(locX + width, locY + height / 2f, Direction.UP);
                break;
            case UP:
                if (locY > view.getBorderY()) {
                    locY -= movement;
                }

                shoot(locX + width / 2f, locY, Direction.UP);
                break;
            case DOWN:
                if (locY + height < view.getBorderY1()) {
                    locY += movement;
                }

                shoot(locX + width / 2f, locY + height, Direction.UP);
                break;
        }

        updateRect();
    }

    public void shoot(float startX, float startY, Direction direction) {
        long now = System.currentTimeMillis();

        if (now - lastBulletTime > bulletDelay) {
            Bullet bullet = new Bullet(this, view);

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
