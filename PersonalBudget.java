/*
 * Sinry Dong
 * ICS4U
 * Due: June 17 2021
 * Main menu of the personal budget program which directs users to various tools
 * Resources: 
 * https://stackoverflow.com/questions/10472376/can-i-use-multiple-actionlisteners-in-a-single-class
 * https://stackoverflow.com/questions/14704719/my-jframe-does-not-refresh
 * https://perso.ensta-paris.fr/~diam/java/online/notes-java/GUI/components/30textfield/11textfield.html
 * https://docs.oracle.com/javase/7/docs/api/javax/swing/JComboBox.html
 * http://www.java2s.com/Code/Java/Swing-JFC/Borderofallkinds.htm
 * https://tips4java.wordpress.com/2009/11/29/text-prompt/
 * https://www.dummies.com/programming/java/how-to-write-java-code-to-show-an-image-on-the-screen/
 * https://stackoverflow.com/questions/11200585/adding-a-prompt-text-property-to-jtextfield
 * http://www.java2s.com/Tutorial/Java/0240__Swing/SpringLayout.htm
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JOptionPane/Customize_JOptionPane_showMessageDialog_icon.htm
 * Google Classroom GUI resources
 */

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class PersonalBudget extends JFrame {
	
	//***fields***
	private Container container; 
	private GridLayout grid, grid1, grid2; 
	private SpringLayout sprLayout; 
	private JPanel financePanel, chartsPanel, walletPanel, interestPanel, historyPanel; 
	private JPanel screen; 
	private JButton buttonExp, buttonEarn, buttonC, buttonW, buttonI, buttonH; 
	private ImageIcon image1, image2, image3; 
	
	//***constructor***
	public PersonalBudget() {
		//Instructions/introductory JOptionPane
		String intro = "Welcome to your personal budgeting program!\n"
						+ "You'll have the tools to record, track, and compare finances.\n"
						+ "You'll be able to calculate interest and receive important reminders!\n"
						+ "Let's get budgeting! Click OK to begin."; 
		JOptionPane.showMessageDialog(null, intro, "Personal Budget", JOptionPane.INFORMATION_MESSAGE, 
										new ImageIcon(getClass().getResource("/pie_chart.png")));
		
		//sets up grid layout and JFrame characteristics 
		grid = new GridLayout(2,3); 
		container = getContentPane(); 
		container.setLayout(grid);
		this.setTitle("Personal Budget");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage((getClass().getResource("/pie_chart.png"))));
		this.setSize(840,560); 
		this.setBackground(Color.WHITE); 
		this.setLocationRelativeTo(null); 
		this.setAlwaysOnTop(true); 
		
		//Creates finance panel on the upper left
		financePanel = new JPanel(); 
		grid1 = new GridLayout(7,1); 
		financePanel.setLayout(grid1);
		//Hidden features
		JLabel financeInstructions = new JLabel(); 
		JTextField entry = new JTextField(10); 
		String types[] = {"Food", "Housing & Utilities", "Education", "Household Products", 
								"Societal and Other"}; 
		JComboBox type = new JComboBox(types); 
		JLabel confirmation = new JLabel("Entry Recorded"); 
		//Initial buttons
		buttonExp = new JButton("Enter Expenses"); 
		buttonEarn = new JButton("Enter Earnings"); 
		financePanel.add(buttonExp); 
		financePanel.add(buttonEarn);
		//When add expenses button is clicked
		buttonExp.addActionListener(new ActionListener() {
			//Override
	         public void actionPerformed(ActionEvent arg0) {
	        	//remove previous entries
	        	financePanel.remove(type);
	        	financePanel.remove(entry);
	        	financePanel.remove(financeInstructions);
	        	entry.setText("");
	        	financePanel.remove(confirmation);
	        	
	        	//Set up components
	        	financePanel.add(type); 
	        	financeInstructions.setText("Enter Expenses: ");
	        	financePanel.add(financeInstructions); 
	        	financePanel.add(entry); 
	        	//when the user enters a number in the text box
	        	entry.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent event) {
	        			//When enter key is pressed
	        			if(event.getSource()==entry) {
	        				//Writes to expenses file assuming that a type is selected
	        				if(type.getSelectedIndex()>=0) {
	        					String expType = type.getSelectedItem().toString().substring(0,1);
	        					//create finance object and calls methods to record expenses
	        					Finances exp = new Finances(); 
		        				exp.addExpense(Double.parseDouble(event.getActionCommand()), expType); 
		        				confirmation.setHorizontalAlignment(SwingConstants.CENTER);
		        				financePanel.add(confirmation); 
		        				financePanel.revalidate();
		        	     	    financePanel.repaint();
	        				}
	        			}
	        				
	        		}
	        	});
	        	//jframe updated
	     	    financePanel.revalidate();
	     	    financePanel.repaint();
	         }
		});
		
		//when add earnings button is clicked
		buttonEarn.addActionListener(new ActionListener() {
			//Override
	         public void actionPerformed(ActionEvent arg0) {
	        	//remove previous entries
	        	financePanel.remove(type);
	        	financePanel.remove(entry);
	        	financePanel.remove(financeInstructions);
	        	entry.setText("");
	        	financePanel.remove(confirmation);
	        	
	        	//set up components
	        	financeInstructions.setText("Enter Earnings: ");
	        	financePanel.add(financeInstructions); 
	        	financePanel.add(entry); 
	        	//Sets jComboBox selection to null to avoid writing to wrong file
	        	type.setSelectedIndex(-1); 
	        	//when the user enters a number in the text box
	        	entry.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent event) {
	        			//create finance object and calls method to record earnings
	        			if(event.getSource()==entry) {
	        				Finances earn = new Finances(); 
	        				earn.addEarning(Double.parseDouble(event.getActionCommand())); 
	        				confirmation.setHorizontalAlignment(SwingConstants.CENTER);
	        				financePanel.add(confirmation); 
	        				financePanel.revalidate();
	        	     	    financePanel.repaint();
	        			}
	        				
	        		}
	        	});
	        	//jframe updated
	     	    financePanel.revalidate();
	     	    financePanel.repaint();
	         }
		});
		financePanel.setBackground(Color.WHITE);
		container.add(financePanel); 
		
		//created charts panel on upper center 
		LocalDate currentDate = LocalDate.now();
		chartsPanel = new JPanel();
		buttonC = new JButton(currentDate.getMonth()+" STATISTICS"); 
		chartsPanel.add(buttonC); 
		//add image to charts panel
		image1 = new ImageIcon(getClass().getResource("/pie_chart.png"));
		JLabel img1Label = new JLabel();
		img1Label.setIcon(image1);
		chartsPanel.add(img1Label); 
		//when the button is clicked
		buttonC.addActionListener(new ActionListener() {
			//Override
	         public void actionPerformed(ActionEvent arg0) {
	        	//remove previously displayed final panel
	        	container.remove(screen); 
	     	    container.setBackground(Color.WHITE);
	     	    //create charts object and updates to the new screen panel
	     	    Charts cp = new Charts();
	     	    screen = cp;
	     	    container.add(screen);
	     	    screen.revalidate();
	     	    screen.repaint();
	         }
		});
		chartsPanel.setBackground(Color.WHITE);
		container.add(chartsPanel);
		
		//created wallet panel on upper right
		walletPanel = new JPanel(); 
		buttonW = new JButton("Check Wallet"); 
		walletPanel.add(buttonW);
		//add image to wallet panel
		image2 = new ImageIcon(getClass().getResource("/wallet.png")); 
		JLabel img2Label = new JLabel();
		img2Label.setIcon(image2);
		walletPanel.add(img2Label);
		//when button is clicked
		buttonW.addActionListener(new ActionListener() {
			//Override
	         public void actionPerformed(ActionEvent arg0) {
	        	//remove previously displayed final panel
	        	container.remove(screen); 
	     	    container.setBackground(Color.WHITE);
	     	    //creates wallet object and updates to the new screen panel
	     	    Wallet wp = new Wallet();
	     	    screen = wp;
	     	    container.add(screen);
	     	    screen.revalidate();
	     	    screen.repaint();
	         }
		});
		walletPanel.setBackground(Color.WHITE);
		container.add(walletPanel); 
		
		//created interest panel at bottom left
		interestPanel = new JPanel(); 
		//Hidden components
		JTextField principal = new JTextField(10); 
		JTextField rate = new JTextField(10);
		JTextField time = new JTextField(5);
		JTextField compoundPeriod = new JTextField(5);
		JLabel pInstructions = new JLabel(); 
		JLabel rInstructions = new JLabel();
		JLabel tInstructions = new JLabel();
		JLabel cInstructions = new JLabel();
		JLabel fvLabel = new JLabel(); 
		JLabel mpLabel = new JLabel(); 
		String Itypes[] = {"LOAN", "INVESTMENT"}; 
    	JComboBox comboBox = new JComboBox(Itypes);
		JButton enter = new JButton("Enter");
		
		//Initially visible components
		buttonI = new JButton("Calculate Interest");
		grid2 = new GridLayout(12,1); 
		interestPanel.setLayout(grid2);
		interestPanel.add(buttonI); 
		//when button is clicked
		buttonI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//remove previous entries
				principal.setText("");
				rate.setText("");
				time.setText("");
				compoundPeriod.setText("");
	        	interestPanel.remove(principal);
	        	interestPanel.remove(rate);
	        	interestPanel.remove(time);
	        	interestPanel.remove(compoundPeriod);
	        	interestPanel.remove(pInstructions);
	        	interestPanel.remove(rInstructions);
	        	interestPanel.remove(tInstructions);
	        	interestPanel.remove(cInstructions);
	        	interestPanel.remove(fvLabel);
	        	interestPanel.remove(mpLabel);
	        	interestPanel.remove(enter);
	        	
	        	//sets up hidden components
	        	pInstructions.setText("Principal: ");
	        	interestPanel.add(pInstructions); 
	        	interestPanel.add(principal); 
	        	rInstructions.setText("Annual Rate (%): ");
	        	interestPanel.add(rInstructions); 
	        	interestPanel.add(rate); 
	        	tInstructions.setText("Time (Years): ");
	        	interestPanel.add(tInstructions); 
	        	interestPanel.add(time); 
	        	cInstructions.setText("Number of Times Interest is Compounded:");
	        	interestPanel.add(cInstructions); 
	        	interestPanel.add(compoundPeriod); 
	        	
	        	interestPanel.add(comboBox); 
	        	interestPanel.add(enter); 
	        	
	        	//when info is entered and the button is clicked
	        	enter.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent event) {
	        			//info retrieved from text fields
	        			double loan = Double.parseDouble(principal.getText()); 
	        			double intRate = Double.parseDouble(rate.getText());
	        			double years = Double.parseDouble(time.getText());
	        			double period = Double.parseDouble(compoundPeriod.getText());
	        			
	        			//create interest calculator object 
	        			InterestCalculator calculator = new InterestCalculator(period, loan, intRate, years);
	        			
	        			//decides on whether a loan or investment is being calculated
	        			int choice = comboBox.getSelectedIndex();
	        			if(choice==1) {
	        				//if an investment is selected, future value is called and calculated
	        				String fv = "Future Value: "+String.format("%.2f", calculator.findFutureValue()); 
	        				fvLabel.setText(fv);
	        				fvLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        				interestPanel.add(fvLabel);  
	        			} else {
	        				//if loan is selected or default, monthly payment is called and calculated
	        				String mp = "Monthly Payment: "+String.format("%.2f", calculator.findMonthlyPayment()); 
	        				mpLabel.setText(mp);
	        				mpLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        				interestPanel.add(mpLabel); 
	        			}
	        			interestPanel.revalidate();
        	     	    interestPanel.repaint();
	        				
	        		}
	        	});
	        	//jframe updated
	     	    interestPanel.revalidate();
	     	    interestPanel.repaint();
			}
		});
		interestPanel.setBackground(Color.WHITE);
		container.add(interestPanel); 
		
		//created history panel at the bottom center
		historyPanel = new JPanel(); 
		buttonH = new JButton("Check History"); 
		historyPanel.add(buttonH);
		//Add image to history panel
		image3 = new ImageIcon(getClass().getResource("/records.jpg"));
		JLabel img3Label = new JLabel();
		img3Label.setIcon(image3);
		historyPanel.add(img3Label);
		//when button is clicked
		buttonH.addActionListener(new ActionListener() {
			//Override
	         public void actionPerformed(ActionEvent arg0) {
	        	//remove previously displayed final panel
	        	container.remove(screen); 
	     	    container.setBackground(Color.WHITE);
	     	    
	     	    //creates history object and updates to new screen panel
	     	    History hp = new History(); 
	     	    screen = hp; 
	     	    container.add(screen);
	     	    screen.revalidate();
	     	    screen.repaint();
	         }
		}); 
		historyPanel.setBackground(Color.WHITE);
		container.add(historyPanel); 
		
		
		//Screen panel occupies bottom right
		screen = new JPanel(); 
		screen.setBackground(Color.WHITE);
		sprLayout = new SpringLayout();
	    screen.setLayout(sprLayout); 
		//creates calendar object
		Calendar calendar = new Calendar(); 
		//Retrieves today's reminders and displays to panel
		calendar.sortDates(); 
		JTextArea message = new JTextArea(2, 22); 
		message.setText(calendar.checkDate());
		message.setBorder(new TitledBorder(new LineBorder(new Color(255, 51, 51), 2), "REMINDER"));
		message.setBounds(10, 5, 10, 5);
		calendar.overwriteDates();
		calendar.writeDates();
		sprLayout.putConstraint(SpringLayout.NORTH, message, 10, SpringLayout.NORTH, screen);
		sprLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, message, 0, SpringLayout.HORIZONTAL_CENTER, screen);
	    
		screen.add(message);
		
		//If user decides to add a new reminder
		//**Note: Assumes user MUST enter a date once button is clicked,
		//otherwise all reminders are removed
		JButton addReminder = new JButton("Add Reminder"); 
		sprLayout.putConstraint(SpringLayout.SOUTH, addReminder, 30, SpringLayout.SOUTH, message);
	    sprLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addReminder, 0, SpringLayout.HORIZONTAL_CENTER, screen);
		screen.add(addReminder); 
		addReminder.addActionListener(new ActionListener() {
			//input components
			String months[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"}; 
			JComboBox month = new JComboBox(months); 
			JTextField day = new JTextField(4); 
			JTextField reminder = new JTextField(20);
			JButton dateButton = new JButton("Enter"); 
			JLabel dateConfirmation = new JLabel("Entry recorded"); 
			
			//When button is clicked
			public void actionPerformed(ActionEvent event) {
				//remove previous components
				screen.remove(month);
				screen.remove(day);
				screen.remove(reminder);
				screen.remove(dateButton);
				screen.remove(dateConfirmation);
				
				//New calendar object created to account for added date
				Calendar calendar2 = new Calendar(); 
				calendar2.sortDates(); 
				sprLayout.putConstraint(SpringLayout.SOUTH, month, 30, SpringLayout.SOUTH, addReminder);
			    sprLayout.putConstraint(SpringLayout.WEST, month, 75, SpringLayout.WEST, screen);
				screen.add(month);//month selection combo box added
				month.setMaximumRowCount(6);
				
				//Date entry text field
				String dateText = "date";  
				day.setForeground(Color.GRAY);
				day.setHorizontalAlignment(day.CENTER);
				day.setText(dateText);
				//Instructions in text field disappears when user clicks and enters something
				day.addFocusListener(new FocusListener() {
		            @Override
		            public void focusGained(FocusEvent fe) {
		            	if(day.getText().equals(dateText)) {
		            		day.setForeground(Color.BLACK);
		            		day.setText("");
		            	}
		            		
		            }

		            @Override
		            public void focusLost(FocusEvent fe) {
		            	if(day.getText().equals("")) {
		            		day.setForeground(Color.GRAY);
		            		day.setText("date");
		            	}
		            }
		        });
				sprLayout.putConstraint(SpringLayout.SOUTH, day, 30, SpringLayout.SOUTH, addReminder);
			    sprLayout.putConstraint(SpringLayout.EAST, day, 70, SpringLayout.EAST, month);
				screen.add(day); 
				
				//Message entry field
				String reminderText = "enter message"; 
				reminder.setForeground(Color.GRAY);
				reminder.setHorizontalAlignment(day.CENTER);
				reminder.setText(reminderText);
				//Instructions in text field disappears when user clicks and enters something
				reminder.addFocusListener(new FocusListener() {
		            @Override
		            public void focusGained(FocusEvent fe) {
		            	if(reminder.getText().equals(reminderText)) {
		            		reminder.setForeground(Color.BLACK);
		            		reminder.setText("");
		            	}	
		            }

		            @Override
		            public void focusLost(FocusEvent fe) {
		            	if(reminder.getText().equals("")) {
		            		reminder.setForeground(Color.GRAY);
		            		reminder.setText("enter message");
		            	}
		            }
		        });
				sprLayout.putConstraint(SpringLayout.SOUTH, reminder, 30, SpringLayout.SOUTH, month);
			    sprLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, reminder, 0, SpringLayout.HORIZONTAL_CENTER, screen);
				screen.add(reminder); 
				
				//New date recorded when button is clicked 
				sprLayout.putConstraint(SpringLayout.SOUTH, dateButton, 30, SpringLayout.SOUTH, reminder);
			    sprLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dateButton, 0, SpringLayout.HORIZONTAL_CENTER, screen);
				screen.add(dateButton);
				
				dateButton.addActionListener(new ActionListener() {
					//Date added to file via calendar class
					public void actionPerformed(ActionEvent event) {
						calendar2.addDate(month.getSelectedIndex()+1, Integer.parseInt(day.getText()), 
											reminder.getText()); 
						dateConfirmation.setHorizontalAlignment(SwingConstants.CENTER);
						sprLayout.putConstraint(SpringLayout.SOUTH, dateConfirmation, 30, SpringLayout.SOUTH, dateButton);
					    sprLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dateConfirmation, 0, SpringLayout.HORIZONTAL_CENTER, screen);
						screen.add(dateConfirmation); 
						calendar2.overwriteDates();
						calendar2.writeDates(); 
						//Panel updated
						screen.revalidate();
						screen.repaint();
					}
				}); 
				//panel updated
				screen.revalidate();
				screen.repaint();
			}
		});
		
		container.add(screen);
		
		this.setVisible(true); //set JFrame to visible
	}

	public static void main(String[] args) {
		//Creates GUI
		PersonalBudget application = new PersonalBudget(); 
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
