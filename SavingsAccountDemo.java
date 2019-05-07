//Programmer: Luis H. Garcia

import java.util.Scanner;

public class SavingsAccountDemo {

	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);
		char choice;
		double amt, rate;
		SavingsAccount saveAccount;
		
		System.out.print("Enter beginning balance :$");
		amt = toValidDouble(keyboard.nextLine());
		System.out.print("Enter interest rate(whole number) :%");
		rate = toValidDouble(keyboard.nextLine());
		
//		Initialize savings Account
		saveAccount = new SavingsAccount(amt, rate);
		
//		Program Menu
		do {
			showMainMenu();
			choice = toValidChar(keyboard.nextLine()); // Get a char from user
			switch(choice) {
			case 'D':
				System.out.print("Enter the amount you want to Deposit :$");
				amt = toValidDouble(keyboard.nextLine());
				saveAccount.deposit(amt);
				break;
			case 'W':
				try {
					if(saveAccount.getActive()) {
						System.out.print("Enter the amount you want to withdraw :$");
						amt = toValidDouble(keyboard.nextLine());
						saveAccount.withdraw(amt);
					} else {
						throw new Exception("Your account is INACTIVE");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				break;
			case 'B':
				System.out.printf("Your Balance is: %s\n", saveAccount.getFormatBalance());
				break;
			case 'M':
				saveAccount.monthlyProcess();
				break;
			case 'E':
				System.out.printf("Balance : $%s\n", saveAccount.getFormatBalance());
				System.out.println("Thank you. Bye");
				break;
			default:
				System.out.println("Invalid choice. Try again\n");
			}
			
		} while(choice != 'E');
		
		keyboard.close();
		
	}
	
	public static void showMainMenu() {
		System.out.println("Enter D to deposit\n" + 
				"Enter W to Withdraw\n" + 
				"Enter B for Balance\n" + 
				"Enter M for Monthly Process\n" + 
				"Enter E to Exit");
	}
	
//	Returns a valid character
	public static char toValidChar(String choice) {
		try {
			return Character.toUpperCase(choice.charAt(0));
		} catch (Exception e) {
			return 'Z';
		}
	}
	
//	Returns a valid Double
	public static double toValidDouble(String amt) {
		try {
			return Double.parseDouble(amt);
		} catch (Exception e) {
			return -1;
		}
	}

}

abstract class BankAccount {
	double balance;
	int deposits;
	int withdraws;
	double annualInterest;
	
	public BankAccount(double bal, double rate) {
		balance = bal;
		annualInterest = rate / 100;
	}
	
	public void deposit(double amount) throws Exception {
		if(amount <= 0) {
			throw new Exception("Must enter positive value\n");
		}
		balance += amount;
		deposits++;

	}
	
	public void withdraw(double amount) throws Exception {
		if(amount <= 0) {
			throw new Exception("Must enter positive value\n");
		}
		
		if(amount >= balance) {
			throw new Exception("Transaction declined!! This transaction will cause overdraft or zero balance\n");
		}
		balance -= amount;
		withdraws++;
	}
	
	public void calcInterest() {
		double monthRate = (annualInterest / 12);
		double monthInterest = balance * monthRate;
		balance += monthInterest;
	}
	
	public void monthlyProcess() {
		calcInterest();
		deposits = 0;
		withdraws = 0;
	}
	
	public double getBalance() {
		return balance;
	}
	
//	Returns a formated string of balance
	public String getFormatBalance() {
		return String.format("%.2f", getBalance());
	}
}

class SavingsAccount extends BankAccount {
	private boolean active = true;
	
	public SavingsAccount(double bal, double rate) {
		super(bal, rate);
	}
	
	public boolean getActive() {
		return active;
	}

	@Override
	public void withdraw(double amount) throws Exception {
		try {
			if(active) {
				super.withdraw(amount);
				if(withdraws > 4) {
					balance--;
				}
				if(deactivate()) {
					active = false;
					System.out.println("Your balance is less than minimum balance. Your account is now INACTIVE ");
				}
				if(withdraws > 4) {
					System.out.println("You have exceeded monthly limit of withdrawals. Fee of $1 charged\n");
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
	
	@Override
	public void deposit(double amount) throws Exception {
		try {
			if(!active && (balance + amount > 25)) {
				active = true;
				System.out.println("Your account is now ACTIVE");
			}
			super.deposit(amount);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
	
	public void monthlyProcess() {
		super.monthlyProcess();
		System.out.printf("Your Balance after Monthly process is: %s\n", getFormatBalance());
	}
	
	public boolean deactivate() {
		if(balance < 25) {
			return true;
		}
		return false;
	}
	
	
}