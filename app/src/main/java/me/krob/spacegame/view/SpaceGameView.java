package me.krob.spacegame.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import me.krob.spacegame.drawable.model.Bullet;
import me.krob.spacegame.drawable.model.Joypad;
import me.krob.spacegame.drawable.model.Spaceship;

public class SpaceGameView extends SurfaceView implements Runnable {
    private static final String TOP_TEXT = "Score: %s Lives: %s FPS: %s Frames: %s";
    private static final int LIVES = 4;
    private static final int SCORE = 10;

    private final Context context;
    private final SurfaceHolder holder;
    private final Paint paint;

    private final int screenX;
    private final int screenY;

    private Thread thread = null;

    private Spaceship spaceship;
    private Joypad joypad;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Bullet> expiredBullets = new ArrayList<>();

    private volatile boolean playing;
    private boolean paused = true;

    public int score = SCORE, lives = LIVES;

    private long framesPerSecond;
    private int totalFrames;

    public SpaceGameView(Context context, int x, int y) {
        super(context);
        this.context = context;

        holder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        initLevel();
    }

    /**
     * The game loop
     */
    public void run() {
        while (playing) {
            long startTime = System.currentTimeMillis();

            // Ensuring we aren't paused before updating
            if(!paused){
                update();
            }

            draw();

            // Calculating the frame times
            long lastFrameTime = System.currentTimeMillis() - startTime;
            if (lastFrameTime >= 1) {
                framesPerSecond = 1000 / lastFrameTime;

                // Increasing score based on time
                if (totalFrames++ % 500 == 0) {
                    score += 5;
                }
            }
        }
    }

    /**
     * Initiating the spaceship
     */
    private void initLevel(){
        spaceship = new Spaceship(this, context);
        joypad = new Joypad(this, context);
    }

    /**
     * Updating the spaceship
     */
    private void update() {
        spaceship.update(framesPerSecond);
        spaceship.handleCollisions();

        bullets.forEach(bullet -> {
            if (bullet.isActive()) {
                bullet.update(framesPerSecond);
                bullet.handleCollisions();
            }
        });

        // Make sure there aren't any memory leaks
        expiredBullets.removeIf(bullet -> {
            bullets.remove(bullet);
            return true;
        });
    }

    /**
     * Drawing our background and objects
     */
    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas(); // Locking the canvas

            drawBackground(canvas);

            spaceship.draw(canvas, paint);
            joypad.draw(canvas, paint);

            bullets.forEach(bullet -> {
                if (bullet.isActive()) {
                    bullet.draw(canvas, paint);
                }
            });

            drawText(canvas);

            holder.unlockCanvasAndPost(canvas); // Unlock the canvas
        }
    }

    /**
     * Drawing the background to the canvas
     */
    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.argb(255, 26, 128, 182)); // Draw the background colour
    }

    /**
     * Drawing the display text
     */
    private void drawText(Canvas canvas) {
        paint.setColor(Color.argb(255,  249, 129, 0)); // Set the colour
        paint.setTextSize(50); // Set the text size
        canvas.drawText(String.format(TOP_TEXT, score, lives, framesPerSecond, totalFrames), 10,50, paint); // Draw the text
    }

    /**
     * Pause the game by closing the thread
     */
    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (Throwable throwable) {
            Log.e("[Error]", "Failed to shutdown thread.");
        }
    }

    /**
     * Start the game by creating and starting the thread
     */
    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
        Log.i("[Info]", "Started thread.");
    }

    /**
     * Register a bullet
     * @param bullet - the bullet
     */
    public void registerBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * Remove a bullet
     * @param bullet - the bullet
     */
    public void removeBullet(Bullet bullet) {
        expiredBullets.add(bullet);
    }

    /**
     * Remove a bullet
     * @param id - the bullet id
     */
    public void removeBullet(int id) {
        for (Bullet bullet : bullets) {
            if (bullet.getId() == id) {
                expiredBullets.add(bullet);
                break;
            }
        }
    }

    /**
     * Handling the user touch event
     * @param event - the motion event
     * @return - true
     */
    public boolean onTouchEvent(MotionEvent event) {
        joypad.handleMovement(event);
        return true;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public float getScreenX() {
        return screenX;
    }

    public float getScreenY() {
        return screenY;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }
}