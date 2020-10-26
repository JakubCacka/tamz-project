package com.shortestwin.game.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.shortestwin.game.GameView;

public class Level {
    private GameView game;

    private Tile[] tiles;

    public Level(GameView game) {
        this.game = game;

        int rectSize = game.screenWidth / 10;
        int rectX = 0;
        int rectY = game.screenHeight / 2 - rectSize * 5;

        this.tiles = new Tile[100];
        for(int i = 0; i < 100; i++) {
            Rect tileRect = new Rect(rectX, rectY, rectX + rectSize, rectY + rectSize);
            if(i % 2 == 0) {
                this.tiles[i] = new Tile(false, 1, tileRect);
            } else {
                this.tiles[i] = new Tile(true, -1, tileRect);
            }

            rectX += rectSize;
            if((i + 1) % 10 == 0) {
                rectX = 0;
                rectY += rectSize;
            }
        }
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
