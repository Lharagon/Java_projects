import java.util.Scanner; // Needed to read user input

public class BankCharges {

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		final double BASE_FEE = 10.00;
		double checkFee;
		int checks;
		
		
		System.out.println("Please enter the number of checks written this month:");
		checks = keyboard.nextInt();
		
		if(checks >= 60)
		{
			checkFee = checks * .04;
		}
		else if(checks >= 40) 
		{
			checkFee = checks * .06;
		}
		else if(checks >= 20) 
		{
			checkFee = checks * .08;
		}
		else if(checks > 0)
		{
			checkFee = checks * .10;
		}
		else 
		{
			checkFee = 0;
		}
		
		System.out.println("This month's service fees are $ " + (BASE_FEE + checkFee));
		
		keyboard.close();
	}

}
