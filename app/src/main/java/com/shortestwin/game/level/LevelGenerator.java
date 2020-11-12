package com.shortestwin.game.level;

import android.graphics.Rect;

import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.level.path.PathFinder;
import com.shortestwin.game.utils.Cell;

public class LevelGenerator {
    private int pathsNum;
    private PathFinder pathFinder;
    private Tile[] tiles;


    public LevelGenerator(int pathsNum, Level level) {
        this.pathsNum = pathsNum;

        this.pathFinder = level.pathFinder;

        int rectX = 0;
        int rectY = level.topOffset;
        this.tiles = new Tile[100];
        for(int i = 0; i < 100; i++) {
            Rect tileRect = new Rect(rectX, rectY, rectX + level.rectSize, rectY + level.rectSize);
            this.tiles[i] = new Tile(true, -1, tileRect);

            rectX += level.rectSize;
            int col = (i + 1) % 10;
            int row = (i + 1) / 10;
            if((i + 1) % 10 == 0) {
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
