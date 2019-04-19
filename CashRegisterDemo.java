//Programmer: Luis Garcia
//Assignment: Cs990_Homework7_SecondClass_CashRegister
//Class:      CS990

import java.util.Scanner;

public class CashRegisterDemo {

	public static void main(String[] args) {
		
		String name;
		int amount, buyingAmount;
		double price;
		CashRegister cashRegister;
		Scanner keyboard = new Scanner(System.in);
		
//		Greeting the user
		System.out.println("We need information about the retail item.");
		
//		Get name of items
		System.out.print("What is the name of the item? ");
		name = keyboard.nextLine();
		
//		Get number of units
		System.out.print("How many units are available? ");
		amount = keyboard.nextInt();
		while(amount <= 0) {
			System.out.println("Invalid Entry. Please try again.");
			amount = keyboard.nextInt();
		}
		
//		Get cost of item
		System.out.print("How much does the item cost per unit? ");
		price = keyboard.nextDouble();
		while(price <= 0) {
			System.out.println("Invalid Entry. Please try again.");
			price = keyboard.nextDouble();
		}
		
//		Get amount of purchase
		System.out.print("How many items are you going to purchase? ");
		buyingAmount = keyboard.nextInt();
		while(buyingAmount <= 0) {
			System.out.println("Invalid Entry. Please try again.");
			buyingAmount = keyboard.nextInt();
		}
		
//		Create new cashRegister and retailItem with user input, and 
		cashRegister = new CashRegister(new RetailItem(name, amount, price), buyingAmount);
		
//		print totals
		System.out.println("Subtotal: " + cashRegister.getSubtotal());
		System.out.println("Tax: " + cashRegister.getTax());
		System.out.println("Total: " + cashRegister.getTotal());
		
		keyboard.close();
	}

}

class CashRegister {
	
	RetailItem item;
	int quantity;
	
//	constructor
	public CashRegister(RetailItem retailItem, int quantity) {
		this.item = retailItem;
		this.quantity = quantity;
	}
	
//	getSubtotal
	public double getSubtotal() {
		return quantity * item.price;
	}
	
//	getTax
	public double getTax() {
		return .06 * getSubtotal();
	}
	
//	getTotal
	public double getTotal() {
		return getSubtotal() + getTax();
	}
}

class RetailItem {
	
//	Short description of item
	String description;
//	number of units on hand
	int unitsOnHand;
//	price of the item
	double price;
	
//	constructor
	public RetailItem(String description, int unitsOnHand, double price) {
		this.description = description;
		this.unitsOnHand = unitsOnHand;
		this.price = price;
	}
	
	public void setDescription(String Desc) {
		description = Desc;
	}
	
	public void setUnitsOnHand(int units) {
		unitsOnHand = units;
	}
	
	public void setPrice(double pris) {
		price = pris;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getUnitsOnHand() {
		return unitsOnHand;
	}
	
	public double getPrice() {
		return price;
	}
}