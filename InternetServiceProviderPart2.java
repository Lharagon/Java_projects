import java.util.Scanner; // Needed to read user input

public class InternetServiceProviderPart2 {
	public static void main(String[] args) {
		String pack;
		int hours;
		double total, tempTotal;
		final double BASE_A = 9.95,
					 BASE_B = 13.95,
					 BASE_C = 19.95;
		
//		Open Scanner
		Scanner keyboard = new Scanner(System.in);
		
//		Get user input
		System.out.println("Enter the letter of the package purchased:");
		pack = keyboard.nextLine();
		System.out.println("Enter the number of hours that were used: ");
		hours = keyboard.nextInt();
		
		switch(pack)
		{
		case "a":
		case "A":
			if(hours > 10)
			{
				total = BASE_A + ((hours - 10) * 2.0);
			}
			else
			{
				total = BASE_A;
			}
			System.out.println("Your total charges are $" + total);
			
//			Check if further comparisons are needed
			if(total > BASE_B) 
			{
//				Check for package b savings
				if(hours > 20)
				{
					tempTotal = BASE_B + ((hours - 20) * 1.0);
				}
				else
				{
					tempTotal = BASE_B;
				}
				
				System.out.printf("You would have saved $%.2f if you had gotten package B\n", total - tempTotal);
				
//				Check if savings with c
				if(total > BASE_C)
				{
					System.out.printf("You would have saved $%.2f if you had gotten package C\n", total - BASE_C);
				}
			}
			
			break;
			
		case "b":
		case "B":

			if(hours > 20)
			{
				total = BASE_B + ((hours - 20) * 1.0);
			}
			else
			{
				total = BASE_B;
			}
			
			System.out.println("Your total charges are $" + total);
			
//			Giving package c difference
			if(total > BASE_C)
			{
				System.out.printf("You would have saved $%.2f if you had gotten package C\n", total - BASE_C);
			}
			
			break;
		case "c":
		case "C":
//			There is no cap so, just one price
			total = BASE_C;
			
			System.out.println("Your total charges are $" + total);
			break;
		default:
			System.out.println("That package input was not an option.");
			break;
		}
		
//		Close keyboard
		keyboard.close();
	}
}
