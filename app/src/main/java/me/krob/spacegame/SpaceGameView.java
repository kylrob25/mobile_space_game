package me.krob.spacegame;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SpaceGameView extends SurfaceView implements Runnable{
    private static final String TOP_TEXT = "Score: %s Lives: %s FPS: %s";
    private static final int LIVES = 4;
    private static final int SCORE = 10;

    private final Context context;
    private final SurfaceHolder holder;
    private final Paint paint;

    private final int screenX;
    private final int screenY;

    private Spaceship spaceship;

    private Thread thread = null;
    private volatile boolean playing;
    private boolean paused = true;

    public int score = SCORE, lives = LIVES;

    private long framesPerSecond;

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
     * Initiating the spaceship
     */
    private void initLevel(){
        spaceship = new Spaceship(this, context, screenX, screenY);
    }

    /**
     * Updating the spaceship
     */
    private void update() {
        spaceship.update(framesPerSecond);
    }

    /**
     * Drawing our background and objects
     */
    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas(); // Locking the canvas

            drawBackground(canvas);

            spaceship.draw(canvas, paint);

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
        paint.setTextSize(40); // Set the text size
        canvas.drawText(String.format(TOP_TEXT, score, lives, framesPerSecond), 10,50, paint); // Draw the text
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
     * Pause the game by closing the thread
     */
    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("[Error]", "Failed to join thread.");
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
     * Handling the user touch event
     * @param event - the motion event
     * @return - true
     */
    public boolean onTouchEvent(MotionEvent event) {
        spaceship.handleMovement(event);
        return true;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
