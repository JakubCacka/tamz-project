package com.shortestwin.game.utils;

/**
 *  Class representing logical coordinates.
 * */
public class Cell {

    /**
     *  Column index of the cell. (Can be interpreted as x in px * tile width)
     * */
    private int col;

    /**
     *  Row index of the cell. (Can be interpreted as y in px * tile height)
     * */
    private int row;

    /**
     *  Class constructor.
     *
     * @param col --- Column index of the cell.
     * @param row --- Row index of the cell.
     * */
    public Cell(int col, int row) {
        this.col = col;
        this.row = row;
    }

    /**
     *  This method returns column index of the cell.
     *
     * @return Column index of the cell.
     * */
    public int getCol() {
        return this.col;
    }

    /**
     *  This method returns row index of the cell.
     *
     * @return Row index of the cell.
     * */
    public int getRow() {
        return this.row;
    }

    /**
     *  Method for summing indexes of this and another cell.
     *
     * @param cell --- Cell that should be summed with this one.
     *
     * @return New instance of the Cell after the sum.
     * */
    public Cell sumCells(Cell cell) {
        return new Cell( this.col + cell.getCol(), this.row + cell.getRow());
    }

    /**
     *  This method deducts two cells. Input is going go be subtracted from actual instance.
     *
     * @param cell --- Cell used for subtraction.
     *
     * @return New instance of the Cell.
     * */
    public Cell sumCellsNegative(Cell cell) {
        return new Cell( this.col - cell.getCol(), this.row - cell.getRow());
    }

    /**
     *  This static method compares two Cells.
     *
     * @param c1 --- First Cell to be compared.
     * @param c2 --- Second cell to be compared.
     *
     * @return Returns true, if row and column of the input Cells have same value.
     * */
    public static boolean compareCells(Cell c1, Cell c2) {
        return c1.getCol() == c2.getCol() && c1.getRow() == c2.getRow();
    }

    /**
     *  This static method checks if the Cell contains any negative value.
     *
     * @param cell --- Cell to be checked.
     *
     * @return True if Cell has any negative value. False, if it has not.
     * */
    public static boolean hasNegative(Cell cell) {
        return cell.getRow() < 0 || cell.getCol() < 0;
    }

    /**
     *  This static method makes an unique int representation for the input Cell.
     *
     * @param cell --- Cell to be hashed.
     *
     * @return Unique hash for the Cell.
     * */
    public static Integer hash(Cell cell) {
        return (cell.getCol() * 0x1f1f1f1f) ^ cell.getRow();
    }
}
