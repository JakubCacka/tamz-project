package com.shortestwin.game.level;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.shortestwin.game.GameView;
import com.shortestwin.game.player.Player;
import com.shortestwin.game.level.path.PathFinder;

public class Level {
    private GameView game;

    public int width;
    public int height;
    public int topOffset;
    public int rectSize;
    public int tilesRowCount;

    private Tile[] tiles;
    private boolean regenerate;

    public PathFinder pathFinder;
    private LevelGenerator levelGenerator;

    private int level;

    private Player player;

    public Level(GameView game) {
        this.game = game;
        this.player = game.getPlayer();

        this.level = 1;
        this.tilesRowCount = 10;

        this.width = game.screenWidth;
        this.height = game.screenWidth;
        this.topOffset = game.screenHeight / 2 - this.height / 2;
        this.rectSize = game.screenWidth / this.tilesRowCount;

        this.pathFinder = new PathFinder(this);
        this.levelGenerator = new LevelGenerator(this);

        this.tiles = this.levelGenerator.generateLevel(this.level);
        this.player.setRect(this.levelGenerator.getStart().getRect());
    }

    public void draw(Canvas canvas, Paint paint) {
        for(int i = 0; i < tilesRowCount * tilesRowCount; i++) {
            this.tiles[i].draw(canvas, paint);
        }
        this.player.draw(canvas, paint, this);
    }

    public void regenerateLevelTiles() {
        this.level++;
        this.tilesRowCount += 5;
        this.rectSize = this.width / this.tilesRowCount;

        this.tiles = this.levelGenerator.generateLevel(this.level);
        this.player.setRect(this.levelGenerator.getStart().getRect());
    }

    public void update() {
        if(regenerate) {
            this.regenerateLevelTiles();
            regenerate = false;
        }
        this.player.update();
    }

    public void setRegenerate(boolean regenerate) {
        this.regenerate = regenerate;
    }

    public Tile[] getTiles() {
        return this.tiles;
    }
}
