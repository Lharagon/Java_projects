//Programmer: Luis Garcia
//Assignment: Cs990_SalesAnalysisDemo
//Class:      CS990

import java.util.Scanner;
import java.io.*;

public class SalesAnalysisDemo {

	public static void main(String[] args) throws IOException {

		Scanner inputFile;
		double totalSale = 0, weekTotal;
		double highWeekAmt = 0, lowWeekAmt = -1;
		String line;
		int lowWeek = 0, highWeek = 0;
		int numWeeks = 0;
		
		File file = new File("SalesData.txt");
		inputFile = new Scanner(file);
		
		while(inputFile.hasNext()) {
//			Inc weeks counter
			numWeeks++;
			line = inputFile.nextLine();
			String[] tokens = line.split(",");
//			Get Sum from tokens array
			weekTotal = getSum(tokens);
//			Add total to totalSales
			totalSale += weekTotal;
			
			System.out.printf("Weekly sales from week %s is $%.2f\n", numWeeks, weekTotal);
			System.out.printf("Average for week %s is $%.2f\n", 
					numWeeks, 
					getAverage(weekTotal, tokens.length));
			
			if(isHighest(weekTotal, highWeekAmt)) {
				highWeekAmt = weekTotal;
				highWeek = numWeeks;
			}
			
			if(isLowest(weekTotal, lowWeekAmt)) {
				lowWeekAmt = weekTotal;
				lowWeek = numWeeks;
			}	
		}
		
		System.out.printf("Total sale of all weeks = %.2f\n", totalSale);
		System.out.printf("Average weekly sales = %.2f\n", getAverage(totalSale, numWeeks));
		System.out.println("The week number with the highest amount of sales is: " + highWeek);
		System.out.println("The week number with the lowest amount of sales is: " + lowWeek);
		
		inputFile.close();
		
	}
	
	public static double getSum(String[] amounts) {
		double weekTotal = 0;
		for(String amount : amounts) {
			weekTotal += Double.parseDouble(amount);
		}
		return weekTotal;
	}
	

	public static double getAverage(double total, int items) {
		return total/items;
	}
	
//	Checks if its the lowest amount
	public static boolean isLowest(double thisWeek, double lowestWeek) {
		if(lowestWeek < 0) {
			return true;
		} else if(thisWeek <= lowestWeek) {
			return true;
		}
		return false;
	}
	
//	Check if it's the highest amounts
	public static boolean isHighest(double thisWeek, double highestWeek) {
		if(thisWeek >= highestWeek) {
			return true;
		}
		return false;
	}

}