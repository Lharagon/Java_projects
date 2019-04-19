import java.util.Scanner; // Needed to read user input

public class InternetServiceProvider {
	public static void main(String[] args) {
		String pack;
		int hours;
		double base, total;
		
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
			base = 9.95;
			if(hours > 10)
			{
				total = base + ((hours - 10) * 2.0);
			}
			else
			{
				total = base;
			}
			System.out.println("Your total charges are $" + total);
			break;
		case "b":
		case "B":
			base = 13.95;;
			if(hours > 20)
			{
				total = base + ((hours - 20) * 1.0);
			}
			else
			{
				total = base;
			}
			
			System.out.println("Your total charges are $" + total);
			break;
		case "c":
		case "C":
			base = 19.95;
//			There is no cap so, just one price
			total = base;
			
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
