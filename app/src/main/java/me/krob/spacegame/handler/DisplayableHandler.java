package me.krob.spacegame.handler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

import me.krob.spacegame.displayable.AlertDisplayable;
import me.krob.spacegame.displayable.Displayable;
import me.krob.spacegame.displayable.DisplayableType;
import me.krob.spacegame.view.SpaceGameView;

public class DisplayableHandler {
    private final SpaceGameView view;

    private final Map<DisplayableType, Displayable> displayableMap = new HashMap<>();

    public DisplayableHandler(SpaceGameView view) {
        this.view = view;

        displayableMap.put(DisplayableType.SCORE, new Displayable(
                "Score: 0 ",
                10,
                50,
                60,
                Color.GREEN,
                update -> update.setText(String.format("FPS: %s Score: %s",
                        view.getFramesPerSecond(), view.getScoreHandler().getScore()
                ))
        ));

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);

        displayableMap.put(DisplayableType.INVADER_POWER_UP, new AlertDisplayable(
                "INVADER POWER UP!", view.getScreenX(), view.getScreenY(), paint
        ));
        displayableMap.put(DisplayableType.GAME_OVER, new AlertDisplayable(
                "GAME OVER!", view.getScreenX(), view.getScreenY(), paint
        ));
    }

    /**
     * Display a displayable
     * @param type
     */
    public void display(DisplayableType type) {
        Displayable displayable = displayableMap.get(type);
        if (displayable != null) {
            displayable.setActive(true);
        }
    }

    /**
     * Drawing each Displayable to the screen
     * @param canvas
     */
    public void draw(Canvas canvas) {
        // Looping through the collection
        displayableMap.values().forEach(displayable -> {
            // Checking the displayable is active
            if (displayable.isActive()) {
                // Drawing the displayable
                displayable.draw(canvas);

                /*
                Checking whether the Displayable is permanent,
                if not, we post a delayed task to hide the Displayable
                after the duration period.
                 */
                if (!displayable.isHide() && displayable.getDuration() > 0) {
                    displayable.setHide(true);

                    view.postDelayed(() -> {
                        displayable.setActive(false);
                        displayable.setHide(false);
                    }, displayable.getDuration());
                }
            }
        });
    }
}
