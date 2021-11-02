/*
 * Sinry Dong
 * ICS4U
 * Due: June 17 2021
 * Keeps track of important dates and issues reminders to the user
 * Resources: 
 * https://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself
 * Google Classroom reading files resources
 */

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.*;

public class Calendar {
	
	//***fields***
	private ArrayList<String> reminders; 
	private String backupDates = "Dates.txt"; 
	private File file; 
	DecimalFormat df = new DecimalFormat("00"); 
	LocalDate currentDate; 
	int month, day, year; 

	//***constructor***
	public Calendar() {
		currentDate = LocalDate.now();
		month = currentDate.getMonthValue(); 
		day = currentDate.getDayOfMonth(); 
		year = currentDate.getYear(); 
		reminders = new ArrayList<String>();
		
		//Creates new file if it does not already exist
		file = new File(backupDates);
		if(file.exists()) { 
		} else {
			try {
				file.createNewFile(); 
			} catch (IOException e) {
				System.out.println("File could not be created:"); 
				System.err.println("IOException: "+e.getMessage()); 
			}
		}		
		
		//Reads text by line use BufferedReader
		try {
			FileReader in = new FileReader(file); 
			String lineOfText; 
			BufferedReader readFile = new BufferedReader(in);
			while((lineOfText = readFile.readLine()) != null) {
				reminders.add(lineOfText); //Saves line of text to array list
			}
			readFile.close();
			in.close();			
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist or could not be found:"); 
			System.err.println("FileNotFoundException: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Problem reading file:"); 
			System.err.println("IOException: "+e.getMessage()); 
		}
		
	}
	
	/**
	 * Sorts array of dates in order and removes any dates that have already passed
	 */
	public void sortDates() {
		if(reminders.size()<2) {
			return; 
		}
		
		//Sorts array, lexicographical order matches order of dates 
		Collections.sort(reminders);
		
		//Finds current date, with month and day formatted to 2 digits
		String date = "" + df.format(month) + df.format(day) + year;
		//Checks through array list to look for dates that have already passed
		int i = 0;
		while(i<reminders.size()) {
			String firstDate = reminders.get(i); 
			int space = firstDate.indexOf(" "); 
			firstDate = firstDate.substring(0, space); 
			//Compares current date to date of the current element
			if(date.compareTo(firstDate)>0) {
				reminders.remove(i); 
			} else {
				i++; 
			}
		}
	}
	
	/**
	 * Adds new date reminder to the array list
	 */
	public void addDate(int month, int day, String message) {
		reminders.add(df.format(month)+df.format(day)+year+" "+message); 
		Collections.sort(reminders);  
	}
	
	/**
	 * Overrides toString method to return the array of reminders
	 */
	public String toString() {
		return reminders.toString(); 
	}
	
	/**
	 * Checks whether the nearest reminder corresponds to the current date. 
	 * If so, the message is returned, if not, the program wishes the user a great day. 
	 */
	public String checkDate() {
		if(reminders.size()<1) {
			return "Have a wonderful day!"; 
		}
		
		//Keeps track of today's date
		String date = "" + df.format(month) + df.format(day) + year; 
		String firstDate = reminders.get(0); 
		//Compares the current date with the date of the next reminder
		if(date.equalsIgnoreCase(firstDate.substring(0, date.length()))) {
			//Returns the message if today is the date
			int space = firstDate.indexOf(" "); 
			String note = firstDate.substring(space+1); 
			reminders.remove(0);
			return note; 
		} else {
			return "Have a wonderful day!"; 
		}
		
	}	
	
	/**
	 * Removes all previous dates from file by overwriting.
	 * Prepares for all upcoming dates to be re-recorded.
	 */
	public void overwriteDates() {
		//Sets up writer 
		Writer overwriter = null;
		try {
			//Append mode switched off 
			overwriter = new FileWriter(backupDates, false); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		//Clears text file by overwriting
		PrintWriter emptyOutput = new PrintWriter(overwriter); 
		emptyOutput.print("");
		emptyOutput.close();
	}
	
	/**
	 * Takes all the remaining reminders in the arraylist and writes it back to the file
	 */
	public void writeDates() { 
		Writer writer = null;
		try {
			//Append mode switched on 
			writer = new FileWriter(backupDates, true); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		//Writes to text file
		PrintWriter output = new PrintWriter(writer); 
		
		//Finds current month
		//Enters automatic dates if it is a new year, otherwise re-enters remaining dates
		if((month==12 && day==31)) {
			for(int monthNum=1; monthNum<=12; monthNum++) {
				output.println(df.format(month)+"01"+year+" Remember to pay your bills!"); 
			}
			output.close(); 
		} else {
			for(int i=0; i<reminders.size(); i++) {
				output.println(reminders.get(i));
			}
			output.close(); 
		}
		reminders.clear();
	}	
	
}
