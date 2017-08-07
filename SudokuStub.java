package csc143.sudoku;

public class SudokuStub extends SudokuBase {
    
    public SudokuStub(int layoutRows, int layoutColumns) {
        super(layoutRows, layoutColumns);
    }
    
    public int getValue(int row, int col) {
        return 0;
    }
    
    public void setValue(int row, int col, int value) {
    }
    
    public boolean isGiven(int row, int col) {
        return false;
    }
    
    public void fixGivens() {
    }
    
    public State getRowState(int n) { 
        return State.INCOMPLETE; 
    }
    
    public State getColumnState(int n) { 
        return State.COMPLETE; 
    }
    
    public State getRegionState(int n) { 
        return State.ERROR; 
    }
    
}