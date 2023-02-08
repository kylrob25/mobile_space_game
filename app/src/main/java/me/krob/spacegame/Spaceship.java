package me.krob.spacegame;// package com.example.glenn.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Spaceship {
    private static final int MOVEMENT_SPEED = 350;

    private final RectF rect;

    private Bitmap bitmapup;
    private Bitmap bitmapleft;
    private Bitmap bitmapright;
    private Bitmap bitmapdown;
    public Bitmap currentBitmap;

    private final float height;
    private final float length;
    private float x;
    private float y;

    private final int screenX;
    private final int screenY;

    public MoveState moveState = MoveState.NONE;
    private int movementSpeed = MOVEMENT_SPEED;

    public Spaceship(Context context, int screenX, int screenY){

        rect = new RectF();

        length = screenX/10;
        height = screenY/10;

        x = screenX / 2;
        y = screenY / 2;

        bitmapup = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);

        // stretch the bitmap to a size appropriate for the screen resolution
        bitmapup = Bitmap.createScaledBitmap(bitmapup,
                (int) (length),
                (int) (height),
                false);

      //  bitmapup = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
      //  bitmapup = Bitmap.createScaledBitmap(bitmapup, (int) (length), (int) (height),false);

        bitmapright = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        bitmapright = Bitmap.createScaledBitmap(bitmapright, (int) (length), (int) (height),false);

        bitmapleft = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipleft);
        bitmapleft = Bitmap.createScaledBitmap(bitmapleft, (int) (length), (int) (height),false);

        bitmapdown = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipdown);
        bitmapdown = Bitmap.createScaledBitmap(bitmapdown, (int) (length), (int) (height),false);

        currentBitmap = bitmapleft;
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public void update(long fps){
        float movement = movementSpeed / fps;

        switch (moveState) {
            case LEFT:
                x -= movement;
                currentBitmap = bitmapleft;

                if ((x+length)<=0) {
                    x = screenX;
                }
                break;
            case RIGHT:
                x += movement;
                currentBitmap = bitmapright;

                if (x>=screenX) {
                    x = 0 - length;
                }
                break;
            case UP:
                y -= movement;
                currentBitmap = bitmapup;

                if (y+height <=0) {
                    y = screenY;
                }
                break;
            case DOWN:
                y += movement;
                currentBitmap = bitmapdown;

                if (y>=screenY) {
                    y = 0 - height;
                }
                break;
        }

        updateRect();
    }

    private void updateRect() {
        rect.set(x, y, x + length, y + height);
    }

    public Bitmap getBitmap(){
        return currentBitmap;
    }

    public float getX(){
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY(){
            return y;
        }

    public void setY(int y){
            this.y = y;
    }

    public float getLength(){
        return length;
    }


    enum MoveState {
        NONE, UP, DOWN, LEFT, RIGHT
    }
}
