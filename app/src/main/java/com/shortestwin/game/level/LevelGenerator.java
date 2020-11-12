package com.shortestwin.game.level;

import android.graphics.Rect;

import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.level.path.PathFinder;
import com.shortestwin.game.utils.Cell;

public class LevelGenerator {
    private int pathsNum;
    private PathFinder pathFinder;
    private Tile[] tiles;
    private int[] blueprint;


    public LevelGenerator(int pathsNum, Level level, int cols, int rows) {
        this.pathsNum = pathsNum;

        this.pathFinder = new PathFinder(level);

        int rectX = 0;
        int rectY = level.topOffset;
        int tilesNum = cols * rows;
        this.tiles = new Tile[tilesNum];
        this.blueprint = new int[tilesNum];
        for(int i = 0; i < tilesNum; i++) {
            Rect tileRect = new Rect(rectX, rectY, rectX + level.rectSize, rectY + level.rectSize);
            if(i % 2 == 0) {
                this.tiles[i] = new Tile(true, -1, tileRect);
            } else {
                this.tiles[i] = new Tile(false, -1, tileRect);
            }
            this.blueprint[i] = 0;

            rectX += level.rectSize;
            int col = (i + 1) % cols;
            int row = (i + 1) / rows;
            if((i + 1) % cols == 0) {
                rectX = 0;
                rectY += level.rectSize;
            }

            this.pathFinder.add(this.tiles[i], new Cell(col, row));
        }
    }

    public Tile[] getLevel() {
        return this.tiles;
    }

    public Tile[] generateLevel() {

        Path path = pathFinder.findShortestPath(new Cell(0, 0), new Cell(8, 8));

        return this.tiles;
    }
}
