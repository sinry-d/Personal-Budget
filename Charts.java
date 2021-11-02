 /* 
 * Sinry Dong
 * ICS4U
 * Due: June 17 2021
 * Generates a pie chart comparing the expenses and earnings of the current month
 * Resources: https://www.tutorialspoint.com/how-to-get-current-day-month-and-year-in-java-8
 * 		      https://zetcode.com/gfx/java2d/textfonts/
 * 			  https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html
 * 			  Ch 6 Poll activity
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.text.*;
import java.time.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Charts extends JPanel {
	
	//***fields***
	private double expensesF, expensesU, expensesE, expensesH, expensesO;
	private double earnings; 
	final int PIXEL_PER_CHAR = 6; 
	private Color color1, color2, color3, color4, color5, color6; 
	
	//***constructor***
	public Charts() {
		color1 = new Color(255, 24, 24); 
		color2 = new Color(247, 78, 107); 
		color3 = new Color(233, 118, 141); 
		color4 = new Color(255, 120, 78); 
		color5 = new Color(183, 31, 31); 
		color6 = new Color(255, 125, 208); 
		setBackground(Color.WHITE);
		
		//Reads expenses file
		LocalDate currentDate = LocalDate.now(); 
		String exp = currentDate.getMonth()+"_"+currentDate.getYear()+"_User_Expenses.txt";
		
		File file1 = new File(exp); 
		Scanner input1 = null; 
		try {
			input1 = new Scanner(file1); 
		} catch (FileNotFoundException e){
			expensesF=0; 
			expensesU=0; 
			expensesE=0; 
			expensesH=0; 
			expensesO=0; 
		}
		while(input1.hasNextLine()) {
			String num = input1.nextLine();
			switch(num.charAt(0)) {
				case 'F': expensesF += Double.parseDouble(num.substring(1)); break; 
				case 'U': expensesU += Double.parseDouble(num.substring(1)); break;
				case 'E' :expensesE += Double.parseDouble(num.substring(1)); break;
				case 'H': expensesH += Double.parseDouble(num.substring(1)); break;
				case 'O': expensesO += Double.parseDouble(num.substring(1)); break;
			} 
		}
		//formats to 2 decimal places
		expensesF = Double.parseDouble(String.format("%.2f", expensesF));
		expensesU = Double.parseDouble(String.format("%.2f", expensesU));
		expensesE = Double.parseDouble(String.format("%.2f", expensesE));
		expensesH = Double.parseDouble(String.format("%.2f", expensesH));
		expensesO = Double.parseDouble(String.format("%.2f", expensesO));
		
		//Reads earnings file
		String earn = currentDate.getMonth()+"_"+currentDate.getYear()+"_User_Earnings.txt";
		File file2 = new File(earn); 
		Scanner input2 = null; 
		try {
			input2 = new Scanner(file2); 
		} catch (FileNotFoundException e){
			earnings=0; 
		}
		while(input2.hasNextLine()) {
			String num = input2.nextLine();
			earnings += Double.parseDouble(num); 
		}
		//formats to 2 decimal places
		earnings = Double.parseDouble(String.format("%.2f", earnings)); 
	}

	/**
	 * Redefines JPanel's paintComponent to draw this pie chart
	 */
	 public void paintComponent(Graphics g) {
	   super.paintComponent(g);

	   int w = getWidth();
	   int h = getHeight();
	   int x = w/2;
	   int y = h/2;
	   int r = Math.min(w, h) / 4;
	   drawPieChart(g, x, y, r);
	   drawLegend(g, x, y, r);
	 }

	 /**
	  * Draws the pie chart by calculating the angle in degrees of the expenses
	  * portion and filling in the rest with the earnings portion
	  */
	 public void drawPieChart(Graphics g, int x, int y, int r) {
	   double total = expensesF + expensesU + expensesE + expensesH + expensesO + earnings;
	   int fromDegree = 0;

	   if (total > 0)
	   {
	     int degrees;
	     //Draws food expenses sector
	     g.setColor(color1);
	     degrees = countToDegrees(expensesF, total);
	     drawSector(g, x, y, r, fromDegree, degrees);
	     fromDegree += degrees; 
	      
	     //Draws utilities expenses sector
	     g.setColor(color2);
	     degrees = countToDegrees(expensesU, total);
	     drawSector(g, x, y, r, fromDegree, degrees);
	     fromDegree += degrees; 
	     
	     //Draws education expenses sector
	     g.setColor(color3);
	     degrees = countToDegrees(expensesE, total);
	     drawSector(g, x, y, r, fromDegree, degrees);
	     fromDegree += degrees; 
	     
	     //Draws household products expenses sector
	     g.setColor(color4);
	     degrees = countToDegrees(expensesH, total);
	     drawSector(g, x, y, r, fromDegree, degrees);
	     fromDegree += degrees; 
	     
	     //Draws other expenses sector
	     g.setColor(color5);
	     degrees = countToDegrees(expensesO, total);
	     drawSector(g, x, y, r, fromDegree, degrees);
	     fromDegree += degrees; 
	      
	     //Draws earnings sector using remaining space
	     g.setColor(color6);
	     degrees = Math.max(360 - fromDegree, 0);
	     drawSector(g, x, y, r, fromDegree, degrees); 
	   } else {
	     g.setColor(Color.LIGHT_GRAY);
	     drawSector(g, x, y, r, fromDegree, 360);
	   }
	 }

	 /**
	  * Draws the expenses and earnings and the corresponding color squares.
	  */
	 public void drawLegend(Graphics g, int x, int y, int r){ 
	   // Display the counts:
	   y += (r + 20);
	   g.setColor(Color.BLACK);
	   
	   //Draws legend with corresponding values
	   //count variables concatenated to "" to convert to a String
	   String expF = "Food $" + expensesF; 
	   g.setColor(color1);
	   g.drawString(expF, x - r - PIXEL_PER_CHAR*expF.length()/2, y);
	   
	   String expU = "Home + Utilities $" + expensesU; 
	   g.setColor(color2);
	   g.drawString(expU, x + r - PIXEL_PER_CHAR*expU.length()/2, y);
	   
	   y+=20; 
	   
	   String expE = "Education $" + expensesE; 
	   g.setColor(color3);
	   g.drawString(expE, x - r - PIXEL_PER_CHAR*expE.length()/2, y);
	   
	   String expH = "Household $" + expensesH; 
	   g.setColor(color4);
	   g.drawString(expH, x + r - PIXEL_PER_CHAR*expH.length()/2, y);
	   
	   y+=20; 
	   
	   String expO = "Other $" + expensesO; 
	   g.setColor(color5);
	   g.drawString(expO, x - r - PIXEL_PER_CHAR*expO.length()/2, y);

	   String earn = "Earnings $" + earnings; 
	   g.setColor(color6);
	   g.drawString(earn, x + r - PIXEL_PER_CHAR*earn.length()/2, y);
	   
	   //Display the month and year of the pie chart
	   LocalDate currentDate = LocalDate.now();
	   String title = currentDate.getMonth()+", "+currentDate.getYear();
	   g.setColor(Color.BLACK);
	   g.setFont(new Font("TimesRoman", Font.BOLD, 20));
	   g.drawString(title, x-g.getFontMetrics().stringWidth(title)/2, y-210);
	   
	 }

	 // 
	 /**
	  * Returns the number of degrees in a pie slice that corresponds 
	  * to monetary value / total, rounded to the nearest integer.
	  */
	 private int countToDegrees(double value, double total) {
		 //Returns the angle in degrees by casting to a double to perform
		 //arithmetic operations and rounding to an int by adding 0.5 and casting
		 return (int)(value / total * 360.0 + 0.5) ;
	 }

	 /**
	  * Draws a sector, centered at x, y, of radius r, of angle 
	  * measure degrees, starting at fromDegree.
	  */
	 private void drawSector(Graphics g, int x, int y, int r, int fromDegree, int degrees) {
	   if (degrees > 359)
	     g.fillOval(x - r, y - r, 2 * r, 2 * r);
	   else
	     g.fillArc(x - r, y - r, 2 * r, 2 * r, fromDegree, degrees);
	 }
}
