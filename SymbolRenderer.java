package csc143.sudoku;

public interface SymbolRenderer {
    
    /**
     * Renders a single symbol for the Sudoku game
     * @param x The x-coordinate for the upper-left corner 
     * of the symbol area (40x40 pixels)
     * @param y The y-coordinate for the upper-left corner 
     * of the symbol area (40x40 pixels)
     * @param g The Graphics object used to draw the symbol
     * @param value The value to be drawn, between 0 and 12,
     * inclusive
     */
    public void drawSymbol(java.awt.Graphics g, int x, int y, int value);
    
}
