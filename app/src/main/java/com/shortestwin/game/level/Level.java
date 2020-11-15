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

    public Level(GameView game) {
        this.game = game;

        this.tilesRowCount = 30;

        this.width = game.screenWidth;
        this.height = game.screenWidth;
        this.topOffset = game.screenHeight / 2 - this.height / 2;
        this.rectSize = game.screenWidth / this.tilesRowCount;

        this.pathFinder = new PathFinder(this);
        this.levelGenerator = new LevelGenerator(10, this, tilesRowCount, tilesRowCount);

        this.tiles = this.levelGenerator.generateLevel();
    }

    public void draw(Canvas canvas, Paint paint) {

        for(int i = 0; i < tilesRowCount * tilesRowCount; i++) {
            this.tiles[i].draw(canvas, paint);
        }
    }

    public void regenerateLevelTiles() {
        this.tiles = this.levelGenerator.generateLevel();
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
