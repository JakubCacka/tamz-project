package com.shortestwin.game.level;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.shortestwin.game.graphics.Textures;

public class Tile implements Cloneable {
    private boolean isSolid;
    private boolean isGoal;
    private boolean isStart;
    private int price;

    private Rect rect;

    public Tile(boolean isBlock, int price, Rect rect) {
        this.isSolid = isBlock;
        this.price = price;
        this.rect = rect;

        this.isStart = false;
        this.isGoal = false;
    }

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
    }

    public void draw(Canvas canvas, Paint paint, Textures textures) {
        if(this.isSolid) {
            paint.setColor(textures.getBlockColor());
        } else if(this.isStart) {
            paint.setColor(Color.WHITE);
        } else if(this.isGoal) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(textures.getBackgroundColor());
        }

        canvas.drawRect(this.rect, paint);
    }

    public boolean isSolid() {
        return this.isSolid;
    }

    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public void setGoal(boolean goal) {
        isGoal = goal;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public Rect getRect() {
        return this.rect;
    }
}
