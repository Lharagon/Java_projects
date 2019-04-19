//Programmer: Luis Garcia
//Assignment: Cs990_Homework7_SecondClass_CashRegister
//Class:      CS990

import java.util.Scanner;

public class ParkingCarSimulator {

	public static void main(String[] args) {
		PoliceOfficer officer;
		ParkedCar car;
		ParkingMeter meter;
		String oName, oBadge, cMake, cModel, cColor, cLiscense;
		int cMins, minsPurchased;
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Enter the officer's name");
		oName = keyboard.nextLine();
		System.out.println("Enter officer's badge number");
		oBadge = keyboard.nextLine();
//		Create a new police officer
		officer = new PoliceOfficer(oName, oBadge);
		
		System.out.println("Enter the car's make");
		cMake = keyboard.nextLine();
		System.out.println("Enter the car's model");
		cModel = keyboard.nextLine();
		System.out.println("Enter the car's color");
		cColor = keyboard.nextLine();
		System.out.println("Enter the car's liscense number");
		cLiscense = keyboard.nextLine();
		System.out.println("Enter Minutes on car");
		cMins = keyboard.nextInt();
		while(cMins <= 0) {
			System.out.println("Invalid Entry. Please try again.");
			cMins = keyboard.nextInt();
		}
//		Create a new parked car
		car = new ParkedCar(cMake, cModel, cColor, cLiscense, cMins);
		
		System.out.println("Enter the number of minutes purchased on the meter");
		minsPurchased = keyboard.nextInt();
		while(minsPurchased <= 0) {
			System.out.println("Invalid Entry. Please try again.");
			minsPurchased = keyboard.nextInt();
		}
//		Create a new parking meter
		meter = new ParkingMeter(minsPurchased);
		
//		officer checks if the time expired
		if(officer.checkIfExpired(car.getMinutes(), meter.getTimeRemaining())) {
			System.out.println("Car parking time has expired.");
			ParkingTicket ticket = officer.issueTicket(meter, car);
			System.out.println(ticket.toString());
		} else {
			System.out.println("The car parking minutes are valid");
		}
		
		keyboard.close();
	}

}

//Parked Car
class ParkedCar {
	private String make, model, color, license;
	private int minutes;
	
//	Constructor
	public ParkedCar(String mak, String mod, String col, String lic, int mins){
		make = mak;
		model = mod;
		color = col;
		license = lic;
		minutes = mins;
	}
	
//	copy constructor
	public ParkedCar(ParkedCar oldCar){
		make = oldCar.make;
		model = oldCar.model;
		color = oldCar.color;
		license = oldCar.license;
		minutes = oldCar.minutes;
	}
	
	public String getMake() {
		return make;
	}
	
	public String getModel() {
		return model;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getLicense() {
		return license;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public String toString() {
		return "Make: " + make +
			   "\nModel: " + model +
			   "\nColor: " + color +
			   "\nLiscense Number: " + license;
	}
}

//Parking Meter class
class ParkingMeter {
	private int timeRemaining;
	
	public ParkingMeter(int time) {
		timeRemaining = time;
	}
	
	public ParkingMeter(ParkingMeter meter) {
		timeRemaining = meter.timeRemaining;
	}
	
	public int getTimeRemaining() {
		return timeRemaining;
	}
}

//Parking ticket class
class ParkingTicket {
	final int BASEFINE = 25;
	final int HOURLY_FINE = 10;
	private ParkedCar parkedCar;
	private PoliceOfficer policeOfficer;
	private ParkingMeter parkingMeter;
	double fine;
	
//	creates a new parking ticket
	public ParkingTicket(ParkedCar car, PoliceOfficer officer, ParkingMeter meter) {
		parkedCar = new ParkedCar(car);
		policeOfficer = new PoliceOfficer(officer);
		parkingMeter = new ParkingMeter(meter);
	}
	
//	Gets the fine amount
	public double getFineAmount() {
		int difference = parkedCar.getMinutes() - parkingMeter.getTimeRemaining();
//		base fine for first 60 minutes
		if(difference <= 60) {
			return BASEFINE; 
		}
		
//		takes away initial 60 minutes
		difference -= 60;
//		integer division to get fine amount
		fine = ((difference/60 + 1) * HOURLY_FINE) + BASEFINE;
		return fine;
	}
	
	public String toString() {
		return "Ticket data:\n" +
			   parkedCar.toString() + "\n" +
			   policeOfficer.toString() + "\n" +
			   "Fine: " + getFineAmount();
	}
	
}

class PoliceOfficer {
	private String name, badge;
	
	public PoliceOfficer(String name, String badge) {
		this.name = name;
		this.badge = badge;
	}
	
	public PoliceOfficer(PoliceOfficer officer) {
		this.name = officer.name;
		this.badge = officer.badge;
	}
	
//	Checks if the time is expired
	public Boolean checkIfExpired(int carTime, int meterTime) {
		if(carTime > meterTime) {
			return true;
		}
		return false;
	}
	
//	issues a new parking ticket
	public ParkingTicket issueTicket(ParkingMeter meter, ParkedCar car) {
		return new ParkingTicket(car, this, meter);
	}
	
	public String toString() {
		return "Officer Name: " + name +
			   "\nBadge Number: " + badge;
	}
}