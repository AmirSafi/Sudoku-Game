package csc143.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class for drawing entire SudokuBoard
 * Board is made up of SudokuCell (Nested Class)
 * Implements SelectedCell , which allows for mouse 
 * and keyboard selection and navigation of cells
 * 
 * @author Amir Ali
 * @version Sudoku Part 1: Sudoku Board
 * @version July 2016
 *
 */
public class SudokuBoard extends JComponent implements SelectedCell {
 //used to store numericFlag state of sudokuView
 private boolean numericFlag;
 //color for symbols and numbers 
 public static final Color darkGreen = new Color(0,128,0);
 //used to store row info
 private int row;
 //used to store column info
 private int column;
 //used to store size of board = row*column
 private int size;
 //stores the color of the square
 private Color cellFill;
 //store if component is gainsboro color 
 private boolean isGB;
    //2D array to store SudokuCells(Nested Class)
 protected SudokuCell[][] sudokuGrid;
 //used to distinguish different regions for coloring 
 private int cRegion = 1;
 //used to index x and y coordinates of top left corner of each region
 //first regions begins at position (2,2)
 private int x = 2;
 private int y = 2;
 //used to index top left corner of each cell
 //offset by one pixel from region boarder , border is 1 pixel wide 
 private int xPos = x+1;
 private int yPos = y+1;
 //stores the sudokuBase that is passed in Constructor
 private SudokuBase sudokuBase;
 /**
  * Main method to test SudokuBoard 
  * 
  * @param args No arguments passed
  */
  public static void main(String[] args) {
         //sets the Frame for the sudoku game
         javax.swing.JFrame win = new javax.swing.JFrame("Sudoku : Test 2x3");
         win.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
         win.add(new SudokuBoard(new SudokuStub(2, 3)));
         win.pack();
         win.setVisible(true);
  }//end main method
 /**
  * Constructor, takes in a SudokuBase, stores the info in SudokuBase
  * by using get methods, sets the entire SudokuBoard by setting 
  * the preferred size, calls method to fill board with squares 
  * 
  * @param b SudokuBase Stores information about the rows,columns and state of the board 
  */
 public SudokuBoard(SudokuBase b) {
  //use SudokuBase get method to get and store row info
  row = b.getRows();
  //get and store column info
  column = b.getColumns();
  //get and store size
  size = b.getSize();
  //store the sudokuBase, 
  sudokuBase = b; 
  //sets the PreferredSize of the entire board 
  //50 pixel squares + 1 pixel between squares on both sides = 52 pixels
  //Total = 52 pixels + 2 pixels for both edges of the board = 52 + 4
  //pixel Dimensions of the board
  int pixelDim = size*52+4;
  setPreferredSize(new Dimension(pixelDim , pixelDim));
  //once preferred size is set, fill the board with squares
  //each square is a component
  fillSudokuBoard(); 
 }//end SudokuBoard constructor
 
 public void numericFlag(boolean a){
  this.numericFlag = a;
 }

 /**
  * Fills the dimensions of the sudoku board with squares that are 50 pixels wide.
  * Also sets the correct color of each cell: Either white or gainsboro
  * depending on which region of the board the square is in.
  * 
  */
 public void fillSudokuBoard(){   
  //Create 2D array to store sudoku squares
  sudokuGrid = new SudokuCell[size][size];
  
  fillRegions();
  //loop over the regions of the board

 }//end method fillSudokuBoard 
 /**
  * Method to fill the regions of the board
  * Fills in the colors 
  */
 public void fillRegions(){
  for(int region = 0; region < size; region++){
   //loop over each cell in region
   for(int c = 0; c < size; c++){
    //set cell colors,
    //create and store Gainsboro color
    //one of web programming named colors
    Color gainsboro = new Color(220, 220, 220);
    //set gray cells to gainsboro color
    if(isGB) cellFill = gainsboro;
    //is not gray set color to white
    else cellFill = Color.white; 
    //create a new SudokuCell 
    SudokuCell cell = new SudokuCell(xPos, yPos);
    //add the cell to the board
    add(cell);
    //translate to from pixels to row/column 
    int row = yPos/52; 
    int col=  xPos/52;
    //add the cell to the two-dimensional array representing the SudokuBoard
    sudokuGrid[row][col] = cell; 
    //increment xPos over by one cell width to next cell
    xPos += 52;
    //if reached x boundary of region reset xPos and increment yPos down
    int xBound = (x + 52 *column);
    if(xPos >= (xBound)){
     //reset x position
     xPos = x + 1;
     //increment y position down one cell
     yPos += 52;
    }
    }//end loop over cells in region  
    //switch colors for new region
    boolean newRegion = (row % 2 == 0 && cRegion == row);
    if(newRegion){
     isGB = !isGB;
     cRegion = 0;
    }
    //switch conditional for colors of regions/cells so alternates every region 
    isGB = !isGB;
    //increment cRegion    
    cRegion++;   
    //index to next region
    x += 52*column;
    //when x is at the end of the board, go down to next region of board below previous regions
    if(x >= 52 *size){
     //reset x
     x = 2;
     y += 52*row;
    }
    yPos = y + 1;
    xPos = x + 1;   
  }//end for loop    
 }//end method fillRegions 
 
