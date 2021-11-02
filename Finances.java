/*
 * Sinry Dong
 * ICS4U 
 * Due: June 17 2021
 * Expenses or earnings are categorized and added to the user's profile
 * Resources: Google classroom File Input/Output resources and grade 11 resources
 */

import java.io.*;
import java.time.LocalDate;
public class Finances {
	
	// *** fields ***
	private String backupExp; 
	private String backupEarn; 
	
	// *** constructor ***
	public Finances() {
		//Sets up file names
		LocalDate currentDate = LocalDate.now(); 
		backupExp = currentDate.getMonth()+"_"+currentDate.getYear()+"_User_Expenses.txt";
		backupEarn = currentDate.getMonth()+"_"+currentDate.getYear()+"_User_Earnings.txt";
	}
	
	/**
	 * Writes expense to a file
	 * @param value of expense
	 */
	public void addExpense(double exp, String type) {
		File file = new File(backupExp); 
		 //Creates new file if the file does not already exist
		if(file.exists()) { 
		} else {
			try {
				file.createNewFile(); 
			} catch (IOException e) {
				System.out.println("File could not be created:"); 
				System.err.println("IOException: "+e.getMessage()); 
			}
		}
		
		Writer writer = null;
		try {
			//Append mode switched on 
			writer = new FileWriter(backupExp, true); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		//Writes to text file
		PrintWriter output = new PrintWriter(writer); 
		output.println(type+exp);
		output.close();
	}
	
	/**
	 * Writes earning to a file
	 * @param value of earnings
	 */
	public void addEarning(double earn) {
		File file = new File(backupEarn); 
		 //Creates new file if the file does not already exist
		if(file.exists()) { 
		} else {
			try {
				file.createNewFile(); 
			} catch (IOException e) {
				System.out.println("File could not be created:"); 
				System.err.println("IOException: "+e.getMessage()); 
			}
		}
		
		Writer writer = null;
		try {
			//Append mode switched on 
			writer = new FileWriter(backupEarn, true); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		//Writes to text file
		PrintWriter output = new PrintWriter(writer); 
		output.println(earn);
		output.close();
	}
	

}
