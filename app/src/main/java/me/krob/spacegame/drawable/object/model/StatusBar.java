package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import me.krob.spacegame.drawable.Drawable;
import me.krob.spacegame.view.SpaceGameView;

public class StatusBar extends Drawable {
    private final SpaceGameView view;

    private final Paint paint, damagePaint;

    private final float minX, maxX, scale;

    public StatusBar(SpaceGameView view) {
        super(200, 300);
        this.view = view;

        paint = new Paint();
        paint.setColor(Color.GREEN);

        damagePaint = new Paint();
        damagePaint.setColor(Color.RED);

        minX = view.getScreenX() * 0.90f;
        maxX = view.getScreenX() * 0.98f;
        scale = (maxX - minX) / 100f;

        updateRect(minX, 25, maxX, 75);
    }

    public void createBitmap(Context context) {

    }

    public void draw(Canvas canvas) {
        long health = view.getScoreHandler().getHealth();

        if (health > 0) {
            updateRect(minX, 25, minX + (health * scale), 75);

            if (health < 50) {
                canvas.drawRect(rect, damagePaint);
                return;
            }

            canvas.drawRect(rect, paint);
        }

        /* Flashing bar when damaged
        if (System.currentTimeMillis() - view.getScoreHandler().getLastDamage() < 500) {
            canvas.drawRect(rect, damagePaint);
            return;
        }
         */
    }
}