 /**
  * Method to return sudoku base which has information 
  * about the board (row,column,state)
  * 
  * @return base 
  */
 SudokuBase getBase() {
  return sudokuBase;
 }//end method getBase
 /**
  * Implementation of method from SelectedCell Interface
  * requests window focus on a square with valid index values
  * 
  * @param row Row index of the square to be selected 
  * @param col Column index of the square to be selected
  */
 public void setSelected(int row, int col){
  //square must be within the sudoku board boundary 
  boolean outOfBound = (row < 0 || column < 0 || row >= size || column >= size);  
  if(outOfBound) System.out.println("out of bounds");
  else{
   //Set instance variable of the selected square to true
   sudokuGrid[row][col].selected = true; 
   //Request window focus on the selected square
   sudokuGrid[row][col].requestFocusInWindow();
  }

  
 }//end method setSelected
 /**
  * Implementation of method from SelectedCell Interface
  * Returns the row index of the selected square
  * 
  * @return Row index of square that is selected
  */
 public int getSelectedRow(){
  int row = 0;
  //iterate over rows and columns to find selected square
  for(int r = 0; r < size; r++){
   for(int c = 0; c < size; c++){
    //update row index
    if(sudokuGrid[r][c].selected) row = r;
   }
  }
  //return row index of selected square
  return row;
 }//end method getSelectedRow
 /**
  * Implementation of method from SelectedCell Interface
  * Used to get the selected column
  * 
  * @return Column Selected column
  */
 public int getSelectedColumn(){
  int column = 0;
  //iterate over rows and columns to find selected cell
  for(int r = 0; r < size; r++){
   for(int c = 0; c < size; c++){
    //update the column index
    if(sudokuGrid[r][c].selected) column = c; 
   }
  }//end for
  //return column index of selected square
  return column;
 }//end method getSelectedColumn
 
 
 /**
  * Nested class used to represent each cell in Sudokuboard 
  * Extends JPanel , each cell is a component 
  * 
  * @author Amir Ali
  * @version Sudoku Board: part 1
  * @version July 2016
  *
  */
 public class SudokuCell extends JPanel implements ActionListener{
  boolean isGiven;
  int value;  
  public Color initialColor;
  boolean selected;
  int row = 0;
  int column = 0;
  /**
   * To be used to get action performed
   * then use switch statement to call appropriate
   * action method
   * 
   */
  @Override
  public void actionPerformed(ActionEvent e){
   String command = e.getActionCommand();
   System.out.println(e);   
  }
  /**
   * Constructs a SudokuCells for SudokuBoard.  
   * Takes x, y coordinates for the upper left corner of the cell.
   * 
   * @param x  x coordinates of upper left of cell
   * @param y  y coordinates of upper left of cell
   */
  public SudokuCell(int x, int y){
   //set location using x and y coordinates
   setLocation(x, y);
   //set border
   setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
   //set fixed size 50X50 pixels
   setSize(50, 50);
   //set background
   setBackground(cellFill);
   mouseListener();
   keyListener();
   focusListener();
   processEvent(null);
   }//end SudokuCell constructor 
  /**
   * Method to check if a row , column index values are within the Sudoku board boundary
   * 
   * @param row Row index of the square
   * @param col Column index of the square
   * @return True if cell is within boundary of the board, false otherwise 
   */
  public boolean outOfBounds(int row, int col){
   //cell must be within the sudoku board boundary 
   return ((row < 0 || column < 0 || row >= size || column >= size));
  }//end method outOfBounds
     /**
      * Calls the UI delegate's paint method
      * 
      * @param g Copy of graphics object 
      */
  public void paintComponent(Graphics g){
   super.paintComponent(g);
   //set appropriate color
   if(isGiven) g.setColor(darkGreen);
   else g.setColor(Color.BLACK); 
         //depending on numeric flag, either draw number or symbol
   if(numericFlag){
   NumericSymbols numbers = new NumericSymbols();
   numbers.drawSymbol(g,5,5,value);
   }
   else{
    PrimarySymbols symbol = new PrimarySymbols();
    symbol.drawSymbol(g,5,5,value); 
   }
  }//end method paintComponent 
  /**
   *Iterate over all the squares in double array
   *to find the selected one and store the row and column information
   *then unselect that cell by changing its instance variable 
   * 
   */
  public void getSelectedSquare(){
   //iterate over height/rows
   for(int r = 0; r < size; r++){
    //iterate over width/columns
    for(int c = 0; c < size; c++){ 
     if(sudokuGrid[r][c].selected){
      //update row and column info
      row = r;
      column = c;
      //Unselect current cell
      sudokuGrid[r][c].selected = false;
     }//end if
    }
   }//end for
  }//end getSelectedSquare 
  /**
   * Method to set window Focus on the selected square
   * Catches out of bounds exception 
   */
  public void requestFocus(){
   try{
   sudokuGrid[row][column].requestFocusInWindow();
   } catch(ArrayIndexOutOfBoundsException e){
   }
  }//end method requestFocus
  /**
   * Makes sound if a move is out of the sudoku board boundary 
   */
  public void beep(){
   //square must be within the sudoku board boundary 
   if(outOfBounds(row,column)) Toolkit.getDefaultToolkit().beep();
  }//end beep
  
