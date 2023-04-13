package me.krob.spacegame.drawable.object.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import me.krob.spacegame.drawable.Drawable;
import me.krob.spacegame.view.SpaceGameView;

public class StatusBar extends Drawable {
    private final SpaceGameView view;

    private final Paint paint, borderPaint, damagePaint;
    private final float minX, maxX, scale;
    private final RectF borderRect;

    public StatusBar(SpaceGameView view) {
        super(200, 300);
        this.view = view;

        borderRect = new RectF();

        paint = new Paint();
        paint.setColor(Color.GREEN);

        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);

        damagePaint = new Paint();
        damagePaint.setColor(Color.RED);

        minX = view.getScreenX() * 0.90f;
        maxX = view.getScreenX() * 0.98f;
        scale = (maxX - minX) / 100f;

        updateRect(minX, 30, maxX, 75);
        borderRect.set(minX - 10, 20, maxX + 10, 85);
    }

    public void createBitmap(Context context) {
        // Do nothing
    }

    public void draw(Canvas canvas) {
        long health = view.getScoreHandler().getHealth();

        // Drawing the border
        canvas.drawRect(borderRect, borderPaint);

        // Ensuring the health is above 0
        if (health > 0) {
            // Updating the rect
            updateRect(minX, 30, minX + (health * scale), 75);

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
