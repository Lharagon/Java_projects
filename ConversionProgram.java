//Programmer: Luis Garcia
//Assignment: Cs990Homework4
//Date: 02/27/2019

import java.util.Scanner;

public class ConversionProgram {

	public static void main(String[] args) {
		
		int choice, meters;
		
		Scanner keyboard = new Scanner(System.in);
		
//		Ask user for distance in meters
		System.out.println("Enter your distance in meters:");
		meters = keyboard.nextInt();
		
//		Check if meters is a negative number
		if(meters < 0) {
			System.exit(0);
		}
		
		do {
			menu();
			choice = keyboard.nextInt();
			
			switch(choice)
			{
			case 1:
				showKilometers(meters);
				break;
			case 2:
				showInches(meters);
				break;
			case 3:
				showFeet(meters);
				break;
			case 4:
				System.out.println("Thank you, the program will now end");
				break;
			default:
				System.out.println("Invalid choice, Reenter a valid choice");
			}
			
		} while(choice != 4);
		
		keyboard.close();

	}
	
//	Converts and shows meters into kilometers
	public static void showKilometers(int meters) {
		double km = meters * 0.001;
		String message = String.format("%s meters is %.1f kilometers.", meters, km);
		System.out.println(message);
		System.out.println();
	}
	
//	Converts and shows meters into inches
	public static void showInches(int meters) {
		double in = meters * 39.37;
		String message = String.format("%s meters is %.1f inches.", meters, in);
		System.out.println(message);
		System.out.println();
	}
	
//	Converts and shows meters into inches
	public static void showFeet(int meters) {
		double ft = meters * 3.281;
		String message = String.format("%s meters is %.1f feet.", meters, ft);
		System.out.println(message);
		System.out.println();
	}
	
//	Displays menu of selections
	public static void menu() {
		System.out.println("1. Convert to kilometers");
		System.out.println("2. Convert to inches");
		System.out.println("3. Convert to feet");
		System.out.println("4. Quit the program");
	}
}
