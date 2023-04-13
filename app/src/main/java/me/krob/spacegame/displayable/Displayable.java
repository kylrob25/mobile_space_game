package me.krob.spacegame.displayable;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.function.Consumer;

public class Displayable {
    private Paint paint;

    private String text;

    private float locX, locY;

    private long duration;
    private boolean active, hide;

    private Consumer<Displayable> update;

    public Displayable(String text, float locX, float locY, long duration, boolean active, int textSize, int color, Consumer<Displayable> update) {
        this.text = text;

        this.locX = locX;
        this.locY = locY;

        this.duration = duration;
        this.active = active;

        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(color);

        this.update = update;
    }

    public void draw(Canvas canvas) {
        if (update != null) update.accept(this);
        canvas.drawText(text, locX, locY, paint);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public Paint getPaint() {
        return paint;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isHide() {
        return hide;
    }
}
