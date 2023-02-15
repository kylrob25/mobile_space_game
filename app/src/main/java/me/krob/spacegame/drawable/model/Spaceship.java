package me.krob.spacegame.drawable.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import me.krob.spacegame.util.Direction;
import me.krob.spacegame.R;
import me.krob.spacegame.view.SpaceGameView;
import me.krob.spacegame.drawable.Drawable;

public class Spaceship extends Drawable {
    private static final int MOVEMENT_SPEED = 350;

    private final SpaceGameView view;

    private Bitmap bitmapUp;
    private Bitmap bitmapLeft;
    private Bitmap bitmapRight;
    private Bitmap bitmapDown;

    public Direction direction = Direction.NONE;
    private int movementSpeed = MOVEMENT_SPEED;

    private long lastBulletTime;

    public Spaceship(SpaceGameView view, Context context){
        super(view.getScreenY() / 10f, view.getScreenY() / 10f);
        this.view = view;

        locX = view.getScreenX() / 2f;
        locY = view.getScreenY() / 2f;

        createBitmap(context);
    }

    public void handleCollisions() {
        // TODO:
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

    /**
     * Updating the location
     * @param framesPerSecond
     */
    public void update(long framesPerSecond) {
        Bullet bullet = createBullet();

        long movement = movementSpeed / framesPerSecond;

        switch (direction) {
            case LEFT:
                locX -= movement;
                setBitmap(bitmapLeft);

                if(bullet != null) bullet.shoot(locX, locY + height / 2f, direction);

                if (locX <= 0) {
                    locX = view.getScreenX() - width;
                }
                break;
            case RIGHT:
                locX += movement;
                setBitmap(bitmapRight);

                if(bullet != null) bullet.shoot(locX + width, locY + height / 2f, direction);

                if ((locX - width) >= view.getScreenX() - 500) {
                    locX = 0;
                }
                break;
            case UP:
                locY -= movement;
                setBitmap(bitmapUp);

                if(bullet != null) bullet.shoot(locX + width / 2f, locY, direction);

                if (locY + height <= 0) {
                    locY = view.getScreenY();
                }
                break;
            case DOWN:
                locY += movement;
                setBitmap(bitmapDown);

                if(bullet != null) bullet.shoot(locX + width / 2f, locY + height, direction);

                if (locY >= view.getScreenY()) {
                    locY = 0 - height;
                }
                break;
        }

        updateRect();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Bullet createBullet() {
        long now = System.currentTimeMillis();

        if (now - lastBulletTime > 500 && direction != Direction.NONE) {
            Bullet bullet = new Bullet(view);
            view.registerBullet(bullet);
            lastBulletTime = now;
            return bullet;
        }
        return null;
    }
}
