package me.krob.spacegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Drawable implements IDrawable {
    protected final RectF rect;
    protected float locX, locY;
    protected final float height, length;

    protected Bitmap bitmap;

    public Drawable(float height, float length) {
        this.height = height;
        this.length = length;

        rect = new RectF();
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public abstract void update(long framesPerSecond);

    /**
     * Updating the rect
     */
    protected void updateRect() {
        rect.set(locX - length, locY, locX + length, locY + height);
    }

    /**
     * Set the current bitmap
     * @param bitmap
     */
    protected void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    protected float getHeight() {
        return height;
    }

    protected float getLength() {
        return length;
    }

    protected float getLocX() {
        return locX;
    }

    protected float getLocY() {
        return locY;
    }
}
