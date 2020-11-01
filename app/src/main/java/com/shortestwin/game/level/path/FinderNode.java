package com.shortestwin.game.level.path;

import com.shortestwin.game.utils.Cell;

/**
 *  Representation of the node with some score value in PathFinder.
 *
 * @see PathFinder
 * */
public class FinderNode {
    /**
     *  Cell where the node is situated.
     * */
    protected Cell cell;

    /**
     *  Score value of the node.
     * */
    private Integer value;

    /**
     *  Class constructor.
     * */
    public FinderNode(Cell cell, Integer value) {
        this.cell = cell;
        this.value = value;
    }

    /**
     *  Cell getter.
     *
     * @return Returns the cell of the node.
     * */
    public Cell getCell() {
        return cell;
    }

    /**
     *  Value getter.
     *
     * @return Returns value of the node.
     * */
    public Integer getValue() {
        return value;
    }

    /**
     *  Equals method override for .contains methods.
     *
     * @see PathFinder#findShortestPath(Cell, Cell)
     * */
    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof FinderNode){
            FinderNode ptr = (FinderNode) v;
            retVal = Cell.compareCells(this.cell, ptr.getCell());
        }

        return retVal;
    }

    /**
     *  hashCode method override for .contains methods.
     *
     * @see PathFinder#findShortestPath(Cell, Cell)
     * */
    @Override
    public int hashCode() {
        return Cell.hash(this.cell);
    }
}
