/*
 * Sinry Dong
 * ICS4U
 * Due: June 17, 2021
 * Displays balance of the user's wallet and provides display-only buttons
 * to link to a bank account, card, or third party
 * Resources: Google classroom file input/output resources
 * https://www.oreilly.com/library/view/java-awt-reference/9781565922402/06_chapter-03.html
 * https://farenda.com/java/java-format-double-2-decimal-places/
 * https://www.java-examples.com/create-custom-color-using-rgb-example 
 * https://www.rapidtables.com/web/color/RGB_Color.html
 */
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class Wallet extends JPanel{
	
	//***fields***
	private double expenses; 
	private double earnings; 
	private double balance; 
	final int PIXEL_PER_CHAR = 6;
	
	//***constructor***
	public Wallet() {
		setBackground(Color.WHITE);
	}
	
	/**
	 * Redefines JPanel's paintComponent to draw this display panel
	 * Includes balance and "buttons" 
	 */
	 public void paintComponent(Graphics g) {
	   //Set up initial dimensions
	   super.paintComponent(g);
	   int w = getWidth();
	   int h = getHeight();
	   int x = w/2; 
	   int y = h/4; 
	   
	   //Displays date
	   LocalDate currentDate = LocalDate.now();
	   String date = currentDate.getMonth()+"/"+currentDate.getDayOfMonth()+"/"+currentDate.getYear();
	   g.drawString(date, x - (date.length()/2*PIXEL_PER_CHAR), h/20);
	   
	   //Find balance and display as title
	   calculateExpenses();
	   calculateEarnings();
	   calculateBalance(); 
	   //Format to 2 decimal places and combine with title
	   String title = "BALANCE: $" + String.format("%.2f", balance); 
	   g.setFont(new Font("Charlesworth", Font.BOLD, 25)); //set font
	   FontMetrics fm = g.getFontMetrics(); 
	   g.drawString(title, x - (fm.stringWidth(title)/2), y); //draw text
	   y += fm.getHeight(); 
	   
	   //Set up new colour for "buttons" and font for labels
	   g.setFont(new Font("Charlesworth", Font.PLAIN, 10));
	   Color c = new Color(255, 51, 51);
	   g.setColor(c);
	   
	   //First "button"
	   x = w/2 - 35; 
	   y = h/2-35; 
	   g.fillRect(x, y, 70, 30); //draw rectangle for "button"
	   g.setColor(Color.black);
	   g.drawRect(x, y, 70, 30); //draw border
	   g.setColor(Color.white);
	   g.drawString("Bank Account", x+2, y+18); //add text to rectangle
	      
	   //Second "button"
	   g.setColor(c); 
	   y = h/2 + 10; 
	   g.fillRect(x, y, 70, 30);
	   g.setColor(Color.black);
	   g.drawRect(x, y, 70, 30);
	   g.setColor(Color.white);
	   g.drawString("Credit Card", x+8, y+18);
	   
	   //Third "button" 
	   g.setColor(c); 
	   y = h/2 + 55; 
	   g.fillRect(x, y, 70, 30);
	   g.setColor(Color.black);
	   g.drawRect(x, y, 70, 30);
	   g.setColor(Color.white);
	   g.drawString("Third Party", x+8, y+18);
	 }
	
	 /**
	  * Calculates current balance by subtracting expenses from earnings
	  */
	 private void calculateBalance() {
		 balance = earnings - expenses; 
		 
	 }
	 
	/**
	 * Reads expenses file and sums up total expenses
	 */
	private void calculateExpenses() {
		//Sets up scanner for expenses file
		LocalDate currentDate = LocalDate.now(); 
		String exp = currentDate.getMonth()+"_"+currentDate.getYear()+"_User_Expenses.txt";
		File file = new File(exp); 
		Scanner input = null; 
		try {
			input = new Scanner(file); 
		} catch (FileNotFoundException e){
		}
		
		//Calculates total expenses
		expenses = 0; 
		while(input.hasNextLine()) {
			expenses += Double.parseDouble(input.nextLine().substring(1)); 
		} 
	}
	
	/**
	 * Reads earnings file and sums up total earnings
	 */
	private void calculateEarnings() {
		//Sets up scanner for earnings file
		LocalDate currentDate = LocalDate.now(); 
		String earn = currentDate.getMonth()+"_"+currentDate.getYear()+"_User_Earnings.txt";
		File file = new File(earn); 
		Scanner input = null; 
		try {
			input = new Scanner(file); 
		} catch (FileNotFoundException e){
		}
		
		//Calculates total earnings
		earnings = 0;
		while(input.hasNextLine()) {
			earnings += Double.parseDouble(input.nextLine()); 
		}
	}

}
