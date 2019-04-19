
public class Car {

    // The year of the Car
    int yearModel;
    // make of the car
    String make;
    // Speed of the car
    int speed;
    
    // constructor method
    public Car(int year, String makeStr) {
        yearModel = year;
        make = makeStr;
        speed = 0;
    }
    
    // returns year model
    public int getYearModel() {
        return yearModel;
    }
    
    // returns make
    public String getMake() {
        return make;
    }
    
    // returns speed
    public int getSpeed() {
        return speed;
    }
    
    // adds 5 to speed
    public void accelerate() {
        speed += 5;
    }
    
    // subtracts 5 from speed
    public void brake() {
        speed -= 5;
    }
	
	
}
