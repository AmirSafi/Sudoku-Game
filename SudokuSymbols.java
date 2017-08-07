package csc143.sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;



/**Class to implement symbol renderer for numbers*/
public class SudokuSymbols extends JPanel implements SymbolRenderer {
 //the value associated with the symbol
 int value;
 boolean isGivenValue; 
 /**
  * Constructs a Cell and sets the preferred size of the component.
  *  
  */
 public SudokuSymbols(){ 
  setPreferredSize(new Dimension(50, 50));  
 }
 public void setValue(int value){
  this.value = value;
 }
 /**
  * Draws component. Invokes drawSymbol method which determines which
  * symbol to draw on the Symbol component based on its value parameter.
  * 
  * @param g Graphics Allows drawing onto components
  */
 public void paintComponent(Graphics g){ 
  super.paintComponent(g); 
  //set Color of pen stroke
  if(isGivenValue){  
   g.setColor(Color.MAGENTA);  
  }
  else{
   g.setColor(Color.BLACK);
  }
  drawSymbol(g, 5, 5, value); 
 }
 
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
 public void drawSymbol(Graphics g, int x, int y, int value) {
  //font for displaying numbers that fill the sudoku square
  final Font largeFont = new Font ("Courier New", 1, 46);
  final Font mediumFont = new Font ("Courier New", 1, 38);
  //To draw thick lines cast formal parameter g to type Graphics2D
  if(g instanceof Graphics2D){
   Graphics2D g2;
      g2 = (Graphics2D) g;
      //set the stroke thickness
      java.awt.Stroke s = new java.awt.BasicStroke(5);
      g2.setStroke(s);
  } 
  //do nothing for value of 0, default blank square
  if(value == 0){}  
  if(value == 1){
   g.setFont(largeFont);
   g.drawString("1", x+7, y+35);
  }  
  if(value == 2){
   g.setFont(largeFont);
   g.drawString("2", x+7, y+35);
  }  
  if(value == 3){
   g.setFont(largeFont);
   g.drawString("3", x+7, y+35); 
  } 
  if(value == 4){
   g.setFont(largeFont);
   g.drawString("4", x+7, y+35); 
  }  
  if(value == 5){
   g.setFont(largeFont);
   g.drawString("5", x+7, y+35);
  }  
  if(value == 6){
   g.setFont(largeFont);
   g.drawString("6", x+7, y+35);  
  }  
  if(value == 7){
   g.setFont(largeFont);
   g.drawString("7", x+7, y+35);   
  }  
  if(value == 8){
   g.setFont(largeFont);
   g.drawString("8", x+7, y+35);
  }  
  if(value == 9){
   g.setFont(largeFont);
   g.drawString("9", x+7, y+35);
  }  
  if(value == 10){
   g.setFont(mediumFont);
   g.drawString("10", x, y+35);
  } 
  if(value == 11){
   g.setFont(mediumFont);
   g.drawString("11", x, y+35);
  }
  if(value == 12){
   g.setFont(mediumFont);
   g.drawString("12", x, y+35);
  } 
 }//end method drawSymbol
}
