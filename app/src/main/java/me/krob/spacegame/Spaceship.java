package me.krob.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

    private final int screenX, screenY;

    public MoveState moveState = MoveState.NONE;
    private int movementSpeed = MOVEMENT_SPEED;

    public Spaceship(SpaceGameView view, Context context, int screenX, int screenY){
        this.view = view;

        rect = new RectF();

        length = screenX / 10f;
        height = screenY / 10f;

        locX = screenX / 2f;
        locY = screenY / 2f;

        createBitmaps(context);

        this.screenX = screenX;
        this.screenY = screenY;
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

                if ((locX +length)<=0) {
                    locX = screenX;
                }
                break;
            case RIGHT:
                locX += movement;
                bitmap = bitmapRight;

                if (locX >=screenX) {
                    locX = 0 - length;
                }
                break;
            case UP:
                locY -= movement;
                bitmap = bitmapUp;

                if (locY +height <=0) {
                    locY = screenY;
                }
                break;
            case DOWN:
                locY += movement;
                bitmap = bitmapDown;

                if (locY >=screenY) {
                    locY = 0 - height;
                }
                break;
        }

        updateRect();
    }

    /**
     * Handle the movement
     * @param event
     */
    public void handleMovement(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                view.setPaused(false);

                if(event.getY() > screenY - (screenY / 2.0)) {
                    if (event.getX() > screenX / 2.0) {
                        moveState = MoveState.RIGHT;
                    } else {
                        moveState = MoveState.LEFT;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (event.getY() > screenY - (screenY / 2.0)) {
                    moveState = MoveState.NONE;
                }
                break;
        }
    }

    /**
     * Handle the collision
     */
    private void handleCollisions() {
        if (locX > screenX - length) {
            locX = 0;
        }

        if (locX < 0 - length) {
            locX = screenX;
        }

        if (locY > screenY - length) {
            locY = 0;
        }

        if (locY < 0 - length) {
            locY = screenY;
        }
    }

    /**
     * Updating the rect
     */
    private void updateRect() {
        rect.set(locX, locY, locX + length, locY + height);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public float getLocX(){
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public float getLocY(){
        return locY;
    }

    public void setLocY(int locY){
            this.locY = locY;
    }

    public float getLength(){
        return length;
    }

    public enum MoveState {
        NONE, UP, DOWN, LEFT, RIGHT
    }
}
