package me.krob.spacegame.drawable.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.krob.spacegame.util.Direction;
import me.krob.spacegame.view.SpaceGameView;
import me.krob.spacegame.drawable.Drawable;

public class Bullet extends Drawable {
    private static final int MOVEMENT_SPEED = 650;

    private final SpaceGameView view;

    private Direction direction = Direction.NONE;
    private boolean active;
    private float movementSpeed = MOVEMENT_SPEED;

    public Bullet(SpaceGameView view) {
        super(0f, 0f);
        this.view = view;
    }

    public void createBitmap(Context context) {

    }

    public void draw(Canvas canvas, Paint paint) {

    }

    public void update(long framesPerSecond) {

    }
}
