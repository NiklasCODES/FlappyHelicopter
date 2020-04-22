package com.example.flappybird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.flappybird.GameView.screenRatioX;
import static com.example.flappybird.GameView.screenRatioY;

public class Bullet {
    int x, y, width, height;
    Bitmap bullet;

    Bullet(Resources res) {
        bullet = BitmapFactory.decodeResource(res, R.drawable.shoot1);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width = width / 4;
        height = height / 4;

        width = (int)(width * screenRatioX);
        height = (int)(height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }
    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
