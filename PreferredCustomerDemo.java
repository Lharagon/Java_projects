
public class PreferredCustomerDemo {

	public static void main(String[] args) {
		PreferredCustomer preferredcustomer1 = new PreferredCustomer("John Adams", 
		"Los Angeles, CA", "3235331234", 933, true, 400);
		System.out.println(preferredcustomer1.toString() + "\n");
	
		PreferredCustomer preferredcustomer2 = new PreferredCustomer("Susan Adams", 
		"Los Angeles, CA", "3235331234", 933, true, 600);
		System.out.println(preferredcustomer2.toString()+ "\n");
	
		PreferredCustomer preferredcustomer3 = new PreferredCustomer("Mary Adams", 
		"Los Angeles, CA", "3235331234", 933, true, 1100);
		System.out.println(preferredcustomer3.toString()+ "\n");
	
		PreferredCustomer preferredcustomer4 = new PreferredCustomer("Larry Adams", 
		"Los Angeles, CA", "3235331234", 933, true, 1600);
		System.out.println(preferredcustomer4.toString()+ "\n");
	
		PreferredCustomer preferredcustomer5 = new PreferredCustomer("Mony Adams", 
		"Los Angeles, CA", "3235331234", 933, true, 2600);
		System.out.println(preferredcustomer5.toString()+ "\n");

	}

}


class PreferredCustomer extends Customer {
	
	private double discount = 0;
	private double purchaseAmt;
	
	PreferredCustomer(String name, String address, String phone, int id, boolean mailing, double purchase) {
		super(name, address, phone, id, mailing);
		this.purchaseAmt = purchase;
		findAndSetDiscount(purchase);
	}
	
	public void findAndSetDiscount(double amt) {
		double disc;
		if(amt >= 2000) {
			disc = 10;
		} else if (amt >= 1500) {
			disc = 7;
		} else if (amt >= 1000) {
			disc = 6;
		} else if (amt >= 500) {
			disc = 5;
		} else {
			disc = 0;
		}
		
		setDiscount(disc);
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public void setDiscount(double disc) {
		this.discount = disc;
	}
	
	public double getPurchaseAmt() {
		return purchaseAmt;
	}
	
	public void setPurchaseAmt(double amt) {
		this.purchaseAmt = amt;
	}
	
	public String toString() {
		return "Cust Name:" + this.getName() +
				"\nCustID: " + this.getID() +
				"\nTelephone Number: " + this.getPhone() + 
				"\nMail List Status: " + this.getMailing() +
				"\nPurchase " + this.getPurchaseAmt() +
				"\nDiscount Percent " + this.getDiscount();
	}
	
}
