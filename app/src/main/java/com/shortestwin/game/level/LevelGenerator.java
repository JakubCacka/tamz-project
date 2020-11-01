package com.shortestwin.game.level;

import com.shortestwin.game.level.path.Path;
import com.shortestwin.game.level.path.PathFinder;
import com.shortestwin.game.utils.Cell;

public class LevelGenerator {
    private int pathsNum;
    private int[] levelArr;
    private PathFinder pathFinder;


    public LevelGenerator(int pathsNum, Level level) {
        this.pathsNum = pathsNum;
        this.levelArr = new int[] {
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 2
        };

        this.pathFinder = level.pathFinder;
    }

    public int[] generateLevel() {

        Path path = pathFinder.findShortestPath(new Cell(0, 0), new Cell(8, 8));

        return this.levelArr;
    }
}
