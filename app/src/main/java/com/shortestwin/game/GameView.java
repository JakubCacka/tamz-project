package com.shortestwin.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.shortestwin.game.core.InputHandler;
import com.shortestwin.game.level.Level;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;

    public int screenWidth, screenHeight;
    public float screeRatioX, screenRatioY;

    private InputHandler inputHandler;
    public Level level;

    private Paint paint;

    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // To fit all mobiles with same ratio as mine
        this.screeRatioX = 2340f / screenWidth;
        this.screenRatioY = 1080f / screenHeight;

        this.inputHandler = new InputHandler(this);
        this.level = new Level(this);

        this.paint = new Paint();
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
        this.level.update();
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawRect(0, 0, this.screenWidth, this.screenHeight, paint);

            this.level.draw(canvas, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.inputHandler.handlePlayerTouch(event)) {
            return true;
        }

        return super.onTouchEvent(event);
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
