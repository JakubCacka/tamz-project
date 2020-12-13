package com.shortestwin.game.player.score;

public class LevelStats {
    private Integer id;
    private int level;
    private int moves;

    public LevelStats(int level) {
        this.level = level;
        this.moves = 0;
        this.id = null;
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

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
