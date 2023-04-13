package me.krob.spacegame.displayable;

import android.graphics.Paint;

public class AlertDisplayable extends Displayable{
    public AlertDisplayable(String text, float screenX, float screenY, Paint paint) {
        super(
                text,
                screenX / 2f,
                (screenY / 2f) - ((paint.ascent() + paint.descent())/2),
                1500,
                false,
                paint,
                null
        );
    }
}
