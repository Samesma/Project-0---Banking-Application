package com.revature.models;

import java.time.LocalDate;
import java.util.List;

public class Account {

	private int id;
	private int number;	
	private float balance;
	private String startdate; 
	private String status;
	private String type;
	private List<User> users;
	
	public Account() {
		
	}
	
	public Account(int id,int number, float deposit, String type,String status,String startdate)
	{
		this.id = id;
	  this.number = number;
	  this.type = type;
	  this.balance = deposit;
	  this.setStatus(status);
      this.startdate = startdate;	  
	}

	public Account(int number, float deposit, String status,String type)
	{
	
	  this.number = number;
	  this.type = type;
	  this.balance = deposit;
	  this.setStatus(status);
	  this.startdate = LocalDate.now().toString();
	}
	
	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public float getBalance() {
		return balance;
	}


	public void setBalance(float balance) {
		this.balance = balance;
	}


	public String getStartdate() {
		return startdate;
	}
	
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Acount [id=" + id + ",account number=" + number + 
				", balance=" + balance + ", type=" + type
				+ ", start date=" + startdate + ", status=" + status +  "]";
	}
	
	
}
