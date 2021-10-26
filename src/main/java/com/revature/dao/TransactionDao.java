package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public interface TransactionDao {

	public void send(int source,int destination,float amount,User user) throws SQLException;
	public void transfer(int source,int destination,float amount, User user) throws SQLException;
	public List<Transaction> getAllTransactionsbyUser(User user);
	public List<Transaction> getAllTransactionsbyAccount(Account acc);
}
