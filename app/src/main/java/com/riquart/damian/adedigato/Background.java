package com.riquart.damian.adedigato;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

class Background {
    private static Bitmap[] globalBitmap = new Bitmap[5];
    private final Bitmap[] bitmap = new Bitmap[5];

    private int height;
    private int width;

    private int scrollCiel;
    private int scrollMontagne;
    private int scrollArbre;
    private int scrollSol;

    Background(Context context) {
        height = context.getResources().getDisplayMetrics().heightPixels;
        width = context.getResources().getDisplayMetrics().widthPixels;

        if(globalBitmap[0] == null) {
            globalBitmap[0] = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer1, width, height);
            globalBitmap[1] = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer2, width, height);
            globalBitmap[2] = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer3, width, height);
            globalBitmap[3] = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer4, width, height);
            globalBitmap[4] = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer5, width, height);
        }

        System.arraycopy(globalBitmap, 0, this.bitmap,0, 5);
    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap[0], new Rect(0, 0, bitmap[0].getWidth(), bitmap[0].getHeight()), new Rect(-scrollCiel, 0, (width * height) / this.width - scrollCiel, height), null);
        canvas.drawBitmap(bitmap[0], new Rect(0, 0, bitmap[0].getWidth(), bitmap[0].getHeight()), new Rect(((width * height) / this.width) - scrollCiel, 0, ((width * height) / this.width) + (width * height) / this.width - scrollCiel, height), null);
        canvas.drawBitmap(bitmap[1], new Rect(0, 0, bitmap[1].getWidth(), bitmap[1].getHeight()), new Rect(-scrollCiel, 0, (width * height) / this.width - scrollCiel, height), null);
        canvas.drawBitmap(bitmap[1], new Rect(0, 0, bitmap[1].getWidth(), bitmap[1].getHeight()), new Rect(((width * height) / this.width) - scrollCiel, 0, ((width * height) / this.width) + (width * height) / this.width - scrollCiel, height), null);
        canvas.drawBitmap(bitmap[2], new Rect(0, 0, bitmap[2].getWidth(), bitmap[2].getHeight()), new Rect(-scrollMontagne, 0, (width * height) / this.width - (scrollMontagne), height), null);
        canvas.drawBitmap(bitmap[2], new Rect(0, 0, bitmap[2].getWidth(), bitmap[2].getHeight()), new Rect(((width * height) / this.width) - (scrollMontagne), 0, ((width * height) / this.width) + (width * height) / this.width - (scrollMontagne), height), null);
        canvas.drawBitmap(bitmap[3], new Rect(0, 0, bitmap[3].getWidth(), bitmap[3].getHeight()), new Rect(-scrollArbre, 0, (width * height) / this.width - scrollArbre, height), null);
        canvas.drawBitmap(bitmap[3], new Rect(0, 0, bitmap[3].getWidth(), bitmap[3].getHeight()), new Rect(((width * height) / this.width) - scrollArbre, 0, ((width * height) / this.width) + (width * height) / this.width - scrollArbre, height), null);
        canvas.drawBitmap(bitmap[4], new Rect(0, 0, bitmap[4].getWidth(), bitmap[4].getHeight()), new Rect(-scrollArbre, 0, (width * height) / this.width - scrollArbre, height), null);
        canvas.drawBitmap(bitmap[4], new Rect(0, 0, bitmap[4].getWidth(), bitmap[4].getHeight()), new Rect(((width * height) / this.width) - scrollArbre, 0, ((width * height) / this.width) + (width * height) / this.width - scrollSol, height), null);

        if(!GameView.isDead) {
            scrollCiel++;
            scrollMontagne += 3;
            scrollArbre += 6;
            scrollSol += 6;

            if (this.scrollCiel > (width * height) / this.width) {
                this.scrollCiel = 0;
            }
            if (this.scrollMontagne > (width * height) / this.width) {
                this.scrollMontagne = 0;
            }
            if (this.scrollArbre > (width * height) / this.width) {
                this.scrollArbre = 0;
            }
            if (this.scrollSol > (width * height) / this.width) {
                this.scrollSol = 0;
            }
        }


    }
}
