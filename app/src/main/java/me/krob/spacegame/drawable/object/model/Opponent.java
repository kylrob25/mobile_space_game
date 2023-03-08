package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.krob.spacegame.R;
import me.krob.spacegame.drawable.object.GameObject;
import me.krob.spacegame.util.Direction;
import me.krob.spacegame.view.SpaceGameView;

public class Opponent extends GameObject {
    private static final int MOVEMENT_SPEED = 650;

    private final SpaceGameView view;

    private Direction direction = Direction.RIGHT;

    public Opponent(SpaceGameView view) {
        super(view.getBorderY() * 0.7f, view.getBorderY() * 0.7f);
        this.view = view;

        locX = view.getScreenX() / 2f;
        locY = (view.getBorderY() / 2f);

        createBitmap(view.getContext());
    }

    public void createBitmap(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.invader1);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) width, (int) height, false);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, locX, locY, null);
    }

    public void update(long framesPerSecond) {
        long movement = MOVEMENT_SPEED / framesPerSecond;

        switch (direction) {
            case RIGHT:
                if (locX + width >= view.getScreenX()) {
                    direction = Direction.LEFT;
                    return;
                }
                locX += movement;
                break;
            case LEFT:
                if (locX + width <= 0) {
                    direction = Direction.RIGHT;
                    return;
                }
                locX -= movement;
                break;
        }
    }

    public void handleCollisions() {

    }
}
