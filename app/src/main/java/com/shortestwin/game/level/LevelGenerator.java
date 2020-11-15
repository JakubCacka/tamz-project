package com.shortestwin.game.level;

import android.graphics.Rect;

import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.level.path.PathFinder;
import com.shortestwin.game.utils.Cell;

import java.util.Random;

public class LevelGenerator {
    private int pathsNum;
    private PathFinder pathFinder;
    private Tile[] tiles;
    private int[] blueprint;
    private int cols;


    public LevelGenerator(int pathsNum, Level level, int cols, int rows) {
        this.pathsNum = pathsNum;

        this.pathFinder = new PathFinder(level);

        this.cols = cols;

        int rectX = 0;
        int rectY = level.topOffset;
        int tilesNum = cols * rows;
        this.tiles = new Tile[tilesNum];
        this.blueprint = new int[tilesNum];
        for(int i = 0; i < tilesNum; i++) {
            Rect tileRect = new Rect(rectX, rectY, rectX + level.rectSize, rectY + level.rectSize);
            this.tiles[i] = new Tile(false, 1, tileRect);
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
        int[] ABCoords = this.setRandomAB();

        Path path = pathFinder.findShortestPath(this.getCellCoord(ABCoords[0]), this.getCellCoord(ABCoords[1]));
        path.removeFirst();
        path.removeLast();

        for(Cell cell : path.getPathNodes()) {
            int coord = this.getArrayCoord(cell.getCol(), cell.getRow());
            this.blueprint[coord] = 1;
            this.tiles[coord].setPlayerSelected(true);
        }

        return this.tiles;
    }

    private int[] setRandomAB() {
        int[] output = new int[2];

        Random random = new Random();

        int blockRange = (this.cols / 5) * this.cols;
        int startTile = random.ints(0, blockRange).findFirst().getAsInt();

        int endStart = this.blueprint.length - blockRange;
        int endTile = random.ints(endStart, endStart + blockRange).findFirst().getAsInt();

        this.blueprint[startTile] = -1;
        this.blueprint[endTile] = -2;

        this.tiles[startTile].setHasPlayer(true);
        this.tiles[endTile].setGoal(true);

        output[0] = startTile;
        output[1] = endTile;
        return output;
    }

    private int getArrayCoord(int col, int row) {
        return row * this.cols + col;
    }

    private Cell getCellCoord(int arrayCoord) {
        int col = arrayCoord % this.cols;
        int row = arrayCoord / this.cols;

        return new Cell(col, row);
    }
}
