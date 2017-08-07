package csc143.sudoku;

/**
 * Support for numeric output for SudokuView, optional.
 */
public interface NumericSupport {
    
    /**
     * Sets the output type: True for numeric output, False
     * for symbol output.
     * @param flag The desired output type
     */
    public void setNumeric(boolean flag);
    
    /**
     * Retrieve the current output type, numeric or graphic
     * @return True if numeric output, False if symbols are output
     */
    public boolean showsNumeric();
    
}
