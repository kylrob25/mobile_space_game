package me.krob.spacegame.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface IDrawable {
    void createBitmap(Context context);
    void draw(Canvas canvas, Paint paint);
    void update(long framesPerSecond);
}
