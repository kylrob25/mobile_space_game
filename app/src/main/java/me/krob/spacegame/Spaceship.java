package me.krob.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class Spaceship {
    private static final int MOVEMENT_SPEED = 350;

    private final SpaceGameView view;

    private final RectF rect;

    public Bitmap bitmap;
    private Bitmap bitmapUp;
    private Bitmap bitmapLeft;
    private Bitmap bitmapRight;
    private Bitmap bitmapDown;

    private final float height, length;
    private float locX, locY;

    public MoveState moveState = MoveState.NONE;
    private int movementSpeed = MOVEMENT_SPEED;

    public Spaceship(SpaceGameView view, Context context){
        this.view = view;

        rect = new RectF();

        float screenX = view.getScreenX();
        float screenY = view.getScreenY();

        length = screenX / 10f;
        height = screenY / 10f;

        locX = screenX / 2f;
        locY = screenY / 2f;

        createBitmaps(context);
    }

    /**
     * Creating our bitmap images
     * @param context
     */
    private void createBitmaps(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) length, (int) height, false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        bitmapUp = Bitmap.createScaledBitmap(decoded, (int) (length), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        bitmapRight = Bitmap.createScaledBitmap(decoded, (int) (length), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipleft);
        bitmapLeft = Bitmap.createScaledBitmap(decoded, (int) (length), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipdown);
        bitmapDown = Bitmap.createScaledBitmap(decoded, (int) (length), (int) (height),false);
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
     * @param fps
     */
    public void update(long fps){
        handleCollisions();

        long movement = movementSpeed / fps;

        switch (moveState) {
            case LEFT:
                locX -= movement;
                bitmap = bitmapLeft;

                if (locX <= 0) {
                    locX = view.getScreenX() - length;
                }
                break;
            case RIGHT:
                locX += movement;
                bitmap = bitmapRight;

                if ((locX - length) >= view.getScreenX() - 500) {
                    locX = 0;
                }
                break;
            case UP:
                locY -= movement;
                bitmap = bitmapUp;

                if (locY + height <= 0) {
                    locY = view.getScreenY();
                }
                break;
            case DOWN:
                locY += movement;
                bitmap = bitmapDown;

                if (locY >= view.getScreenY()) {
                    locY = 0 - height;
                }
                break;
        }

        updateRect();
    }

    /**
     * Handle the collision
     */
    private void handleCollisions() {

    }

    /**
     * Updating the rect
     */
    private void updateRect() {
        rect.set(locX - length, locY, locX + length, locY + height);
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public void setLocY(int locY){
        this.locY = locY;
    }

    public float getLength(){
        return length;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public float getLocX(){
        return locX;
    }

    public float getLocY(){
        return locY;
    }
}
