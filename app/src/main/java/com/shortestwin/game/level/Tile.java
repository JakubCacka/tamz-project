package com.shortestwin.game.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {
    private boolean isSolid;
    private int price;

    private Rect rect;

    public Tile(boolean isBlock, int price, Rect rect) {
        this.isSolid = isBlock;
        this.price = price;
        this.rect = rect;
    }

    public void draw(Canvas canvas, Paint paint) {
        if(this.isSolid) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.BLUE);
        }

        canvas.drawRect(this.rect, paint);
    }

    public boolean isSolid() {
        return this.isSolid;
    }
}
