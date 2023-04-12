package me.krob.spacegame.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.krob.spacegame.handler.GameObjectHandler;
import me.krob.spacegame.handler.ScoreHandler;

public class SpaceGameView extends SurfaceView implements Runnable {
    private final Context context;
    private final SurfaceHolder holder;

    private final Paint paint;

    private final int screenX;
    private final int screenY;

    private final float borderY, borderY1;

    private final ScoreHandler scoreHandler;
    private final GameObjectHandler objectHandler;

    private Thread thread = null;
    private volatile boolean playing;
    private boolean paused = true;

    private long framesPerSecond;

    public SpaceGameView(Context ctx, int x, int y) {
        super(ctx);

        context = ctx;
        holder = getHolder();

        paint = new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.GREEN);

        screenX = x;
        screenY = y;

        borderY = screenY / 8f;
        borderY1 = screenY - (screenY / 10f);

        scoreHandler = new ScoreHandler();
        objectHandler = new GameObjectHandler(this);
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
            }
        }
    }

    /**
     * Updating the object handler
     */
    private void update() {
        if (framesPerSecond > 0) {
            objectHandler.update(framesPerSecond);
        }
    }

    /**
     * Drawing our background and objects
     */
    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas(); // Locking the canvas

            drawBackground(canvas);

            objectHandler.draw(canvas);

            drawBorder(canvas);
            drawText(canvas);

            holder.unlockCanvasAndPost(canvas); // Unlock the canvas
        }
    }

    private void drawBorder(Canvas canvas) {
        // Only for debug purposes
        //canvas.drawLine(0, borderY, screenX, borderY, paint);
        //canvas.drawLine(0, borderY1, screenX, borderY1, paint);
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
        //canvas.drawText(String.format(TOP_TEXT, scoreHandler.getScore(), scoreHandler.getLives(), framesPerSecond), 10,50, paint); // Draw the text
        canvas.drawText(String.format("FPS: %s Score: %s", framesPerSecond, scoreHandler.getScore()), 10, 50, paint);
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

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Handling the user touch event
     * @param event - the motion event
     * @return - true
     */
    public boolean onTouchEvent(MotionEvent event) {
        objectHandler.onTouchEvent(event);
        return true;
    }

    public GameObjectHandler getObjectHandler() {
        return objectHandler;
    }

    public ScoreHandler getScoreHandler() {
        return scoreHandler;
    }

    public float getScreenX() {
        return screenX;
    }

    public float getScreenY() {
        return screenY;
    }

    public float getBorderY() {
        return borderY;
    }

    public float getBorderY1() {
        return borderY1;
    }
}
