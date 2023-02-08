package me.krob.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

public class Joypad {
    private final SpaceGameView view;
    private Bitmap bitmap;

    private final float size;
    private final float maxX, maxY, minX, minY;

    public Joypad(SpaceGameView view, Context context) {
        this.view = view;

        size = view.getScreenX() / 4.5f;

        minX = view.getScreenX() - (size + (view.getScreenX() * 0.025f));
        minY = view.getScreenY() - (size + (view.getScreenY() * 0.075f));

        maxX = minX + size;
        maxY = minX + size;

        createBitmap(context);
    }

    private void createBitmap(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.joystick);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) size, (int) size, false);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, minX, minY, paint);
    }

    public void handleMovement(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                view.setPaused(false);

                float x = event.getX();
                float y = event.getY();

                // TODO: Should work for all screen sizes but should probs tidy it up
                if (x > minX && x < maxX) {
                    if (y > minY && y < maxY) {
                        float diffX = (maxX - minX) / 3f;

                        if (x > (minX+diffX) && x < (maxX-diffX)) {
                            if (y < minY+diffX) {
                                view.getSpaceship().setMoveState(MoveState.UP);
                            } else if (y > minY+(diffX*2) && y < minY+(diffX*3)) {
                                view.getSpaceship().setMoveState(MoveState.DOWN);
                            }
                        }

                        if (x < minX + (diffX*3) && y > minY+diffX && y < minY+(diffX*2)) {
                            if (x < minX+diffX) {
                                view.getSpaceship().setMoveState(MoveState.LEFT);
                            } else if (x > minX+(diffX*2)) {
                                view.getSpaceship().setMoveState(MoveState.RIGHT);
                            }
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                view.getSpaceship().setMoveState(MoveState.NONE);
                break;
        }
    }
}
