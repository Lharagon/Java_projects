import java.util.Scanner;

public class CustomerDemo {

	public static void main(String[] args) {
		Customer customer;
		String name, address, phone;
//		boolean mailing;
		int id;
		
		Scanner keyboard = new Scanner(System.in);
		
		 System.out.print("Enter customer Name: ");
		 name = keyboard.nextLine();
		 
		 System.out.print("Enter customer Address: ");
		 address = keyboard.nextLine();
		 
		 System.out.print("Enter customer Telephone Number: ");
		 phone = keyboard.nextLine();
		 
		 System.out.print("Enter CustID: ");
		 id = Integer.parseInt(keyboard.nextLine());
		 
		 customer = new Customer(name, address, phone, id);
		 
		 System.out.printf("Cust Name:%s\n"
		 		+ "CustID: %s\n"
		 		+ "Telephone Number: %s\n"
		 		+ "Mail List Status: %s\n",
		 		customer.getName(),
		 		customer.getID(),
		 		customer.getPhone(),
		 		customer.getMailing());
		
		keyboard.close();
	}

}

class Person {
	String name, address, phone;
	
	public Person(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	
}

class Customer extends Person {
	
	int id;
	boolean mailing = true;
	
	public Customer(String name, String address, String phone, int id) {
		super(name, address, phone);
		this.id = id;
	}
	
	public Customer(String name, String address, String phone, int id, boolean mailing) {
		super(name, address, phone);
		this.id = id;
		this.mailing = mailing;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public void setMailing(boolean mailing) {
		this.mailing = mailing;
	}
	
	public boolean getMailing() {
		return mailing;
	}
}

