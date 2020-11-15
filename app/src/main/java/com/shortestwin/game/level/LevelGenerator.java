package com.shortestwin.game.level;

import android.graphics.Rect;

import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.level.path.PathFinder;
import com.shortestwin.game.utils.Cell;
import com.shortestwin.game.utils.Direction;
import com.shortestwin.game.utils.DirectionController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LevelGenerator {
    private Level level;
    private int pathsNum;
    private PathFinder pathFinder;
    private Tile[] tiles;
    private int[] blueprint;


    public LevelGenerator(int pathsNum, Level level, int cols, int rows) {
        this.pathsNum = pathsNum;

        this.level = level;
        this.pathFinder = new PathFinder(level);

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
        }
    }

    public Tile[] getLevel() {
        return this.tiles;
    }

    public Tile[] generateLevel() {
        int[] ABCoords = this.setRandomAB();

        for(int i = 0; i < this.tiles.length; i++) {
            this.pathFinder.add(this.tiles[i], getCellCoord(i));
        }
        Path path = pathFinder.findShortestPath(this.getCellCoord(ABCoords[0]), this.getCellCoord(ABCoords[1]));
        path.removeFirst();
        path.removeLast();

        for(Cell cell : path.getPathNodes()) {
            int coord = this.getArrayCoord(cell);
            this.blueprint[coord] = 1;
            this.tiles[coord].setPlayerSelected(true);
        }

        return this.tiles;
    }

    private int[] setRandomAB() {
        int[] output = new int[2];

        Random random = new Random();

        int blockRange = (this.level.tilesRowCount / 5) * this.level.tilesRowCount;
        int startTile = random.ints(0, blockRange).findFirst().getAsInt();

        int endStart = this.blueprint.length - blockRange;
        int endTile = random.ints(endStart, endStart + blockRange).findFirst().getAsInt();

        this.blueprint[startTile] = -1;
        this.blueprint[endTile] = -2;

        this.tiles[startTile].setHasPlayer(true);
        this.tiles[endTile].setGoal(true);

        this.setBlocksAround(getCellCoord(startTile), 1);
        this.setBlocksAround(getCellCoord(endTile), 1);

        output[0] = startTile;
        output[1] = endTile;
        return output;
    }

    private void setBlocksAround(Cell cell, int removeNum) {
        if(removeNum > Direction.values().length) removeNum = Direction.values().length;
        Random random = new Random();
        ArrayList<Integer> skipDir = new ArrayList<>();

        for(int i = 0; i < removeNum; i++) {
            int randomDir = random.ints(1, Direction.values().length).findFirst().getAsInt();

            if(skipDir.contains(randomDir)) {
                i--;
            } else {
                skipDir.add(randomDir);
            }
        }

        int counter = 1;
        for (Direction dir : Direction.values()) {
            Cell neighborCell = cell.sumCells(DirectionController.getDirectionCell(dir));

            if(skipDir.contains(counter)) {
                counter += 1;
                continue;
            }

            if (!Cell.hasNegative(neighborCell)) {
                if ((neighborCell.getCol() <= level.width / level.rectSize) && (neighborCell.getRow() <= level.height / level.rectSize)) {
                    int arrayCoord = this.getArrayCoord(neighborCell);

                    this.tiles[arrayCoord].setSolid(true);
                    this.blueprint[arrayCoord] = 0;
                }
            }

            counter += 1;
        }
    }

    private int getArrayCoord(Cell cell) {
        return cell.getRow() * this.level.tilesRowCount + cell.getCol();
    }

    private Cell getCellCoord(int arrayCoord) {
        int col = arrayCoord % this.level.tilesRowCount;
        int row = arrayCoord / this.level.tilesRowCount;

        return new Cell(col, row);
    }
}
