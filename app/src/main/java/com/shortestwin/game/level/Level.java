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
    int tilesRowCount;

    private Tile[] tiles;
    private boolean regenerate;

    public PathFinder pathFinder;
    private LevelGenerator levelGenerator;

    private int level;

    public Level(GameView game) {
        this.game = game;

        this.level = 1;
        this.tilesRowCount = 10;

        this.width = game.screenWidth;
        this.height = game.screenWidth;
        this.topOffset = game.screenHeight / 2 - this.height / 2;
        this.rectSize = game.screenWidth / this.tilesRowCount;

        this.pathFinder = new PathFinder(this);
        this.levelGenerator = new LevelGenerator( this, tilesRowCount, tilesRowCount);

        this.tiles = this.levelGenerator.generateLevel(this.level);
    }

    public void draw(Canvas canvas, Paint paint) {

        for(int i = 0; i < tilesRowCount * tilesRowCount; i++) {
            this.tiles[i].draw(canvas, paint);
        }
    }

    public void regenerateLevelTiles() {
        this.tiles = this.levelGenerator.generateLevel(this.level);
        this.level++;
    }

    public void update() {
        if(regenerate) {
            this.regenerateLevelTiles();
            regenerate = false;
        }
    }

    public void setRegenerate(boolean regenerate) {
        this.regenerate = regenerate;
    }
}
