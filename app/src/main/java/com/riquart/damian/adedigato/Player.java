package com.riquart.damian.adedigato;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

class Player {
    private GameView view;

    private static Bitmap[] globalBitmap = new Bitmap[2];
    private final Bitmap[] bitmap = new Bitmap[2];

    private final int height;
    private final int width;

    private int x;
    private int y;

    private float speedX;
    private float speedY;

    private int currentFrame;

    Player(Context context, GameView view) {
        this.view = view;

        int height = context.getResources().getDisplayMetrics().heightPixels;
        int width = context.getResources().getDisplayMetrics().widthPixels;

        /* Utilisation d'une tableau static pour réduire l'utilisation de la ram */
        if(globalBitmap[0] == null) {
            globalBitmap[0] = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.frame1, Float.valueOf(height / 10f).intValue(), Float.valueOf(width / 10f).intValue());
            globalBitmap[1] = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.frame3, Float.valueOf(height / 10f).intValue(), Float.valueOf(width / 10f).intValue());
        }

        System.arraycopy(globalBitmap, 0, this.bitmap,0, 2);

        this.height = this.bitmap[0].getHeight();
        this.width = this.bitmap[0].getWidth();

        this.x = this.width / 6;
        this.y = context.getResources().getDisplayMetrics().heightPixels / 2;

        this.speedX = 0;
        this.speedY = 0;
    }

    void onTap() {
        this.speedY = getTabSpeed();
        this.y += getPosTabIncrease();
    }

    private float getPosTabIncrease() {
        return - view.getHeight() / 200;
    }

    private float getTabSpeed() {
        return -view.getHeight() / 16f;
    }

    void move() {
        if(speedY < 0){
            // Le personne monte
            speedY = speedY * 2 / 3 + getSpeedTimeDecrease() / 2;
            this.currentFrame = 1;
        }else{
            // Le personnage descend
            this.speedY += getSpeedTimeDecrease();
            this.currentFrame = 0;
        }
        if(this.speedY > getMaxSpeed()){
            // Vitesse maximun
            this.speedY = getMaxSpeed();
        }

        if(speedY != 0.0f) {
            if(y + speedY > view.getHeight() - height || y + speedY < 0) {
                speedY = -speedY;
            }
        }

        this.y += speedY;
        this.x += speedX;
    }

    void deadmove() {
        speedY += getSpeedTimeDecrease();
        speedX = 10;

        this.y += speedY;
        this.x += speedX;
    }

    private float getSpeedTimeDecrease() {
        return view.getHeight() / 220;
    }

    private float getMaxSpeed() {
        return view.getHeight() / 51.2f;
    }

    void draw(Canvas canvas) {
        if(GameView.isDead) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap dead = Bitmap.createBitmap(bitmap[this.currentFrame], 0, 0, bitmap[this.currentFrame].getWidth(), bitmap[this.currentFrame].getHeight(), matrix, true);
            canvas.drawBitmap(dead, x, y, null);
        } else {
            canvas.drawBitmap(bitmap[this.currentFrame], x, y , null);
        }

    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}