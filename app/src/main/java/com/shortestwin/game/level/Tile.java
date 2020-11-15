package com.shortestwin.game.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {
    private boolean isSolid;
    private boolean isGoal;
    private boolean hasPlayer;
    private boolean playerSelected;
    private int price;

    private Rect rect;

    public Tile(boolean isBlock, int price, Rect rect) {
        this.isSolid = isBlock;
        this.price = price;
        this.rect = rect;

        this.hasPlayer = false;
        this.isGoal = false;
        this.playerSelected = false;
    }

    public void draw(Canvas canvas, Paint paint) {
        if(this.isSolid) {
            paint.setColor(Color.GRAY);
        } else if(this.hasPlayer) {
            paint.setColor(Color.WHITE);
        } else if(this.playerSelected) {
            paint.setColor(Color.GREEN);
        } else if(this.isGoal) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.BLUE);
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

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public void setPlayerSelected(boolean playerSelected) {
        this.playerSelected = playerSelected;
    }
}
