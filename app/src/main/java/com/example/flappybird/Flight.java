package com.example.flappybird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.flappybird.GameView.screenRatioX;
import static com.example.flappybird.GameView.screenRatioY;

public class Flight {
    public int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, wingCounter = 0, shootCounter = 1;
    Bitmap flight1, flight2, dead;
    private GameView gameView;


    Flight (GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;
        flight1 = BitmapFactory.decodeResource(res, R.drawable.heli);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.heli);

        width = flight1.getWidth();
        height = flight1.getHeight();

        width = width / 4;
        height = height / 4;

        width = (int)(width * screenRatioX);
        height = (int)(height * screenRatioY);

        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        dead = BitmapFactory.decodeResource(res, R.drawable.heli_damaged);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);


        y= screenY / 2;
        x = (int)(256 * screenRatioX) ;

    }

    Bitmap getFlight() {
        if(toShoot != 0) {
         //   shootCounter = 1;
            toShoot--;
            gameView.newBullet();
        }


        if(wingCounter == 0) {
            wingCounter++;
            return flight1;
        }
        else {
            wingCounter--;
            return flight2;
        }
        }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead() {
        return dead;
    }
}
