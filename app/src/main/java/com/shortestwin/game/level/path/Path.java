package com.shortestwin.game.level.path;

import com.shortestwin.game.utils.Cell;

import java.util.LinkedList;

/**
 *  Path representation as outcome from PathFinder.
 *
 * @see PathFinder
 * */
public class Path {

    /**
     *  List of all cells in path.
     * */
    private LinkedList<Cell> path;

    private Cell goal;

    /**
     *  Class constructor.
     * */
    public Path() {
        this.path = new LinkedList<>();
    }

    /**
     *  Adds cell to the path, if it is not already there.
     *
     * @param node --- Cell to be added.
     * */
    public void addNode(Cell node) {
        if(this.canAdd(node)) {
            this.path.addLast(node);
        }
    }

    /**
     *  Adds cell representing goal.
     *
     * @param goal --- Cell to be added.
     * */
    public void addGoalNode(Cell goal) {
        this.goal = goal;
    }

    /**
     *  @return First cell in the path.
     * */
    public Cell getFirstNode() {
        return this.path.getFirst();
    }

    /**
     *  @return Last cell in the path.
     * */
    public Cell getLastNode() {
        return this.path.getLast();
    }

    /**
     * @return Goal cell of the path.
     * */
    public Cell getGoal() {
        return goal;
    }

    public boolean pathHasGoal() {
        if(this.path.size() <= 0) return false;

        return Cell.compareCells(this.goal, this.getLastNode());
    }

    /**
     *  Removes first node in the path.
     * */
    public void removeFirst() {
        this.path.remove();
    }

    /**
     *  Removes last node in the path.
     * */
    public void removeLast() {
        this.path.removeLast();
    }

    /**
     *  Gets all the cells in the path.
     * */
    public LinkedList<Cell> getPathNodes(){
        return this.path;
    }

    /**
     *  Checks if the cell is already in the path.
     *
     * @param node --- Cell to be added.
     *
     * @return True if it is not and false if it is.
     * */
    public boolean canAdd(Cell node) {

        for(Cell pathNode : this.path) {
            if(Cell.compareCells(pathNode, node)) return false;
        }

        return true;
    }
}
