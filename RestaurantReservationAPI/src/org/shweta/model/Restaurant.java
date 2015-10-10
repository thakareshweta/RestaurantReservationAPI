package org.shweta.model;

public class Restaurant {

	private int ID;
	private String contact;
	private String name;
	private String address1;
	private String city;
	private String state;
	private String country;
	private boolean autoAssign;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean isAutoAssign() {
		return autoAssign;
	}
	public void setAutoAssign(boolean autoAssign) {
		this.autoAssign = autoAssign;
	}	
}