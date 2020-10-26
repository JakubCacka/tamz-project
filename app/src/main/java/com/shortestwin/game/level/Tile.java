package com.shortestwin.game.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {
    private boolean isBlock;
    private int price;

    private Rect rect;

    public Tile(boolean isBlock, int price, Rect rect) {
        this.isBlock = isBlock;
        this.price = price;
        this.rect = rect;
    }

    public void draw(Canvas canvas, Paint paint) {
        if(this.isBlock) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.BLUE);
        }

        canvas.drawRect(this.rect, paint);
    }
}
