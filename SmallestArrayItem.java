import java.util.Scanner;

public class SmallestArrayItem {

	public static void main(String[] args) {
		int[] list1 = new int[5];
		int[] list2 = new int[5];
		int lowestMatch = 0;
		boolean match = false;
		
		
		Scanner keyboard = new Scanner(System.in);
		
//		Get values for array 1
		System.out.println("Please enter 5 values for array 1");
		for(int i = 0; i < 5; i++) {
			list1[i] = keyboard.nextInt();
		}
		
//		Get values for array 2
		System.out.println("Please enter 5 values for array 2");
		for(int i = 0; i < 5; i++) {
			list2[i] = keyboard.nextInt();
		}
		
//		Outer loop for array 1
		for(int index1 = 0; index1 < list1.length; index1++) {
			
//			Inner loop for array 2
			for(int index2 = 0; index2 < list2.length; index2++) {
				
//				if the two numbers match compare
				if(list1[index1] == list2[index2]) {
					
//					if first match then set immediately
					if(!match) {
						lowestMatch = list1[index1];
						match = true;	
					} 
//					otherwise compare to existing value
					else if(list1[index1] <= lowestMatch) {
						lowestMatch = list1[index1];
						match = true;
					} 
				}
			}
		}
		
		
		if(match) {
			System.out.println("The Smallest match in the array is : " + lowestMatch);
		} else {
			System.out.println("There is no smallest matching integer!");
		}
		
		keyboard.close();
	}

}
