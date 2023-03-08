package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import me.krob.spacegame.drawable.Drawable;
import me.krob.spacegame.util.Direction;
import me.krob.spacegame.R;
import me.krob.spacegame.view.SpaceGameView;

public class Joypad extends Drawable {
    private final SpaceGameView view;

    private final float minX, minY;
    private final RectF up, down, left, right;

    public Joypad(SpaceGameView view) {
        super(view.getScreenX() / 5f, view.getScreenX() / 5f);
        this.view = view;

        minX = view.getScreenX() - (getHeight() + (view.getScreenX() * 0.025f));
        minY = view.getScreenY() - (getHeight() + (view.getScreenY() * 0.075f));

        float maxX = minX + getHeight();
        float maxY = minY + getHeight();

        updateRect(minX, minY, maxX, maxY);

        float diff = (maxX - minX) / 3f;

        up = new RectF(minX + diff, minY, minX + (diff * 2f), minY + diff);
        down = new RectF(minX + diff, minY + (diff * 2f), minX + (diff * 2f), maxY);
        left = new RectF(minX, minY + diff, minX + diff, minY + (diff * 2f));
        right = new RectF(minX + (diff * 2f), minY + diff, maxX, minY + (diff * 2f));

        createBitmap(view.getContext());
    }

    public void createBitmap(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.joystick);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) getHeight(), (int) getHeight(), false);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, minX, minY, null);
    }

    public void onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                view.setPaused(false);

                float x = event.getX();
                float y = event.getY();

                if (rect.contains(x, y)) {
                    Defender defender = view.getObjectHandler().getDefender();
                    if (up.contains(x, y)) {
                        defender.setDirection(Direction.UP);
                    } else if (down.contains(x, y)) {
                        defender.setDirection(Direction.DOWN);
                    } else if (right.contains(x, y)) {
                        defender.setDirection(Direction.RIGHT);
                    } else if (left.contains(x, y)) {
                        defender.setDirection(Direction.LEFT);
                    }
                }

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float x = event.getX();
                float y = event.getY();

                if (rect.contains(x, y)) {
                    float diffX = x - rect.centerX();
                    float diffY = y - rect.centerY();

                    if (diffY == 0 && diffX == 0) {
                        return;
                    }

                    Defender defender = view.getObjectHandler().getDefender();
                    float angle = getAngle(diffX, diffY);
                    defender.setDirection(Direction.fromAngle(angle));
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                Defender defender = view.getObjectHandler().getDefender();
                defender.setDirection(Direction.NONE);
                break;
        }
    }

    public float getAngle(float diffX, float diffY) {
        return (float) ((Math.toDegrees(Math.atan2(diffX, diffY))) + 360) % 360;
    }
}
