package me.krob.spacegame.displayable;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.function.Consumer;

public class Displayable {
    private final Paint paint;
    private final float locX, locY;
    private final long duration;
    private final Consumer<Displayable> update;

    private boolean active, hide;
    private String text;

    public Displayable(String text, float locX, float locY, long duration, boolean active, int textSize, int color, Consumer<Displayable> update) {
        this.text = text;

        this.locX = locX;
        this.locY = locY;

        this.duration = duration;
        this.active = active;

        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.CENTER);

        this.update = update;
    }

    public Displayable(String text, float locX, float locY, long duration, boolean active, Paint paint, Consumer<Displayable> update) {
        this.text = text;

        this.locX = locX;
        this.locY = locY;

        this.duration = duration;
        this.active = active;

        this.paint = paint;

        this.update = update;
    }

    public Displayable(String text, float locX, float locY, int textSize, int color, Consumer<Displayable> update) {
        this(text, locX, locY, 0, true, textSize, color, update);
    }

    public Displayable(String text, float locX, float locY, long duration, int textSize, int color) {
        this(text, locX, locY, duration, false, textSize, color, null);
    }

    public void draw(Canvas canvas) {
        // Updating the text prior to displaying
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
