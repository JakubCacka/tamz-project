package com.shortestwin.game.level;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.shortestwin.R;
import com.shortestwin.game.GameView;
import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.player.APlayer;
import com.shortestwin.game.player.Bot;
import com.shortestwin.game.player.Player;
import com.shortestwin.game.level.path.PathFinder;
import com.shortestwin.game.utils.Cell;
import com.shortestwin.game.utils.Direction;

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

    private boolean isEnd;
    private boolean playerWin;
    private boolean botWin;
    private Bitmap winSign;
    private Bitmap defeatSign;

    public Level(GameView game) {
        this.winSign = BitmapFactory.decodeResource(game.getResources(), R.drawable.sign_victory);
        this.defeatSign = BitmapFactory.decodeResource(game.getResources(), R.drawable.sign_defeat);

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
            this.tiles[i].draw(canvas, paint, this.game.textures);
        }

        if(this.isEnd) {
            APlayer winner;
            if(this.playerWin) {
                winner = player;
            } else {
                winner = bot;
            }

            Path winPath = winner.getPath();
            paint.setColor(winner.getColor());

            winPath.draw(canvas, paint, this.tiles, this.tilesRowCount);

            Bitmap titleSign;
            if(winner.isPlayable()) {
                titleSign = this.winSign;
            } else {
                titleSign = this.defeatSign;
            }

            int x = this.width / 2 - titleSign.getWidth() / 2;
            int y = this.topOffset / 2 - titleSign.getHeight() / 2;

            canvas.drawBitmap(titleSign, null, new Rect(
                    x, y, x + titleSign.getWidth(), y + titleSign.getHeight()),null);
            y += titleSign.getHeight() + 50;
            x = this.width / 2 - 240;
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("Click screen for next level.", x, y, paint);
        } else {
            this.bot.draw(canvas, paint, this);
            this.player.draw(canvas, paint, this);
        }
    }

    public void update() {
        if(this.regenerate) {
            if(this.isEnd) {
                this.regenerateLevelTiles();
            }

            regenerate = false;
        }

        this.bot.update(this);
        this.player.update(this);

        this.checkEnd();
    }

    public void generateLevel() {
        this.tiles = this.levelGenerator.generateLevel(this.level);
        Rect startRect = this.levelGenerator.getStart().getRect();
        Cell startCell = this.levelGenerator.getStartCell();
        Cell endCell = this.levelGenerator.getEndCell();

        this.player.setRect(startRect);
        this.player.setPosition(startCell);
        this.player.resetPath();

        Path botFullPath = this.levelGenerator.findShortestPath(startCell, endCell);
        this.bot.setRect(startRect);
        this.bot.setPosition(startCell);
        this.bot.setFullPath(botFullPath);
        this.bot.resetPath();

        this.isEnd = false;
        this.playerWin = false;
        this.botWin = false;
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

    public void setPlayerMoveDir(Direction direction) {
        if(!this.isEnd) {
            this.player.setMoveDir(direction);
            this.bot.setMove(true);
        }
    }

    private boolean checkEnd() {
        this.playerWin = Cell.compareCells(levelGenerator.getEndCell(), player.getPosition());
        this.botWin = Cell.compareCells(levelGenerator.getEndCell(), bot.getPosition());
        this.isEnd = (playerWin || botWin);

        return this.isEnd;
    }
}
