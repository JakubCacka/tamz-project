package com.shortestwin.game.utils;

/**
 *  Class with static methods for manipulating with Direction enum.
 *
 * @see Direction
 * */
public class DirectionController {

    /**
     *  This method returns shift coordinates representing given direction in logical.
     *
     * @see Cell#Cell(int, int)
     *
     * @param direction --- Direction in which we want to shift.
     *
     * @return New instance of Cell representing given direction shift in cells.
     * */
    public static Cell getDirectionCell(Direction direction) {
        int col = 0, row = 0;

        if(direction == null) {
            return new Cell(col, row);
        }

        switch(direction) {
            case N: row = -1; break;
            case NE: col = 1; row = -1; break;
            case E: col = 1; break;
            case SE: col = 1; row = 1; break;
            case S: row = 1; break;
            case SW: col = -1; row = 1; break;
            case W: col = -1; break;
            case NW: col = -1; row = -1; break;
            default: break;
        }

        return new Cell(col, row);
    }

    /**
     *  This method returns opposite Direction to given one.
     *  For example for given Direction.W, this method returns Direction.S
     *
     * @param direction --- Given direction
     *
     * @return Opposite Direction for given one.
     * */
    public static Direction getOppositeDirection(Direction direction) {
        Direction output = null;

        switch(direction) {
            case N: output = Direction.S; break;
            case NE: output = Direction.SW; break;
            case E: output = Direction.W; break;
            case SE: output = Direction.NW; break;
            case S: output = Direction.N; break;
            case SW: output = Direction.NE; break;
            case W: output = Direction.E; break;
            case NW: output = Direction.SE; break;
            default: break;
        }

        return output;
    }
}
