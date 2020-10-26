package com.shortestwin.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.shortestwin.game.core.InputHandler;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    public int screenWidth, screenHeight;
    private float screeRatioX, screenRatioY;

    private InputHandler inputHandler;
    private Paint paint;

    public Rect rect;
    public boolean rectUp;
    public boolean rectDown;

    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // To fit all mobiles with same ratio as mine
        this.screeRatioX = 2340f / screenWidth;
        this.screenRatioY = 1080f / screenHeight;

        this.inputHandler = new InputHandler(this);
        this.paint = new Paint();

        this.rect = new Rect(490, screenHeight - 200, 590, screenHeight - 100);
        this.rectUp = false;
        this.rectDown = false;
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
        if(this.rect.left >= this.screenWidth) {
            this.rect.left = 0;
            this.rect.right = 100;
        } else {
            this.rect.left += 10;
            this.rect.right += 10;
        }

        if(this.rectUp && this.rect.top > 0) {
            this.rect.top -= 10;
            this.rect.bottom = this.rect.top + 100;
        } else if(this.rectUp) {
            this.rect.top = 0;
            this.rect.bottom = this.rect.top + 100;
        }

        if(this.rectDown && this.rect.top + 100 < screenHeight) {
            this.rect.top += 10;
            this.rect.bottom = this.rect.top + 100;
        } else if(this.rectDown) {
            this.rect.top = screenHeight - 100;
            this.rect.bottom = this.rect.top + 100;
        }
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            paint.setColor(Color.BLACK);
            canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
            paint.setColor(Color.WHITE);
            canvas.drawRect(rect, paint);

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
