package me.krob.spacegame.handler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import me.krob.spacegame.drawable.object.model.Bullet;
import me.krob.spacegame.drawable.object.model.Joypad;
import me.krob.spacegame.drawable.object.model.Ship;
import me.krob.spacegame.view.SpaceGameView;

public class GameObjectHandler {

    private final Ship ship;
    private final Joypad joypad;

    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Bullet> expiredBullets = new ArrayList<>();

    public GameObjectHandler(SpaceGameView view) {
        joypad = new Joypad(view);
        ship = new Ship(view);
    }

    public void update(long framesPerSecond) {
        ship.update(framesPerSecond);

        bullets.forEach(bullet -> {
            if (bullet.isActive()) {
                bullet.update(framesPerSecond);
            }
        });

        // Make sure there aren't any memory leaks
        expiredBullets.removeIf(bullet -> {
            bullets.remove(bullet);
            return true;
        });
    }

    public void draw(Canvas canvas, Paint paint) {
        ship.draw(canvas, paint);
        joypad.draw(canvas, paint);

        bullets.forEach(bullet -> {
            if (bullet.isActive()) {
                bullet.draw(canvas, paint);
            }
        });
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        expiredBullets.add(bullet);
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        joypad.onTouchEvent(motionEvent);
    }

    public Ship getShip() {
        return ship;
    }

    public Joypad getJoypad() {
        return joypad;
    }
}
