package com.shortestwin.game.level.path;

import com.shortestwin.game.level.Level;
import com.shortestwin.game.level.Tile;
import com.shortestwin.game.utils.Cell;
import com.shortestwin.game.utils.Direction;
import com.shortestwin.game.utils.DirectionController;

import java.util.*;

/**
 * Class for finding some paths.
 */
public class PathFinder {

    /**
     * All available nodes in the level. Hash from tile cell has tile.
     */
    private HashMap<Integer, Tile> availableNodes;

    /**
     * All available nodes in the level. Hash from tile cell has its cell representation.
     */
    private HashMap<Integer, Cell> nodesCells;

    private Level level;

    /**
     * Class constructor. Initialize HashMaps.
     * @param level
     */
    public PathFinder(Level level) {
        this.level = level;
        this.availableNodes = new HashMap<>();
        this.nodesCells = new HashMap<>();
    }

    /**
     * Adds the tiles and their cells to the nodes HashMaps.
     */
    public void add(Tile tile, Cell cell) {
        this.availableNodes.put(Cell.hash(cell), tile);
        this.nodesCells.put(Cell.hash(cell), cell);
    }

    public void reset() {
        this.availableNodes = new HashMap<>();
        this.nodesCells = new HashMap<>();
    }

    /**
     * This method will try to find shortest path between two cells.
     * It is using a A* algorithm with euclidean estimate.
     *
     * @return Returns path to the goal or empty path.
     * @see PathFinder#heuristicCostEstimate(Cell, Cell, String)
     * @see PathFinder#euclideanEstimate(Cell, Cell)
     * <p>
     * It is using custom comparator for the PriorityQueue,
     * because we want to have cells with lowest value on the top and we are using custom nodes.
     * @see PathFinder#nodeValueComparator
     * @see FinderNode
     * <p>
     * Great tutorial:
     * https://www.youtube.com/watch?v=FsParg61xGw&t=1089s
     * https://github.com/anneomcl/PathfindingBasics/blob/master/Assets/Agent.cs
     * <p>
     * Also using:
     * @see Cell
     * @see Path
     */
    public Path findShortestPath(Cell from, Cell to) {
        Path path = new Path();

        if (Cell.compareCells(from, to)) return path;

        path.addGoalNode(to);

        Integer fromHash = Cell.hash(from);

        // Represents h(x) or the score from whatever heuristic we're using
        HashMap<Integer, Integer> heuristicScore = new HashMap<>();

        // Represents g(x) or the distance from start to node "x" (Same meaning as in Dijkstra's "distances")
        HashMap<Integer, Integer> distanceFromStart = new HashMap<>();

        heuristicScore.put(fromHash, heuristicCostEstimate(from, to, "euclidean"));
        distanceFromStart.put(fromHash, 0);

        ArrayList<Integer> neighborsHashes = getNeighbours(from);
        for (Integer neighbourHash : neighborsHashes) {
            heuristicScore.put(neighbourHash, Integer.MAX_VALUE);
            distanceFromStart.put(neighbourHash, Integer.MAX_VALUE);
        }

        // The item dequeued from a priority queue will always be the one with the lowest int value
        //    In this case we will input nodes with their calculated distances from the start g(x),
        //    so we will always take the node with the lowest distance from the queue.
        PriorityQueue<FinderNode> priorityQueue = new PriorityQueue<>(nodeValueComparator);
        priorityQueue.add(new FinderNode(from, heuristicScore.get(fromHash)));
        while (priorityQueue.size() > 0) {

            // Get the node with the least distance from the start
            Cell curr = priorityQueue.poll().getCell();

            // If our current node is the goal then stop
            if (Cell.compareCells(curr, to)) {
                path.addNode(curr);
                return path;
            }

            neighborsHashes = getNeighbours(curr);
            for (Integer neighbourHash : neighborsHashes) {
                if (!heuristicScore.containsKey(neighbourHash)) heuristicScore.put(neighbourHash, Integer.MAX_VALUE);
                if (!distanceFromStart.containsKey(neighbourHash))
                    distanceFromStart.put(neighbourHash, Integer.MAX_VALUE);
            }

            for (Integer neighbourHash : neighborsHashes) {
                // Get the distance so far, add it to the distance to the neighbor
                Integer currScore = distanceFromStart.get(Cell.hash(curr)) + 1; // TODO Tile difficulty instead of int 1

                // If our distance to this neighbor is LESS than another calculated shortest path
                //    to this neighbor, set a new node parent and update the scores as our current
                //    best for the path so far.
                if (currScore < distanceFromStart.get(neighbourHash)) {
                    path.addNode(curr);
                    distanceFromStart.replace(neighbourHash, currScore);

                    int hScore = distanceFromStart.get(neighbourHash) + heuristicCostEstimate(this.nodesCells.get(neighbourHash), to, "euclidean");
                    heuristicScore.replace(neighbourHash, hScore);

                    // If this node isn't already in the queue, make sure to add it. Since the
                    //    algorithm is always looking for the smallest distance, any existing entry
                    //    would have a higher priority anyway.
                    if (!priorityQueue.contains(this.nodesCells.get(neighbourHash))) {
                        priorityQueue.add(new FinderNode(this.nodesCells.get(neighbourHash), hScore));
                    }
                }
            }
        }

        return path;
    }

