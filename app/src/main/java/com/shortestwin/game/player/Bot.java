package com.shortestwin.game.player;

import com.shortestwin.game.level.path.Path;

public class Bot extends APlayer {

    private Path fullPath;
    private boolean move;

    public Bot(String name, int color) {
        super(name, color);

        this.move = false;
    }

    @Override
    public void update() {
        if(move) {
            // Next node - current = direction
            // call move func
        }
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setFullPath(Path fullPath) {
        this.fullPath = fullPath;
    }
}
