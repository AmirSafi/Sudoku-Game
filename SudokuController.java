package csc143.sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import csc143.sudoku.SudokuBase.State;

/**
 * Class for integration of Sudoku game and controllers
 * The game can be played in either normal mode 
 * or setup mode. 
 * 
 * @author Amir Ali
 * @version HW8:Standard
 *
 */
public class SudokuController implements Observer{
	//the JFrame window that contains all the parts
	private static JFrame win;
	//number or rows
	private int rows;
	//number of columns
	private int columns;
	//for row and column input in setup mode 
	private JTextField rowInput, colInput;
	private SudokuBase base;
	//displays data from the base
	private SudokuView gameView;
	//the base/model in the MVC
	private SudokuBase gameBoard;
	//keeps track of which mode the player is in
	private boolean setupMode;
	
	/**
	 * Constructor starts the game 
	 * in normal play mode
	 */
	public SudokuController(){
		startingBoard();
	}//end constructor 
	
	
	/**
	 * This method is called when the observed object(SudokuBase) is changed
	 * 
	 * @param o An observable object
	 * @param arg Objects passed to method for notifying observers 
	 */
	@Override
	public void update(Observable o, Object arg) {
		displayGame();		
	}
	
	/**
	 * Method to get user input for preferred rows and column numbers
	 */
	public  void boardSizeInput(){
		//updates flag for setupMode
		setupMode = true;
		JFrame inputFrame = new JFrame("Lets Play Sudoku!");
		inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputFrame.setLayout(new GridLayout(3,2));
		JLabel rowText = new JLabel("Enter number of rows");
		rowInput = new JTextField(7);
		JLabel colText = new JLabel("Enter number of columns");
		colInput = new JTextField(7);
	    //add the the parts for input UI
		inputFrame.add(rowText);
		inputFrame.add(rowInput);
		inputFrame.add(colText);
		inputFrame.add(colInput);
		//Button to create the game
		JButton gameButton = new JButton("Create New Board");
		//add action listener to the newGame button 
		gameButton.addActionListener(new ActionListener(){
			//which clicked check for validity of input then create game 
			public void actionPerformed(ActionEvent e){
				try{
				rows = Integer.parseInt(rowInput.getText());
				columns = Integer.parseInt(colInput.getText());
				}catch(NumberFormatException n){			
				}
				if(validRowCol()){
					inputFrame.dispatchEvent(new WindowEvent(inputFrame, WindowEvent.WINDOW_CLOSING));
					base = newBoard(rows,columns);
				}
			}
		});
		//button for canceling game 
		JButton cancelButton = new JButton("cancel");
		//add action listener 
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inputFrame.dispatchEvent(new WindowEvent(inputFrame, WindowEvent.WINDOW_CLOSING));
			}
		});	
		//add buttons to user input display
		inputFrame.add(gameButton);
		inputFrame.add(cancelButton);	
		inputFrame.pack();
		inputFrame.setVisible(true);		
	}//end method boardSizeInput
	
	/**
	 * Method for starting Sudoku game in 
	 * normal play mode
	 * 
	 * @return SudokuBase base which will be used to get and set values from, the controller in the MVC model
	 */
	public  SudokuBase startingBoard() {
		  setupMode = false;
		  win = new JFrame("SUDOKU PLAY MODE");
		  win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		  //default row and column
		  rows = 2;
		  columns = 3;
		  //create the board
		  SudokuBase board = new SudokuModel(rows, columns);
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
		  //fix the given values 
		  board.fixGivens();
		  SudokuView view = new SudokuView(board);
		  view.setSelected(1, 1);
		  //for the symbol rendering , display numbers rather than symbols for the view
		  view.setNumeric(true);
		  //get and save the model
		  gameBoard = board;
		  gameBoard.addObserver(this);
		  base = view.getBase();
		  gameView = view;
		  //add controller button to the bottom of the boarder layout entire display
		  win.add(controllerButtons(),BorderLayout.SOUTH);
		  displayGame();
		  return board;
	}//end method startingBoard
	
	/**
	 * Create a new board and returns the base
	 * that was used to make the board
	 * 
	 * @param r number of rows
	 * @param c number of columns
	 * @return SudokuBase which will be cases to the view 
	 */
	public  SudokuBase newBoard(int r , int c) {
		  win = new JFrame("SUDOKU SETUP-MODE");
		  //make the Background distinct cyan color
		  win.getContentPane().setBackground( Color.cyan);
		  win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		  //make the board
		  SudokuBase board = new SudokuModel(r, c);	  
		  board.fixGivens();
		  SudokuView view = new SudokuView(board);
		  view.setSelected(1, 1);
		  view.setNumeric(true);
		  //get and save the model
		  gameBoard = board;
		  gameBoard.addObserver(this);
		  base = view.getBase();
		  gameView = view;
		  //add controller buttons
		  win.add(controllerButtons(),BorderLayout.SOUTH);
		  displayGame();  
		  return board;
	}//end method newBoard
	
	/**
	 * Method to display the game
	 * adds the Sudoku indicators and game view 
	 */
	public void displayGame(){
		  //add the game view 
		  win.add(gameView,BorderLayout.CENTER);	  
		  //add the game indicator 
		  SudokuIndicator gameIndicator = new SudokuIndicator();
		  win.add(gameIndicator.stateDisplay(),BorderLayout.EAST);
		  win.setVisible(true);
		  win.pack();
	}//end method displayGame
	
	/**
	 * Method to make the controller buttons that allow 
	 * for number input into the sudoku board
	 * 
	 * @return JPanel the panel that contains all the buttons
	 */
	public JPanel controllerButtons(){
		JPanel cButtons = new JPanel();
		//use flow layout
		cButtons.setLayout(new FlowLayout());
		//for the three option buttons
		JPanel options = new JPanel(new GridLayout(3,1));
		if(setupMode){		
		win.setBackground(Color.CYAN);	
	    //add options available in setupMode
		options.add(setGivensButton());
		options.add(newGameButton());
		options.add(cancelButton());
		cButtons.add(options);	
		}//end method controllerButtons
		//else if normal play
		else{
			//add options available for normal play mode
			options.add(newGameButton());
			options.add(setupModeButton());
			options.add(cancelButton());
			cButtons.add(options);			
		}
		//add the buttons for numeric input into the board
		for(int i = 0; i < rows*columns + 1 ; i++){
			cButtons.add(new ControllerButton(i));
		}	
		return cButtons;
	}//end method controllerButtons
	
	/**
	 * Method to make the newGameButton
	 * this button has an added action listener
	 * 
	 * @return JButton returns the new game button
	 */
	public JButton newGameButton(){
		JButton newGameB = new JButton();
		newGameB.setText("New Game");
		//add action listener
		newGameB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//close the current window
				win.dispatchEvent(new WindowEvent(win, WindowEvent.WINDOW_CLOSING));
				//start a new game
				startingBoard();
			}});
		return newGameB;
	}//end method newGameButton
	
	/**
	 * Method to make the cancelButton
	 * this button has an added action listener
	 * 
	 * @return JButton returns the cancel button
	 */
	public JButton cancelButton(){
		JButton cancelB = new JButton();
		cancelB.setText("Cancel");
		//add action listener
		cancelB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//close the current window
				win.dispatchEvent(new WindowEvent(win, WindowEvent.WINDOW_CLOSING));
			}});
		return cancelB;
	}//end method cancelButton
	
	
	/**
	 * Method to make the setupModeButton
	 * this button has an added action listener
	 * 
	 * @return JButton returns the setup mode button
	 */
	public JButton setupModeButton(){
		JButton setupModeB = new JButton();
		setupModeB.setText("Setup-Mode");
		//add action listener
		setupModeB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//close current window
				win.dispatchEvent(new WindowEvent(win, WindowEvent.WINDOW_CLOSING));
				//ask user for row and column input for new board
				boardSizeInput();	
			}});
		return setupModeB;
	}//end method setupModeButton
	
	
	/**
	 * Method to make the setGivensButton
	 * this button has an added action listener
	 * 
	 * @return JButton returns the setGivens button
	 */
	public JButton setGivensButton(){
		JButton setGivensB = new JButton();
		setGivensB.setText("Set Givens");
		//add action listener 
		setGivensB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//fix the givens
				gameBoard.fixGivens();	
			}});
		return setGivensB;
	}//end method setGivensButton
	
	
	/**
	 * Method for checking if the user input for rows and columns is valid
	 * If not valid displays error message 
	 * 
	 * @return boolean returns true if the row and columns are valid 
	 */
	public boolean validRowCol(){
		//rows and columns have to be positive
		if(rows <=0 || columns <= 0){
			JOptionPane.showMessageDialog(null, "ERROR: Row and Column values must be positive integers");
			return false;
	    //the product of rows and columns can't be greater than 12
		}else if(rows*columns >12){
			JOptionPane.showMessageDialog(null, "ERROR: Row times Column value cannot be greater than 12");
			return false;		
		}else if(rows > columns){
			int temp = rows;
			rows = columns;
			columns = temp;	
		}		
			return true;				
	}//end method validRowCol
	
	
	/**
	 * Private helper inner class. Placed here so that it can access the 
	 * instance variables of the Sudoku controller
	 * This class is used to instantiate the buttons for numberic input
	 * Implements mouse listener and gets symbols from SudokuSymbols class 
	 * 
	 * @author Amir Ali
	 * @version HW8:Standard Edition
	 *
	 */
	private class ControllerButton extends SudokuSymbols implements MouseListener{
		
		//color for symbols and numbers light gray
		public final Color lightGray = new Color(211,211,211);
		//color for symbols and numbers cyan 
		public final Color activeColor = new Color(0,255,255);
		//value of the button
		private int bValue;
		
		/**
		 * Constructor for the button
		 * 
		 * @param value the value that the button will take on
		 */
		public ControllerButton(int value){
			super();
			//set the value for symbol renderer to make the correct symbol
			super.setValue(value);
			//store the value in the button
			bValue = value;
			//create a boarder for the button
			setBorder(new LineBorder(Color.BLACK, 2, true));
		    setBackground(lightGray);
		    addMouseListener(this);
		}//end constructor 
		
	    /**
	     * Invoked when the mouse button has been clicked (pressed and released) on a component.
	     * 
	     * @param e the mouse event when the button is clicked
	     */
		@Override
		public void mouseClicked(MouseEvent e) {
			setBackground(activeColor);
			//update the model 
			//when user tries to update a given cell, the system will beep 
			if(base.isGiven(gameView.getSelectedRow(), gameView.getSelectedColumn())){
				Toolkit.getDefaultToolkit().beep();	
			}
			else{
				//if it is not given then set the value
			    base.setValue(gameView.getSelectedRow(), gameView.getSelectedColumn(), bValue);
			}			
		}
		
	    /**
	     * Invoked when the mouse button has been clicked (pressed and released) on a component.
	     * 
	     * @param e the mouse event when the button is clicked
	     */
		@Override
		public void mousePressed(MouseEvent e) {
			setBackground(activeColor);
		}
		
	    /**
	     * Invoked when the mouse button has been released on a component.
	     * 
	     * @param e the mouse event when the button is released
	     */
		@Override
		public void mouseReleased(MouseEvent e) {
			setBackground(lightGray);
		}
		
	    /**
	     * Invoked when the mouse button has entered on a component.
	     * 
	     * @param e the mouse event that occurred
	     */
		@Override
		public void mouseEntered(MouseEvent e) {
			setBackground(Color.WHITE);
		}
		
	    /**
	     * Invoked when the mouse button exited a component.
	     * 
	     * @param e the mouse event the occurred
	     */
		@Override
		public void mouseExited(MouseEvent e) {
			setBackground(lightGray);			
		}		
	}//end private inner class ControllerButton
	
	
	
	
	/**
	 * Private helper inner class that is used to 
	 * make a display that indicates which rows, columns and regions 
	 * are complete, incomplete or error
	 * This class implements observer so that when the sudoku 
	 * base is changed it will update itself as well with the correct 
	 * state information for the rows, columns and regions
	 * 
	 * @author Amir Ali
	 * @version HW8:Standard
	 *
	 */
	private class SudokuIndicator extends JPanel implements Observer{
		
		/**
		 * Constructor for the sudoku indicator 
		 */
		public SudokuIndicator(){
			//register the view 
			gameBoard.addObserver(this);
			//display the all the states
			stateDisplay();	
		}//end constructor
		
		
		/**
		 * This method is called when the observed object(SudokuBase) is changed
		 * 
		 * @param o An observable object
		 * @param arg Objects passed to method for notifying observers 
		 */
		@Override
		public void update(Observable o, Object arg) {
			repaint();	
		}//end method update
		
		
		/**
		 * Method to display the state information
		 * Makes JPanels for each row,column and region indicators 
		 * 
		 * @return JPanel panel that contains each row,column and region indicators 
		 */
		public JPanel stateDisplay(){
			JPanel stateDisplay = new JPanel();
			//gird layout for the three indicators 
			stateDisplay.setLayout(new GridLayout(3,1));
		    stateDisplay.add(rowDisplay());
			stateDisplay.add(columnDisplay());
			stateDisplay.add(regionDisplay());
			return stateDisplay;
		}//end method stateDispaly
		
		/**
		 * Method to display the state information for rows
		 * @return JPanel panel that contains row indicators 
		 */			
		public JPanel rowDisplay(){
			JPanel rowDisplay = new JPanel();
			//make a boarder
			rowDisplay.setBorder(new LineBorder(Color.BLACK, 2, true));
			rowDisplay.setLayout(new GridLayout(rows*columns,1));
			rowDisplay.setPreferredSize(new Dimension(150,150));
			for(int i = 0; i < rows*columns ; i++){
				JPanel rowIndicator = new JPanel();
				//color each row state differently 
				if(base.getRowState(i).equals(State.ERROR)){
					rowIndicator.setBackground(Color.red);
				}else if (base.getRowState(i).equals(State.INCOMPLETE)){
					rowIndicator.setBackground(Color.yellow);
				}else if (base.getRowState(i).equals(State.COMPLETE)){
					rowIndicator.setBackground(Color.GREEN);
				}
				rowDisplay.add(rowIndicator);
			}			
			return rowDisplay;
		}//end method rowDisplay
		
		/**
		 * Method to display the state information for columns
		 * @return JPanel panel that contains column indicators 
		 */	
		public JPanel columnDisplay(){
			JPanel columnDisplay = new JPanel();
			columnDisplay.setBorder(new LineBorder(Color.BLACK, 2, true));
			columnDisplay.setLayout(new GridLayout(1,rows*columns));
			columnDisplay.setPreferredSize(new Dimension(150,150));
				for(int i = 0; i < rows*columns ; i++){
					JPanel columnIndicator = new JPanel();
					//color each column state differently 
					if(base.getColumnState(i).equals(State.ERROR)){
						columnIndicator.setBackground(Color.red);
					}else if (base.getColumnState(i).equals(State.INCOMPLETE)){
						columnIndicator.setBackground(Color.yellow);
					}else if (base.getColumnState(i).equals(State.COMPLETE)){
						columnIndicator.setBackground(Color.GREEN);
					}
					columnDisplay.add(columnIndicator);
				}						
			return columnDisplay;
		}
		
		/**
		 * Method to display the state information for regions
		 * @return JPanel panel that contains region indicators 
		 */	
		public JPanel regionDisplay(){
			JPanel regionDisplay = new JPanel();
			regionDisplay.setBorder(new LineBorder(Color.BLACK, 2, true));
			regionDisplay.setLayout(new GridLayout(columns,rows));
			regionDisplay.setPreferredSize(new Dimension(150,150));
			for(int i = 0; i < rows*columns ; i++){
				JPanel regionIndicator = new JPanel();
				//color each region state differently 
				if(base.getRegionState(i).equals(State.ERROR)){
					regionIndicator.setBackground(Color.red);
				}else if (base.getRegionState(i).equals(State.INCOMPLETE)){
					regionIndicator.setBackground(Color.yellow);
				}else if (base.getRegionState(i).equals(State.COMPLETE)){
					regionIndicator.setBackground(Color.GREEN);
				}
				//add the regions
				regionDisplay.add(regionIndicator);
			}							
			return regionDisplay;
		}//end method regionDisplay		
	}//end private helper inner class SudokuIndicator 

}//end class SudokuController

