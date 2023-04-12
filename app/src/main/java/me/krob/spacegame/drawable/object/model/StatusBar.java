package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import me.krob.spacegame.drawable.Drawable;
import me.krob.spacegame.view.SpaceGameView;

public class StatusBar extends Drawable {
    private final SpaceGameView view;

    private Paint paint, damagePaint;

    private float minX, maxX;

    public StatusBar(SpaceGameView view) {
        super(200, 300);
        this.view = view;

        paint = new Paint();
        paint.setColor(Color.GREEN);

        damagePaint = new Paint();
        damagePaint.setColor(Color.RED);

        minX = view.getScreenX() * 0.9f;
        maxX = view.getScreenX() * 0.98f;

        updateRect(minX, 25, maxX, 75);

        Log.i("HEALTH", String.valueOf(minX - maxX));
    }

    public void createBitmap(Context context) {

    }

    public void draw(Canvas canvas) {
        long health = view.getScoreHandler().getHealth();
        updateRect(minX, 25, maxX - ((100-health) * 5), 75);

        if (System.currentTimeMillis() - view.getScoreHandler().getLastDamage() < 500) {
            canvas.drawRect(rect, damagePaint);
            return;
        }

        canvas.drawRect(rect, paint);
    }
}
