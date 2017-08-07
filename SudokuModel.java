package csc143.sudoku;

/**
 * This class is a subclass of SudokuCore
 * SudokuCore stores the basic values for elements of grid
 * SudokuModel provides methods to get the state of a row, column 
 * or region on the sudoku board 
 * 
 * @author Amir Ali
 * @version HW3 July2016: Standard Edition: SudokuModel
 *
 */
public class SudokuModel extends SudokuCore {
	
    /**
     * Constructor takes the layout dimensions of the sudoku board and passes
     * the dimensions to the super class constructor to make the board
     * 
     * @param r Rows of the sudoku board
     * @param c Columns of the sudoku board
     */
    public SudokuModel(int r, int c) {
        super(r, c);
    }//end constructor 
    
    /**
     * Method returns the state of a valid row in the sudoku board 
     * Invalid value (negative or greater than size) returns ERROR
     * Precondition: The row n is on the sudoku board
     * 
     * @return State The state of the row {COMPLETE, INCOMPLETE, ERROR} 
     * @param n The row of which the state is inquired about
     */
    public State getRowState(int n) { 
    	if( n <0 || n > getSize()) throw new java.lang.IllegalArgumentException();
    	//array to store all value in row n
    	int[] rowValues =  new int[getSize()];
    	//array to keep track of each number occurrence including blank which is 0
    	int[] counter = new int[getSize() +1 ];
    	//while storing value in array check for error
    	for(int col = 0; col < getSize(); col++){
    		if(getValue(n,col) < 0 || getValue(n,col) > getSize()){
    			return State.ERROR;	
    		}else{
    		//store value in array	
    		rowValues[col] = getValue(n,col);
    		//increment counter array for each value
    		counter[getValue(n,col)]++;
    		}
    	} 
        return getState(rowValues, counter); 
    }//end method getRowState
    
    /**
     * Method returns the state of a valid column of the sudoku board 
     * Invalid value (negative or greater than size) returns ERROR
     * Precondition: The column n is on the sudoku board
     * 
     * @return State The state of the column {COMPLETE, INCOMPLETE, ERROR} 
     * @param n The column of which the state is inquired about 
     */  
    public State getColumnState(int n) { 
    	if( n <0 || n > getSize()) throw new java.lang.IllegalArgumentException();
    	//array to store all value in column n
    	int[] colValues =  new int[getSize()];
    	//array to keep track of each number occurrence including blank which is 0
    	int[] counter = new int[getSize() +1 ];
    	//while storing value in array check for error
    	for(int row = 0; row < getSize(); row++){
    		if(getValue(row,n) < 0 || getValue(row,n) > getSize()){
    			return State.ERROR;	
    		}else{
    		//store value in array	
    		colValues[row] = getValue(row,n);
    		//increment counter array for each value
    		counter[getValue(row,n)]++;

    		
    		}
    	}
        return getState(colValues, counter); 
    }//end method getColumnState
    
    /**
     * Method returns the state of a valid region on the sudoku board 
     * Invalid value (negative or greater than size) returns ERROR
     * Precondition: The region n is on the sudoku board
     * 
     * @return State The state of the column {COMPLETE, INCOMPLETE, ERROR} 
     * @param n The region of the board of which the state is inquired about
     */
    public State getRegionState(int n) {
    	if( n <0 || n > getSize()) throw new java.lang.IllegalArgumentException();
    	//array to store all value in region n
    	int[] regionValues =  new int[getSize()];
    	
    	
    	
    	//array to keep track of each number occurrence including blank which is 0
    	int[] counter = new int[getSize() +1 ];		
    	//adjusted row and column values for region
    	int aRow = getRows() * (n / getRows());
    	int aCol = getColumns() * (n % getRows());  	
    	//while storing value in array check for error
    	for(int row = 0; row < getRows(); row++){
    		for(int col = 0; col < getColumns(); col++){
    			if(getValue(aRow,aCol) < 0 || getValue(aRow,aCol) > getSize()){
    				return State.ERROR;	
    			}else{
    				//store value in array	
    				regionValues[(row*getRows()+ col)] = getValue(aRow + row, aCol + col);
    				counter[getValue(aRow + row ,aCol + col)]++;
    			}
    		}	
    	} 
        return getState(regionValues, counter); 
    }//end method getRegionState
    
    /**
     * Method returns the state of a give row ,column or region by
     * comparing the values in that section with the count for each number  occurrence  
     * 
     * @param values Array of integers values of the given row,column or region
     * @param counter Array of integers containing the count of each number 
     * @return State Returns the state of the sudoku board section (row,column,region) {COMPLETE, INCOMPLETE, ERROR} 
     */
    public State getState(int[] values, int[] counter){
   		//error if (region contains duplicate of any value 1 to size)   	
    	for(int i = 1; i <= getSize(); i++){
    		if(counter[i] > 1 ) return State.ERROR; 
    	}  
		//incomplete if there are blanks, 0 , but no error   	
    	if(counter[0] > 0) return State.INCOMPLETE;	 	
    	//At this point all values are within (1 to size) and 
    	//each value has exactly one occurrence and no blanks
        return State.COMPLETE;    	  	
    }//end method getState
    
}//end class SudokuModel
