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
        // Do nothing
    }

    public void draw(Canvas canvas) {
        long health = view.getScoreHandler().getHealth();

        // Ensuring the health is above 0
        if (health > 0) {
            // Updating the rect
            updateRect(minX, 25, minX + (health * scale), 75);

            // If below half health, we make the status bar red.
            if (health < 50) {
                canvas.drawRect(rect, damagePaint);
                return;
            }

            // Drawing the status bar
            canvas.drawRect(rect, paint);
        }
    }
}
