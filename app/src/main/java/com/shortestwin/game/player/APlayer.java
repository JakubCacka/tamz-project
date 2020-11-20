package com.shortestwin.game.player;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.shortestwin.game.level.Level;
import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.utils.Cell;
import com.shortestwin.game.utils.Direction;
import com.shortestwin.game.utils.DirectionController;
import com.shortestwin.game.utils.Helper;

public abstract class APlayer {
    private String name;
    private int color;

    protected Cell position;
    private Rect rect;

    private Path path;

    protected Direction moveDir;

    protected APlayer(String name, int color) {
        this.name = name;
        this.color = color;
        this.path = new Path();
        this.moveDir = null;
    }

    public void draw(Canvas canvas, Paint paint, Level level) {

        paint.setColor(Helper.manipulateColor(this.color, (float) 0.8));
        for(Cell node : this.path.getPathNodes()) {
            int coord = Cell.getArrayCoord(node, level.tilesRowCount);
            Rect nodeRect = level.getTiles()[coord].getRect();

            canvas.drawRect(nodeRect, paint);
        }

        paint.setColor(this.color);
        canvas.drawRect(this.rect, paint);
    }

    public void update(Level level) {
        if(this.moveDir != null) {
            this.move(level);

            this.moveDir = null;
        }
    }

    protected void move(Level level) {
        Cell dirCell = DirectionController.getDirectionCell(this.moveDir);
        this.position = this.position.sumCells(dirCell);

        int newCoord = Cell.getArrayCoord(this.position, level.tilesRowCount);
        this.rect = level.getTiles()[newCoord].getRect();
    }

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void setMoveDir(Direction moveDir) {
        this.moveDir = moveDir;
    }
}
