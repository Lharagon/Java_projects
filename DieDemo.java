
public class DieDemo {

	public static void main(String[] args) {
		
		Die userDie = new Die(6);
		Die compDie = new Die(6);
		int userScore = 0;
		int compScore = 0;
		
//		Ten times
		for(int i = 1; i <= 10; i++) {
			
			System.out.println("Roll #" + i + ":");
			
			userDie.roll();
			compDie.roll();
			
			System.out.println("The computer rolled " + compDie.getValue() + " and the user rolled a " + userDie.getValue());
			
//			See who won
			if(compDie.getValue() > userDie.getValue()) {
				System.out.println("The computer won this round!");
				compScore++;
			} 
			else if (compDie.getValue() < userDie.getValue()) {
				System.out.println("The user won this round!");
				userScore++;
			} 
			else {
				System.out.println("Tie!");
			}
			
			System.out.println();

		}
		
		System.out.println("The user won a grand total of " + userScore + " times");
		System.out.println("The computers won a grand total of " + compScore + " times");
		System.out.println();
		
		if(userScore > compScore) {
			System.out.println("The user is the grand winner!!!");
		}
		else if(userScore < compScore) {
			System.out.println("The computer is the grand winner!!!");
		} else {
			System.out.println("There was a tie, there is no grand winner :(");
		}
		
	}

}
