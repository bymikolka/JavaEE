package by.javacourcee.hotel.entities;

public class Guest {

    private int id;
    private String name;
    private String address;
    private String city;
    private String phone;
    private CreditCard cardDetails;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getPhone() {
		return phone;
	}
	public CreditCard getCardDetails() {
		return cardDetails;
	}
	public Guest(int id, String name, String address, String city, String phone, CreditCard cardDetails) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.cardDetails = cardDetails;
	}
	
    
    
    
}
