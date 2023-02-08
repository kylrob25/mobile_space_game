package me.krob.spacegame;// package com.example.glenn.spacegame;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class SpaceGameView extends SurfaceView implements Runnable{
    private static final String DATA = "Score: %s Lives: %s FPS: %s";

    private final Context context;

    private Thread thread = null;

    private final SurfaceHolder holder;

    private volatile boolean playing;
    private boolean paused = true;

    private Canvas canvas;
    private final Paint paint;

    private long framesPerSecond, lastFrameTime;

    private final int screenX;
    private final int screenY;

    public int score = 0;

    private int lives = 4;
    private Spaceship spaceShip;

    public SpaceGameView(Context context, int x, int y) {
        super(context);
        this.context = context;

        holder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        initLevel();
    }

    private void initLevel(){
        spaceShip = new Spaceship(context, screenX, screenY);
    }

    @Override
    public void run() {
        while (playing) {
            score = 10;

            long startTime = System.currentTimeMillis();

            if(!paused){
                update();
            }

            draw();

            lastFrameTime = System.currentTimeMillis() - startTime;
            if (lastFrameTime >= 1) {
                framesPerSecond = 1000 / lastFrameTime;
            }
        }
    }

    private void update(){
        spaceShip.update(framesPerSecond);
        checkCollisions();
    }

    private void checkCollisions(){

    }

    private void draw(){
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            canvas.drawColor(Color.argb(255, 26, 128, 182));

            paint.setColor(Color.argb(255,  255, 255, 255));

            canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY() , paint);

            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText(String.format(DATA, score, lives, framesPerSecond), 10,50, paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("[Error]", "Failed to join thread.");
        }
    }

    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
        Log.i("[Info]", "Started thread.");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                paused = false;

                if(event.getY() > screenY - (screenY / 2.0)) {
                    if (event.getX() > screenX / 2.0) {
                        spaceShip.moveState = Spaceship.MoveState.RIGHT;
                    } else {
                        spaceShip.moveState = Spaceship.MoveState.LEFT;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (event.getY() > screenY - (screenY / 2.0)) {
                    spaceShip.moveState = Spaceship.MoveState.NONE;
                }
                break;
        }
        return true;
    }
}
