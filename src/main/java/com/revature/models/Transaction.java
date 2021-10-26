package com.revature.models;

public class Transaction {

	private int id;
	private int source;
	private int destination;
	private int amount;
	private int requserId;
	private String regdate;

	public Transaction() {
		// TODO Auto-generated constructor stub
	}
	
	public Transaction(int source,int destination,int amount,int requserid)
	{
		this.source = source;
		this.destination = destination;
		this.amount = amount;
		this.requserId = requserid;
	}
	
	public Transaction(int id,int source,int destination,int amount,int requserid, String regdate)
	{
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.amount = amount;
		this.requserId = requserid;
		this.regdate = regdate;
	}
	

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getRequserId() {
		return requserId;
	}
	public void setRequserId(int requserId) {
		this.requserId = requserId;
	}
	
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}


}
