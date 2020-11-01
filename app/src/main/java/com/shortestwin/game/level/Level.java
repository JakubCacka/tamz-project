package com.shortestwin.game.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.shortestwin.game.GameView;
import com.shortestwin.game.level.path.PathFinder;
import com.shortestwin.game.utils.Cell;

public class Level {
    private GameView game;

    public int width;
    public int height;
    public int rectSize;

    private Tile[] tiles;

    public PathFinder pathFinder;

    public Level(GameView game) {
        this.game = game;

        this.width = game.screenWidth;
        this.height = game.screenWidth;
        this.rectSize = game.screenWidth / 10;
        int rectX = 0;
        int rectY = game.screenHeight / 2 - rectSize * 5;

        this.pathFinder = new PathFinder(this);

        this.tiles = new Tile[100];
        for(int i = 0; i < 100; i++) {
            Rect tileRect = new Rect(rectX, rectY, rectX + rectSize, rectY + rectSize);
            if(i % 2 == 0) {
                this.tiles[i] = new Tile(false, 1, tileRect);
            } else {
                this.tiles[i] = new Tile(true, -1, tileRect);
            }

            rectX += rectSize;
            int col = (i + 1) % 10;
            int row = (i + 1) / 10;
            if((i + 1) % 10 == 0) {
                rectX = 0;
                rectY += rectSize;
            }

            this.pathFinder.add(this.tiles[i], new Cell(col, row));
        }

        LevelGenerator levelGenerator = new LevelGenerator(1, this);
        levelGenerator.generateLevel();
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, game.screenWidth, game.screenHeight, paint);

        for(int i = 0; i < 100; i++) {
            this.tiles[i].draw(canvas, paint);
        }
    }
}
