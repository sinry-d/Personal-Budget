/*
 * Sinry Dong
 * ICS4U
 * Due: June 17 2021
 * Displays summary of current finances for the month
 * Resources: Google classroom file input/output resources
 * https://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
 */

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class History extends JPanel{
	
	//***fields***
	private Scanner input1; 
	private Scanner input2; 
	private String expenses; 
	private String earnings; 
	final int PIXEL_PER_CHAR = 6;
	
	//***constructor***
	public History() {
		setBackground(Color.WHITE);
		expenses = "";
		earnings = ""; 
		
		//Sets up scanner for expenses file
		LocalDate currentDate = LocalDate.now(); 
		String exp = currentDate.getMonth()+"_"+currentDate.getYear()+"_User_Expenses.txt";
		InputStream is = getClass().getResourceAsStream(exp); 
		File file1 = new File(exp); 
		input1 = null; 
		try {
			input1 = new Scanner(file1); 
		} catch (FileNotFoundException e){ 
		}
		
		//Sets up scanner for earnings file
		String earn = currentDate.getMonth()+"_"+currentDate.getYear()+"_User_Earnings.txt";
		File file2 = new File(earn); 
		input2 = null; 
		try {
			input2 = new Scanner(file2); 
		} catch (FileNotFoundException e){
		}
		
	}
	
	/**
	 * Reads expenses file and lists all the expenses recursively
	 * @return String containing all the individual expenses
	 */
	public String listExpenses() {
		if(input1.hasNextLine()) {
			//formats number 
			expenses += "$" + String.format("%.2f", Double.parseDouble(input1.nextLine().substring(1))) + "\n";
			listExpenses(); //recursive call to read next line
		}  
		return expenses; //returns when no more text is below the current line of the scanner
	}
	
	/**
	 * Reads earnings file and lists all the earnings recursively
	 * @return String containing all the individual earnings
	 */
	public String listEarnings() {
		if(input2.hasNextLine()) {
			//formats number
			earnings += "$" + String.format("%.2f", Double.parseDouble(input2.nextLine())) + "\n";
			listEarnings(); //recursive call to read next line
		}  
		return earnings; //returns when no more text is below the current line of the scanner
	}
	
	/**
	 * Redefines JPanel's paintComponent to draw this list
	 */
	 public void paintComponent(Graphics g) {
	   super.paintComponent(g);

	   int w = getWidth();
	   int h = getHeight();
	   //Draws title
	   LocalDate currentDate = LocalDate.now();
	   String title = "HISTORY: "+currentDate.getMonth()+", "+currentDate.getYear();
	   g.drawString(title, w/2 - (title.length()/2*PIXEL_PER_CHAR), h/20);
	   //Draws lists
	   createExpensesList(g, w, h); 
	   createEarningsList(g, w, h);
	    
	 }
	 
	 /**
	  * Draws the expenses list on the left
	  */
	 public void createExpensesList(Graphics g, int w, int h) {
		 //Sets up colour and starting dimensions
		 g.setColor(Color.BLACK); 
		 h = h/8; 
		 w = w/2 - 16*PIXEL_PER_CHAR; 
		 //Draws subtitle
		 g.drawString("EXPENSES", w, h);
		 h += g.getFontMetrics().getHeight(); 
		 //Draws list
		 for (String line : listExpenses().split("\n"))
	            g.drawString(line, w, h += g.getFontMetrics().getHeight());
	 }
	 
	 /**
	  * Draws the earnings list on the right
	  */
	 public void createEarningsList(Graphics g, int w, int h) {
		 //Sets up colour and starting dimensions
		 g.setColor(Color.BLACK); 
		 h = h/8; 
		 w = w/2 + 8*PIXEL_PER_CHAR; 
		 //Draws subtitle
		 g.drawString("EARNINGS", w, h);
		 h += g.getFontMetrics().getHeight(); 
		 //Draws list
		 for (String line : listEarnings().split("\n"))
	            g.drawString(line, w, h += g.getFontMetrics().getHeight());
	 }
	
	

}
