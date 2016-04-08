package org.sricharan.ticketService.dto;

public class SeatHold {
	
    public SeatHold(int iD, int levelID, String levelName, double price,
			int rowID, int seatID, int seatHoldID, String customerEmail) {
		super();
		ID = iD;
		this.levelID = levelID;
		this.levelName = levelName;
		this.price = price;
		this.rowID = rowID;
		this.seatID = seatID;
		this.seatHoldID = seatHoldID;
		this.customerEmail = customerEmail;
	}

	private int ID;	
 
	private int levelID;
	
	private String levelName;
	
	private double price;
	
	private int rowID;
	
	private int seatID;
	
	private int seatHoldID; 
	
	private String customerEmail;
	
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getLevelID() {
		return levelID;
	}

	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRowID() {
		return rowID;
	}

	public void setRowID(int rowID) {
		this.rowID = rowID;
	}

	public int getSeatID() {
		return seatID;
	}

	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}

	public int getSeatHoldID() {
		return seatHoldID;
	}

	public void setSeatHoldID(int seatHoldID) {
		this.seatHoldID = seatHoldID;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	
}
