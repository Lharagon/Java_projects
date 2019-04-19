
public class CarDemo {

	public static void main(String[] args) {
		
		Car jaguar = new Car(1999, "Jaguar");
		
		System.out.println("We will now call the accelerate function 5 times");
		
//		Accelerate five times
		for(int i = 0; i < 5; i++) {
			jaguar.accelerate();
			System.out.println("The current speed is: " + jaguar.getSpeed());
		}
		System.out.println();
		System.out.println("We will now call the brake function 5 times");
		
//		Brake five times
		for(int i = 0; i < 5; i++) {
			jaguar.brake();
			System.out.println("The current speed is: " + jaguar.getSpeed());
		}
		
		System.out.println();
		System.out.println("The program will end now.");
		
	}
}
