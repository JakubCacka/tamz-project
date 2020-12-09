package com.shortestwin.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.shortestwin.game.core.InputHandler;
import com.shortestwin.game.graphics.Textures;
import com.shortestwin.game.player.Player;
import com.shortestwin.game.level.Level;
import com.shortestwin.game.utils.Direction;

public class GameView extends SurfaceView implements Runnable {
    public static final String MY_PREFS_NAME = "ShortestWinPrefs";

    private Thread thread;
    private boolean isPlaying;

    private Context context;
    public DisplayMetrics display;

    public int screenWidth, screenHeight;
    public float screeRatioX, screenRatioY;

    public Textures textures;
    private InputHandler inputHandler;
    public Level level;
    private Player player;

    private Paint paint;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.display = Resources.getSystem().getDisplayMetrics();
        this.screenWidth = display.widthPixels;
        this.screenHeight = display.heightPixels;

        // To fit all mobiles with same ratio as mine
        this.screeRatioX = 2340f / screenWidth;
        this.screenRatioY = 1080f / screenHeight;

        this.textures = new Textures(context);
        this.player = new Player("John Doe", textures.getPlayerColor());
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

    public void handlePlayerMove(Direction direction) {
        this.inputHandler.handlePlayerMove(direction);
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayerMoveDir(Direction direction) {
        this.level.setPlayerMoveDir(direction);
    }
}
