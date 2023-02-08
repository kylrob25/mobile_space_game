package me.krob.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Joypad {
    private final SpaceGameView view;
    private Bitmap bitmap;

    private final float size;

    public Joypad(SpaceGameView view, Context context) {
        this.view = view;

        size = view.getScreenX() / 4.8f;

        createBitmap(context);
    }

    private void createBitmap(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.joystick);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) size, (int) size, false);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, view.getScreenX() - (size + (size * 0.1f)), view.getScreenY() - (size + (size * 0.1f)), paint);
    }

    public void handleMovement(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                view.setPaused(false);

                float xDiff = view.getScreenX() - event.getX();
                float yDiff = view.getScreenY() - event.getY();

                // TODO: Figure out the math to support any screen size
                if (xDiff > 165 && xDiff < 330) {
                    if (yDiff > 400 && yDiff < 600) {
                        view.getSpaceship().setMoveState(MoveState.UP);
                    } else if (yDiff > 100 && yDiff < 290) {
                        view.getSpaceship().setMoveState(MoveState.DOWN);
                    }
                } else if (xDiff > 300 && xDiff < 500) {
                    if (yDiff > 260 && yDiff < 430) {
                        view.getSpaceship().setMoveState(MoveState.LEFT);
                    }
                } else if (xDiff > 0 && xDiff < 350) {
                    if (yDiff > 260 && yDiff < 430) {
                        view.getSpaceship().setMoveState(MoveState.RIGHT);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                view.getSpaceship().setMoveState(MoveState.NONE);
                break;
        }
    }
}
