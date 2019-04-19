//Programmer: Luis Garcia
//Assignment: Cs990Homework3A
//Date: 02/23/2019


import java.util.Scanner;

public class BarChart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int store1,   // Contains Sales for the stores
			store2,
			store3,
			store4,
			store5;
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Enter sales for store 1:");
		store1 = keyboard.nextInt();
		System.out.println("Enter sales for store 2:");
		store2 = keyboard.nextInt();
		System.out.println("Enter sales for store 3:");
		store3 = keyboard.nextInt();
		System.out.println("Enter sales for store 4:");
		store4 = keyboard.nextInt();
		System.out.println("Enter sales for store 5:");
		store5 = keyboard.nextInt();
		
		// Sales Bar Charts
		System.out.println("SALES BAR CHART");
		// Chart for Store 1
		System.out.print("Store 1: ");
		for(int i = 0; i < (store1/100); i++) 
		{
			System.out.print("*");
		}
		System.out.print("\n");
		
		// Chart for Store 2
		System.out.print("Store 2: ");
		for(int i = 0; i < (store2/100); i++) 
		{
			System.out.print("*");
		}
		System.out.print("\n");
		
		// Chart for Store 3
		System.out.print("Store 3: ");
		for(int i = 0; i < (store3/100); i++) 
		{
			System.out.print("*");
		}
		System.out.print("\n");
		
		// Chart for Store 4
		System.out.print("Store 4: ");
		for(int i = 0; i < (store4/100); i++) 
		{
			System.out.print("*");
		}
		System.out.print("\n");
		
		// Chart for Store 5
		System.out.print("Store 5: ");
		for(int i = 0; i < (store5/100); i++) 
		{
			System.out.print("*");
		}
		System.out.print("\n");
		
		
		
		
		
		keyboard.close();
	}

}
