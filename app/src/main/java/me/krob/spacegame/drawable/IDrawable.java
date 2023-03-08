package me.krob.spacegame.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public interface IDrawable {
    void createBitmap(Context context);
    void draw(Canvas canvas);

    RectF getRect();
}
