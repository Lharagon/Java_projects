//Programmer: Luis Garcia
//Assignment: Cs990_PayRollExceptions
//Class:      CS990

import java.util.Scanner;
public class PayRollExceptions {

	public static void main(String[] args) {
		String name, id;
		double pay;
		int hrs;
		Scanner keyboard = new Scanner(System.in);
		Payroll roll;
		
		System.out.print("Enter the employee's name: ");
		name = keyboard.nextLine();
		System.out.print("Enter employee number, (ex. 999-M): ");
		id = keyboard.nextLine();
		System.out.print("Enter the employee's hourly rate: ");
		pay = keyboard.nextDouble();
		System.out.print("Enter the number of hours the employee has worked: ");
		hrs = keyboard.nextInt();
		
		try {
			roll = new Payroll(name, id);
			roll.setHours(hrs);
			roll.setPay(pay);
			
			System.out.println("Employees name: " + roll.getName());
			System.out.println("ID: " + roll.getID());
			System.out.println("Hourly Rate: $" + roll.getPay());
			System.out.println("Hours: " + roll.getHours() + " hrs");
			System.out.println("Gross Pay: $" + roll.getGrossPay());
		} catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		} finally {
			keyboard.close();
		}
		

	}

}

class Payroll {
	private String employeeName;
	private String idNumber;
	private double payRate;
	private int hours;
	
	public Payroll(String name, String id) {
		
		setName(name);
		setId(id);
	}
	
	public String getName() {
		return employeeName;
	}
	
	public String getID() {
		return idNumber;
	}
	
	public double getPay() {
		return payRate;
	}
	
	public int getHours() {
		return hours;
	}
	
	public void setName(String name) {
		if(name.length() == 0) {
			throw new IllegalArgumentException("Numericals in ID must be between 0-9 and letters must be between A-M"); //Not clear what this is supposed to say
		}
		employeeName = name;
	}
	
	public void setId(String id) {
//		Check for valid id
		if(id.length() != 0) {
			String[] parts = id.split("-");
			if(parts.length == 2) {
				for(int i = 0; i < parts[0].length(); i++) {
					if(!Character.isDigit(parts[0].charAt(i))) {
						throw new IllegalArgumentException("Numericals in ID must be between 0-9 and letters must be between A-M");
					}
				}
				int value = parts[1].charAt(0);
				if(value >= 'A' && value <= 'M') {
//					if everything is okay assign idNumber
					idNumber = id;
					return;
				}
			}
			
		}
		throw new IllegalArgumentException("Numericals in ID must be between 0-9 and letters must be between A-M");
	}
	
	public void setPay(double pay) {
		if(pay < 0 || pay > 25) {
			throw new IllegalArgumentException("Hourly Rate Cannot be negative or greater than 25");
		}
		payRate = pay;
	}
	
	public void setHours(int hours) {
		if(hours < 0 || hours > 84) {
			throw new IllegalArgumentException("Hours Cannot be negative or greater than 84");
		}
		this.hours = hours;
	}
	
	public double getGrossPay() {
		return payRate * hours;
	}
}