    /**
     * Custom comparator for the PriorityQueue used in A* algorithm.
     *
     * @see FinderNode
     * @see PathFinder#findShortestPath(Cell, Cell)
     */
    Comparator<FinderNode> nodeValueComparator = new Comparator<FinderNode>() {
        @Override
        public int compare(FinderNode n1, FinderNode n2) {
            return n1.getValue() - n2.getValue();
        }
    };

    /**
     * Euclidean estimate for the A* algorithm.
     * Math example:
     * sqrt((x1 - x2)^2 + (y1 - y2)^2)
     */
    private int euclideanEstimate(Cell from, Cell to) {
        return (int) Math.sqrt(Math.pow(from.getCol() - to.getCol(), 2) +
                Math.pow(from.getRow() - to.getRow(), 2));
    }

    /**
     * Manhattan estimate for the A* algorithm.
     * Math example:
     * |x1 - x2| + |y1 - y2|
     */
    private int manhattanEstimate(Cell from, Cell to) {
        return (Math.abs(from.getCol() - to.getCol()) +
                Math.abs(from.getRow() - to.getRow()));
    }

    /**
     * This method chooses estimate based on input method name and return its outcome.
     *
     * @see PathFinder#euclideanEstimate(Cell, Cell)
     * @see PathFinder#manhattanEstimate(Cell, Cell)
     */
    private int heuristicCostEstimate(Cell from, Cell to, String method) {
        switch (method) {
            case "euclidean":
                return euclideanEstimate(from, to);
            case "manhattan":
                return manhattanEstimate(from, to);
        }

        return -1;
    }

    /**
     * This method returns arraylist of hashes of neighbours of the input Cell for the A* algorithm.
     *
     * @param curr --- Current cell in algorithm.
     * @see PathFinder#findShortestPath(Cell, Cell)
     */
    private ArrayList<Integer> getNeighbours(Cell curr) {
        ArrayList<Integer> neighbours = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Cell newCell = curr.sumCells(DirectionController.getDirectionCell(dir));

            if(this.availableNodes.containsKey(Cell.hash(newCell))) {
                if (!Cell.hasNegative(newCell) && !this.availableNodes.get(Cell.hash(newCell)).isSolid()) { // TODO and also if neighbour is not barrier
                    if ((newCell.getCol() <= level.width / level.rectSize) && (newCell.getRow() <= level.height / level.rectSize)) {
                        neighbours.add(Cell.hash(newCell));
                    }
                }
            }
        }

        return neighbours;
    }
}
