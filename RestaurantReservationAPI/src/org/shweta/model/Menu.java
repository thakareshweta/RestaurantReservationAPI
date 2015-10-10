package org.shweta.model;

public class Menu {

	private int id;
	private String menu;
	private double price;
	private String description;
	private boolean hotFlag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isHotFlag() {
		return hotFlag;
	}
	public void setHotFlag(boolean hotFlag) {
		this.hotFlag = hotFlag;
	}
	
	
	
	
}
