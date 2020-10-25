package com.shortestwin.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private int screenWidth, screenHeight;
    private float screeRatioX, screenRatioY;

    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // To fit all mobiles with same ratio as mine
        this.screeRatioX = 2340f / screenWidth;
        this.screenRatioY = 1080f / screenHeight;
    }

    @Override
    public void run() {
        while(this.isPlaying) {
            this.update();
            this.draw();
            this.sleep();
        }
    }

    private void update() {
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        this.thread = new Thread(this);
        this.thread.start();

        this.isPlaying = true;
    }

    public void pause() {
        try {
            this.thread.join();
            this.isPlaying = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