  /**
   * Mouse listener 
   * Listener for mouse events/clicks 
   */
  public void mouseListener(){   
     addMouseListener(new MouseAdapter(){
    /**
     * Method to get window focus on sudoku square that is clicked on
     * 
     * @param  MouseEvent e   Event indicates mouse cursor over container and mouse action occurred
     */
    public void mouseClicked(MouseEvent e) {
     requestFocusInWindow();
    }
       });  
  }//end method mouseListener 
  /**
   * Key listener 
   */
  public void keyListener(){
            //listener for keystroke events 
   addKeyListener(new KeyAdapter(){
   /**
    * Method to indicate when a keystroke has been made and 
    * to get the code of the key that has been pressed
    * key code is used to change selected cell: up,down, right, left
    * 
    * @param KeyEvent e An event that indicates a keystroke has been made
    */
   public void keyPressed(KeyEvent e){
    //get code of pressed key and move cells accordingly
    switch(e.getKeyCode()){
    //left
    case 37 : getSelectedSquare();
        //after getting selected square, update index to intended square
              column--;
              //if out of bounds, make beep sound
              beep();
              //request that selected component gets the input focus
              requestFocus();
              //if a move is made, break out of switch statement 
              break;
    //up 
    case 38 : getSelectedSquare();
                    row--;
                    beep();  
                    requestFocus();
                    break;               
    //right          
    case 39 : getSelectedSquare();
                          column++;
                          beep();  
                          requestFocus();
                          break;
                //down          
    case 40 : getSelectedSquare();
                row++;
                beep();  
                requestFocus();
                break;                              
    }//end switch
   }//end keyPressed
    }
     );   
  }//end method keyListener  
  /**
   * Focus listener
   */
  public void focusListener(){
   addFocusListener(new FocusListener() {
   //get and store background color of current component/square
   Color initial = getBackground(); 
   /**
    * Method that indicates a component/square has lost focus
    * Sets the background color to initial color, either white or gainsboro
    * 
    * @param FocusEvent e  Event which indicates that a Component(cell) has gained or lost the input focus.
    */
   public void focusLost(FocusEvent e) {
    setBackground(initial);
   }//end method focusLost
   /**
    * Method that indicates a component/square has gained focus
    * Sets the square which gained focus yellow color
    *
    * @param FocusEvent e  Event which indicates that a Component(cell) has gained or lost the input focus.
    */
   public void focusGained(FocusEvent e) {
    setBackground(java.awt.Color.yellow); 
    selected = true;     
   }// end method focusGained
  });       
  }//end method focusListener 

 }//end class SudokuCell  
}//end class SudokuBoard 