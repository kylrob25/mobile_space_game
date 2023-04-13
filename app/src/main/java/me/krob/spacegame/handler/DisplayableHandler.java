package me.krob.spacegame.handler;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.krob.spacegame.displayable.Displayable;
import me.krob.spacegame.view.SpaceGameView;

public class DisplayableHandler {
    private final SpaceGameView view;

    private final Map<String, Displayable> displayableMap = new HashMap<>();

    public DisplayableHandler(SpaceGameView view) {
        this.view = view;

        displayableMap.put("score", new Displayable(
                "Score: 0 ",
                10,
                50,
                0,
                true,
                60,
                Color.GREEN,
                update -> update.setText(String.format("FPS: %s Score: %s",
                        view.getFramesPerSecond(), view.getScoreHandler().getScore()
                ))
        ));

        displayableMap.put("game_over", new Displayable(
                "GAME OVER",
                (view.getScreenX() / 2f) - (100 * 2),
                view.getScreenY() / 2f,
                1500,
                false,
                100,
                Color.RED,
                null
        ));
    }

    public void draw(Canvas canvas) {
        displayableMap.values().forEach(displayable -> {
            if (displayable.isActive()) {
                displayable.draw(canvas);

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

    public Displayable getDisplayable(String id) {
        return displayableMap.get(id);
    }
}
