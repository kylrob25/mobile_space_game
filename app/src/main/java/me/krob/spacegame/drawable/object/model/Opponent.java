package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.krob.spacegame.drawable.object.GameObject;
import me.krob.spacegame.view.SpaceGameView;

public class Opponent extends GameObject {
    public Opponent(SpaceGameView view) {
        super(view.getBorderY() / 12f, view.getBorderY() / 12f);
    }

    public void createBitmap(Context context) {

    }

    public void draw(Canvas canvas, Paint paint) {

    }

    public void update(long framesPerSecond) {

    }

    public void handleCollisions() {

    }
}
