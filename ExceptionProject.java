//Programmer: Luis Garcia
//Assignment: Cs990_ExceptionProject
//Class:      CS990

import java.util.Scanner;

//Shows the employee classes and production worker classes in action with exceptions
public class ExceptionProject {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		String name, idNumber, hireDate;
		ProductionWorker worker;
		int shift;
		double rate;
		
		System.out.print("Enter employee name: ");
		name = keyboard.nextLine();
		System.out.print("Enter employee number, (ex. 999-M): ");
		idNumber = keyboard.nextLine();
		System.out.print("Enter employee hire date: ");
		hireDate = keyboard.nextLine();
		System.out.print("Employee shift, (1 = day or 2 = night): ");
		shift = keyboard.nextInt();
		System.out.print("Enter employee hourly pay rate: ");
		rate = keyboard.nextDouble();
		
		
		try {
			worker = new ProductionWorker(shift, rate, name, idNumber, hireDate);
			
			System.out.println("Employee Details");
			System.out.println("Employees name: " + worker.getName());
			System.out.println("Employee Number: " + worker.getEmployeeNumber());
			System.out.println("Hire Date: " + worker.getHireDate());
			System.out.println("Shift: " + worker.getShift());
			System.out.println("Hourly Pay Rate: $" + worker.getPay());
			
		} catch (InvalidEmployeeNumber | InvalidShift | InvalidPayRate e) {
			System.out.println(e.getMessage());
		} finally {
			keyboard.close();
		}
			
	}

}

//Employee class
class Employee {
	private String name;
	private String employeeNumber;
	private String hireDate;
	
	public Employee(String name, String number, String hireDate) throws InvalidEmployeeNumber {
		this.name = name;
		this.hireDate = hireDate;
		setEmployeeNumber(number);
	}
	
	public String getHireDate() {
		return hireDate;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
//	possibly throws exception
	public void setEmployeeNumber(String number) throws InvalidEmployeeNumber {
		if(idNumberIsValid(number)) {
			this.employeeNumber = number;
		} else {
			throw new InvalidEmployeeNumber();
		}
		

	}
	
	public String getEmployeeNumber() {
		return employeeNumber; 
	}
	
//	checks if the id is valid
	private boolean idNumberIsValid(String number) {
		
		if(number.length() != 5) {
			return false;
		}
		
		for(int i = 0; i < 5; i++) {
			char x = number.charAt(i);
			if(i < 3) {
				if(!Character.isDigit(x)) {
					return false;
				}
			} else if (i == 3) {
				if(x != '-') {
					return false;
				}
			} else {
				if(x < 'A' || x > 'M') {
					return false;
				}
			}
		}
		
		return true;
		
	}
}

class ProductionWorker extends Employee {
	
	private int shift;
	private double payRate;
	
//	production worker constructor that calls worker super
	public ProductionWorker(int shift, double rate, String name, String number, String hireDate) throws InvalidEmployeeNumber, InvalidShift, InvalidPayRate {
		super(name, number, hireDate);
		setShift(shift);
		setPay(rate);
	}
	
//	shift validation
	public void setShift(int shift) throws InvalidShift {
		if(shift == 1 || shift == 2) {
			this.shift = shift;
		} else {
			throw new InvalidShift();
		}
		
	}
	
//	pay validation
	public void setPay(double rate) throws InvalidPayRate {
		if(rate < 0) {
			throw new InvalidPayRate();
		}
		this.payRate = rate;
	}
	
	public int getShift() {
		return shift;
	}
	
	public double getPay()  {
		return payRate;
	}
	
}

class InvalidEmployeeNumber extends Exception {
	
	public InvalidEmployeeNumber() {
		super("ERROR: InvalidEmployeeNumber");
	}
	
}

class InvalidShift extends Exception {
	
	public InvalidShift() {
		super("ERROR: InvalidShift");
	}

}

class InvalidPayRate extends Exception {

	public InvalidPayRate() {
		super("ERROR: InvalidPayRate");
	}
	
}
