package me.krob.spacegame.drawable.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import me.krob.spacegame.util.Direction;
import me.krob.spacegame.R;
import me.krob.spacegame.view.SpaceGameView;
import me.krob.spacegame.drawable.Drawable;

public class Joypad extends Drawable {
    private final SpaceGameView view;

    private final float maxX, maxY, minX, minY;

    public Joypad(SpaceGameView view, Context context) {
        super(view.getScreenX() / 4.5f, view.getScreenX() / 4.5f);
        this.view = view;

        minX = view.getScreenX() - (getHeight() + (view.getScreenX() * 0.025f));
        minY = view.getScreenY() - (getHeight() + (view.getScreenY() * 0.075f));

        maxX = minX + getHeight();
        maxY = minX + getHeight();

        createBitmap(context);
    }

    public void createBitmap(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.joystick);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) getHeight(), (int) getHeight(), false);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setAlpha(40);
        canvas.drawBitmap(bitmap, minX, minY, paint);
        paint.setAlpha(100);
    }

    public void update(long framesPerSecond) {
        // Do nothing
    }

    public void handleMovement(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                view.setPaused(false);

                float x = event.getX();
                float y = event.getY();

                // TODO: Use the rect?
                if (x > minX && x < maxX) {
                    if (y > minY && y < maxY) {
                        float diffX = (maxX - minX) / 3f;

                        Spaceship spaceship = view.getSpaceship();

                        if (x > (minX+diffX) && x < (maxX-diffX)) {
                            if (y < minY+diffX) {
                                spaceship.setDirection(Direction.UP);
                                spaceship.shoot();
                            } else if (y > minY+(diffX*2) && y < minY+(diffX*3)) {
                                spaceship.setDirection(Direction.DOWN);
                                spaceship.shoot();
                            }
                        }

                        if (x < minX + (diffX*3) && y > minY+diffX && y < minY+(diffX*2)) {
                            if (x < minX+diffX) {
                                spaceship.setDirection(Direction.LEFT);
                                spaceship.shoot();
                            } else if (x > minX+(diffX*2)) {
                                spaceship.setDirection(Direction.RIGHT);
                                spaceship.shoot();
                            }
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                view.getSpaceship().setDirection(Direction.NONE);
                break;
        }
    }
}
