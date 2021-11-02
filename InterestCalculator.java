/*
 * Sinry Dong
 * ICS4U
 * Due: June 17 2021
 * Calculates future value, and monthly interest
 * Resources: https://www.investopedia.com/terms/c/compoundinterest.asp
 * 		      https://www.thebalance.com/loan-payment-calculations-315564
 * 			  Previous business course material
 */

public class InterestCalculator {
	
	// *** fields ***
	private double comPeriod; //number of times per year the interest is compounded
	private double principal; 
	private double rate;
	private double time; //in years
	private double amount; 
	
	// *** constructor ***
	public InterestCalculator(double period, double loan, double interest, double years) { 
		comPeriod = period; 
		principal = loan; 
		rate = interest/100; //converts percentage to decimal
		time = years; 
	}
	
	/**
	 * Calculates future value of an investment
	 * @return future value of an investment
	 */
	public double findFutureValue() {
		amount = principal*(Math.pow(1+rate/comPeriod, comPeriod*time)); 
		return amount;
	}
	
	/**
	 * Calculates monthly payments for a loan
	 * @return amount to be paid back each month
	 */
	public double findMonthlyPayment() {
		double r = rate/12; 
		double n = time*12; 
		return principal*((r*Math.pow(1+r, n))/(Math.pow(1+r, n) - 1)); 
	}

}
