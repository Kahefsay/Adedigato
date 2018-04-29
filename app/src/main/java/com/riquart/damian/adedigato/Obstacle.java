package com.riquart.damian.adedigato;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

class Obstacle {
    private SharedPreferences sp;

    private static Bitmap[] globalBitmap = new Bitmap[2];
    private final Bitmap[] bitmap = new Bitmap[2];

    private int height;
    private int width;

    private int x;
    private int y;

    private boolean easy, hard;

    private final float gapHeight;

    Obstacle(Context context, int x, int y) {
        sp = context.getSharedPreferences("adedigato" ,Context.MODE_PRIVATE);

        easy = sp.getBoolean("easy", false);
        hard = sp.getBoolean("hard", false);

        height = context.getResources().getDisplayMetrics().heightPixels;
        width = context.getResources().getDisplayMetrics().widthPixels;
        /* DEFINIT L'ESPACE ENTRE LES TUYAUX*/

        if (easy)
            gapHeight = context.getResources().getDisplayMetrics().heightPixels  / 4f;
        else if (hard)
            gapHeight = context.getResources().getDisplayMetrics().heightPixels  / 5f;
        else
            gapHeight = context.getResources().getDisplayMetrics().heightPixels  / 4.5f;

        /* Utilisation d'une tableau static pour réduire l'utilisation de la ram */
        if(globalBitmap[0] == null) {
            globalBitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.moss_obstacle_down);
            globalBitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.moss_obstacle_up);
        }
        this.bitmap[0] = Bitmap.createScaledBitmap(globalBitmap[0], width / 5, height, true);
        this.bitmap[1] = Bitmap.createScaledBitmap(globalBitmap[1], width / 5, height, true);

        this.x = x;
        this.y = y;
    }

    void move() {
        /* L'OBSTACLE BOUGE DE LA DROITE VERS LA GAUCHE */
        if (easy)
            this.x -= 20;
        else if (hard)
            this.x -= 30;
        else
            this.x -= 25;

    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap[0], x,  y - (this.gapHeight / 2) , null);
        canvas.drawBitmap(bitmap[1], x, ((this.height) + this.gapHeight / 2) + y, null);
    }

    int getWidth() {
        return width;
    }

    float getGapHeight() {
        return gapHeight;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

}
