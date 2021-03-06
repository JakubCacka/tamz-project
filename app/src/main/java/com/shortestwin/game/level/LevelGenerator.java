package com.shortestwin.game.level;

import android.graphics.Rect;

import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.level.path.PathFinder;
import com.shortestwin.game.utils.Cell;
import com.shortestwin.game.utils.Direction;
import com.shortestwin.game.utils.DirectionController;

import java.util.ArrayList;
import java.util.Random;

public class LevelGenerator {
    private Level level;
    private PathFinder pathFinder;
    private Tile[] tiles;

    private int start;
    private int end;

    public LevelGenerator( Level level) {
        this.level = level;
        this.pathFinder = new PathFinder(level);
    }

    private void initTiles() {
        int rectX = 0;
        int rectY = level.topOffset;
        int tilesNum = level.tilesRowCount * level.tilesRowCount;
        this.tiles = new Tile[tilesNum];
        for(int i = 0; i < tilesNum; i++) {
            Rect tileRect = new Rect(rectX, rectY, rectX + level.rectSize, rectY + level.rectSize);
            this.tiles[i] = new Tile(false, 1, tileRect);

            rectX += level.rectSize;
            if((i + 1) % level.tilesRowCount == 0) {
                rectX = 0;
                rectY += level.rectSize;
            }
        }
    }

    public Tile[] generateLevel(int blocksGeneration) {
        this.initTiles();
        Cell[] ABCoords = this.setRandomAB();
        this.resetPathFinder();

        Path finalPath = new Path();
        int tries = 0;
        for(int i = 0; i < blocksGeneration; i++) {
            Tile[] tmpLevel = this.copyTiles(this.tiles);
            this.blockPhase(ABCoords);

            finalPath = pathFinder.findShortestPath(ABCoords[0], ABCoords[1]);
            if(!finalPath.pathHasGoal()) {
                this.tiles = copyTiles(tmpLevel);
                this.resetPathFinder();
                i--;
                tries++;
            } else {
                tries = 0;
            }

            if(tries >= 20) {
                break;
            }
        }

        return this.tiles;
    }

    private Cell[] setRandomAB() {
        Cell[] output = new Cell[2];

        Random random = new Random();

        int blockRange = this.level.tilesRowCount;
        int start = this.level.tilesRowCount * 2;
        int startTile = random.ints(start + 2, start + blockRange - 2).findFirst().getAsInt();

        int endStart = this.tiles.length - this.level.tilesRowCount * 3;
        int endTile = 0;
        while(true) {
            endTile = random.ints(endStart + 2, endStart + blockRange - 2).findFirst().getAsInt();

            if(endTile < this.tiles.length) break;
        }

        this.tiles[startTile].setStart(true);
        this.start = startTile;

        this.tiles[endTile].setGoal(true);
        this.end = endTile;

        this.setBlocksAround(Cell.getCellCoord(startTile, this.level.tilesRowCount), 1);
        this.setBlocksAround(Cell.getCellCoord(endTile, this.level.tilesRowCount), 1);

        output[0] = Cell.getCellCoord(startTile, this.level.tilesRowCount);
        output[1] = Cell.getCellCoord(endTile, this.level.tilesRowCount);
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
                    int arrayCoord = Cell.getArrayCoord(neighborCell, this.level.tilesRowCount);

                    if(arrayCoord < this.tiles.length) {
                        this.tiles[arrayCoord].setSolid(true);
                    }
                }
            }

            counter += 1;
        }
    }

    private void blockPhase(Cell[] ABCoords) {
        Path path = pathFinder.findShortestPath(ABCoords[0], ABCoords[1]);

        Random random = new Random();


        for(int i = 0; i < path.getPathNodes().size(); i++) {
            if(i > 10 && i < path.getPathNodes().size() - 10) {
                Cell cell = path.getPathNodes().get(i);
                if (random.ints(0, 10).findFirst().getAsInt() == 1) {
                    int remove = random.ints(0, Direction.values().length - 1).findFirst().getAsInt();
                    this.setBlocksAround(cell, remove);
                }
            }
        }

        this.resetPathFinder();
    }

    private void resetPathFinder() {
        this.pathFinder.reset();
        for(int i = 0; i < this.tiles.length; i++) {
            this.pathFinder.add(this.tiles[i], Cell.getCellCoord(i, this.level.tilesRowCount));
        }
    }

    private Tile[] copyTiles(Tile[] origin) {
        Tile[] output = new Tile[origin.length];

        for(int i = 0; i < origin.length; i++) {
            try {
                output[i] = (Tile) origin[i].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    public Path findShortestPath(Cell startCell, Cell endCell) {
        return this.pathFinder.findShortestPath(startCell, endCell);
    }

    public Tile getStart() {
        return this.tiles[start];
    }

    public Cell getStartCell() {
        return Cell.getCellCoord(start, level.tilesRowCount);
    }

    public Cell getEndCell() {
        return Cell.getCellCoord(end, level.tilesRowCount);
    }
}
