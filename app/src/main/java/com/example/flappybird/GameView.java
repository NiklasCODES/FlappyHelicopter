package com.example.flappybird;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable{
    private Thread thread;
    private boolean isGameOver;
    private boolean isPlaying;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Flight flight;
    private Background background1, background2;
    private List<Bullet> bullets;
    private Flame[] flames;
    private Random random;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        paint = new Paint();
        isGameOver = false;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        flight = new Flight(this, screenY, getResources());

        bullets = new ArrayList<>();

        flames = new Flame[4];
        random = new Random();

        background2.x = screenX;


        for(int i = 0; i < 4; i++) {
            Flame flame = new Flame(getResources());
            flames[i] = flame;
        }
    }

    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        background1.x = (int)(background1.x - (10 * screenRatioX));
        background2.x = (int)(background2.x - (10 * screenRatioX));

        if(background1.x + background1.background.getWidth() < ((0.2) * background1.background.getWidth())) {
            background1.x = screenX;
        }

        if(background2.x + background2.background.getWidth() < ((0.2) * background2.background.getWidth())) {
            background2.x = screenX;
        }

       if(flight.isGoingUp) {
            flight.y -= 30 * screenRatioY;
        } else {
            flight.y += 30 * screenRatioY;
        }

        if(flight.y < 0) {
            flight.y = 0;
        }

        if(flight.y > screenY - flight.height) {
            flight.y = screenY - flight.height;
        }

        List<Bullet> trash = new ArrayList<>();


        for(Bullet bullet: bullets) {
            if((bullet.x * 0.1) > screenX) { //if true, bullet is off screen
                trash.add(bullet);
            }
            bullet.x = (int) (bullet.x + (50 * screenRatioX));

            //checking collision of bullet and flame
            for (Flame flame: flames) {
                if(Rect.intersects(flame.getCollisionShape(), bullet.getCollisionShape())) {
                    flame.x = -500;
                    bullet.x = screenX + 500;
                    flame.wasShot = true;
                }
            }
        }

        for(Bullet bullet: trash) {
            bullets.remove(bullet);
        }

        for(Flame flame: flames) {
            flame.x = flame.x - flame.speed;

            if(flame.x + flame.width < 0) { //checking flame off screen

                if(!flame.wasShot) {
                    isGameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                flame.speed = random.nextInt(bound);

                if(flame.speed < 10 * screenRatioX)
                    flame.speed = (int) (10 * screenRatioX);

                    flame.x = screenX;
                    flame.y = random.nextInt(screenY - flame.height);

                    flame.wasShot = false;



            }
            //checking collision - ends game
            if(Rect.intersects(flame.getCollisionShape(), flight.getCollisionShape()));
            {
                isGameOver = true;
            }

        }
    }

    private void draw() { //gets canvas to draw + draws both bg
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);


            if(!isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
               return;
            }
//
        for(Flame flame: flames) {
            canvas.drawBitmap(flame.getFlame(), flame.x, flame.y, paint);
        }




            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);



            for(Bullet bullet: bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep() { //1000ms / 17ms = 60fps
        try {
            Thread.sleep(17);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    //calls run()
    public void resume() {
        thread = new Thread(this);
        thread.start();
        isPlaying = true;
    }

    //terminates thread
    public void pause() {
        try {
          //  isPlaying = false;
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenX / 2) {
                    flight.isGoingUp = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if(event.getX() > screenX / 2) { //checking if right side of screen
                    flight.toShoot++;
            }
                break;
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);
    }
}
