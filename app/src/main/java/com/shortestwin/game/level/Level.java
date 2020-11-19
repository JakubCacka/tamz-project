package com.shortestwin.game.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.shortestwin.game.GameView;
import com.shortestwin.game.player.Bot;
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
    private Bot bot;

    public Level(GameView game) {
        this.game = game;
        this.player = game.getPlayer();
        this.bot = new Bot("Bot", Color.YELLOW);

        this.level = 1;
        this.tilesRowCount = 10;

        this.width = game.screenWidth;
        this.height = game.screenWidth;
        this.topOffset = game.screenHeight / 2 - this.height / 2;
        this.rectSize = game.screenWidth / this.tilesRowCount;

        this.pathFinder = new PathFinder(this);
        this.levelGenerator = new LevelGenerator(this);

        this.generateLevel();
    }

    public void draw(Canvas canvas, Paint paint) {
        for(int i = 0; i < tilesRowCount * tilesRowCount; i++) {
            this.tiles[i].draw(canvas, paint);
        }

        this.bot.draw(canvas, paint, this);
        this.player.draw(canvas, paint, this);
    }

    public void update() {
        if(regenerate) {
            this.regenerateLevelTiles();
            regenerate = false;
        }
        this.bot.update();
        this.player.update();
    }

    public void generateLevel() {
        this.tiles = this.levelGenerator.generateLevel(this.level);
        Rect startRect = this.levelGenerator.getStart().getRect();
        this.bot.setRect(startRect);
        this.player.setRect(startRect);
    }

    public void regenerateLevelTiles() {
        this.level++;
        this.tilesRowCount += 5;
        this.rectSize = this.width / this.tilesRowCount;

        this.generateLevel();
    }

    public void setRegenerate(boolean regenerate) {
        this.regenerate = regenerate;
    }

    public Tile[] getTiles() {
        return this.tiles;
    }
}
