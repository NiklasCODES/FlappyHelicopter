package com.example.flappybird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.flappybird.GameView.screenRatioX;
import static com.example.flappybird.GameView.screenRatioY;

public class Flame {
    public int speed = 20;
    public boolean wasShot = true;
    int flameCounter = 1;
    int x, y;
    int width, height;
    Bitmap flame1, flame2, flame3, flame4;

    Flame(Resources res, int randomPosY, int screenWidth) {
        flame1 = BitmapFactory.decodeResource(res, R.drawable.flame);
        flame2 = BitmapFactory.decodeResource(res, R.drawable.flame);
        flame3 = BitmapFactory.decodeResource(res, R.drawable.flame);
        flame4 = BitmapFactory.decodeResource(res, R.drawable.flame);

        width = flame1.getWidth();
        height = flame1.getHeight();

        width = width / 6;
        height = height / 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        flame1 = Bitmap.createScaledBitmap(flame1, width, height, false);
        flame2 = Bitmap.createScaledBitmap(flame1, width, height, false);
        flame3 = Bitmap.createScaledBitmap(flame1, width, height, false);
        flame4 = Bitmap.createScaledBitmap(flame1, width, height, false);

        //y = -height;
        x = 1920;
        y = randomPosY;
    }

    Bitmap getFlame() {
        if(flameCounter == 1) {
            flameCounter++;
            return flame1;
        }

        if(flameCounter == 2) {
            flameCounter++;
            return flame2;
        }

        if(flameCounter == 1) {
            flameCounter++;
            return flame1;
        }

        if(flameCounter == 3) {
            flameCounter++;
            return flame3;
        }

        flameCounter = 1;
        return flame4;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
