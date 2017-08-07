package csc143.sudoku;

public abstract class SudokuCore extends SudokuBase {
    
    private final int[] grid;
    
    private static final int GIVEN_MASK = 0x00000100;
    private static final int GIVEN_UNMASK = ~ GIVEN_MASK;
    
    public SudokuCore(int layoutRows, int layoutColumns) {
        super(layoutRows, layoutColumns);
        int size = getSize();
        grid = new int[size*size];
    }
    
    private int getIndex(int row, int col) {
        if(row < 0 || row >= getSize() || col < 0 || col >= getSize()) {
            String msg = "Error in location";
            throw new IllegalArgumentException(msg);
        }
        return row * getSize() + col;
    }
    
    public final int getValue(int row, int col) {
        return grid[getIndex(row, col)] & GIVEN_UNMASK;
    }
    
    public final void setValue(int row, int col, int value) {
        if(value < 0 || value > getSize()) {
            String msg = "Value out of range: " + value;
            throw new IllegalArgumentException(msg);
        }
        if(isGiven(row, col)) {
            String msg = "Cannot set given location: " + row + ", " + col;
            throw new IllegalStateException(msg);
        }
        grid[getIndex(row, col)] = value;
        setChanged();
        notifyObservers();
    }
    
    public final boolean isGiven(int row, int col) {
        return (grid[getIndex(row, col)] & GIVEN_MASK) == GIVEN_MASK;
    }
    
    public final void fixGivens() {
        for(int i = 0; i < grid.length; i++)
            if(grid[i] != 0) 
            grid[i] |= GIVEN_MASK;
        setChanged();
        notifyObservers();
    }
    
    public String toString() {
        String board = "";
        for(int i = 0; i < getSize(); i ++) {
            for(int j = 0; j < getSize(); j ++)
                board += charFor(i, j) + " ";
            board += "\n";
        }
        return board;
    }
    
    private String charFor(int i, int j) {
        int v = getValue(i, j);
        if(v < 0) {
            return "?";
        } else if(v == 0) {
            return ".";
        } else if(v < 36) {
            return Character.toString(Character.forDigit(v, 36)).toUpperCase();
        } else {
            return "?";
        }
    }
    
    protected void readFromStream(java.io.InputStream is) {
    }
    
    protected void writeToStream(java.io.OutputStream os) {
    }
    
    protected int getRawValue(int row, int col) {
        return grid[getIndex(row, col)];
    }
    
    protected void setRawValue(int row, int col, int value) {
        grid[getIndex(row, col)] = value;
    }
    
}