package com.shortestwin.game.player;

import com.shortestwin.game.level.Level;
import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.utils.Cell;
import com.shortestwin.game.utils.DirectionController;


public class Bot extends APlayer {

    private Path fullPath;
    private boolean move;

    public Bot(String name, int color) {
        super(name, color);

        this.move = false;
    }

    @Override
    public void update(Level level) {
        if(move) {
            Cell nextCell = this.fullPath.getFirstNode();
            this.moveDir = DirectionController.getDirectionFromCell(nextCell.sumCellsNegative(this.position));
            this.move(level);

            this.fullPath.removeFirst();
            this.move = false;
        }
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setFullPath(Path fullPath) {
        this.fullPath = fullPath;
        this.fullPath.removeFirst();
    }
}
