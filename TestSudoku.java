package csc143.sudoku;

public class TestSudoku {
	public static void main(String[] args){
		  SudokuBase board = new SudokuModel(2, 3);
		  board.setValue(0, 3, 6);
		  board.setValue(0, 5, 1);
		  board.setValue(1, 2, 4);
		  board.setValue(1, 4, 5);
		  board.setValue(1, 5, 3);
		  board.setValue(2, 3, 3);
		  board.setValue(3, 2, 6);
		  board.setValue(4, 0, 2);
		  board.setValue(4, 1, 3);
		  board.setValue(4, 3, 1);
		  board.setValue(5, 0, 6);
		  board.setValue(5, 2, 1);
		  board.fixGivens();
		  
		  SudokuView v1 = new SudokuView(board);
	} 

}
