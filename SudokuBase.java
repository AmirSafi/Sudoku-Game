package csc143.sudoku;

public abstract class SudokuBase extends java.util.Observable {
    
    private final int rows;
    private final int columns;
    private final int size;
    
    public enum State {COMPLETE, INCOMPLETE, ERROR};
    
    public SudokuBase(int layoutRows, int layoutColumns) {
        rows = layoutRows;
        columns = layoutColumns;
        size = columns * rows;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public int getSize() {
        return size;
    }
    
    public abstract int getValue(int row, int col);
    
    public abstract void setValue(int row, int col, int value);
    
    public abstract boolean isGiven(int row, int col);
    
    public abstract void fixGivens();
    
    public abstract State getRowState(int n);
    
    public abstract State getColumnState(int n);
    
    public abstract State getRegionState(int n);
    
}