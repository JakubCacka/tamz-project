package com.shortestwin.game.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.shortestwin.game.GameView;
import com.shortestwin.game.level.path.PathFinder;

public class Level {
    private GameView game;

    public int width;
    public int height;
    public int topOffset;
    public int rectSize;

    private Tile[] tiles;

    public PathFinder pathFinder;
    private LevelGenerator levelGenerator;

    public Level(GameView game) {
        this.game = game;

        this.width = game.screenWidth;
        this.height = game.screenWidth;
        this.topOffset = game.screenHeight / 2 - this.height / 2;
        this.rectSize = game.screenWidth / 10;

        this.pathFinder = new PathFinder(this);
        this.levelGenerator = new LevelGenerator(1, this);
        this.tiles = this.levelGenerator.getLevel();
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, game.screenWidth, game.screenHeight, paint);

        for(int i = 0; i < 100; i++) {
            this.tiles[i].draw(canvas, paint);
        }

        levelGenerator.generateLevel();
    }
}
