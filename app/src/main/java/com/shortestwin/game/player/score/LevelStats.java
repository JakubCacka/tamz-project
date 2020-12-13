package com.shortestwin.game.player.score;

public class LevelStats {
    private int level;
    private int moves;

    public LevelStats(int level) {
        this.level = level;
        this.moves = 0;
    }

    public void addMove() {
        this.moves++;
    }

    public int getLevel() {
        return level;
    }

    public int getMoves() {
        return moves;
    }
}
