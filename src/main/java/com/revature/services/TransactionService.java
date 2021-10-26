package com.revature.services;

import java.sql.SQLException;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.TransactionDao;
import com.revature.exceptions.InvalidAccountNumberException;
import com.revature.logging.Logging;
import com.revature.models.Account;
import com.revature.models.User;

public class TransactionService {
	
	private TransactionDao trDao;
	private static AccountService aServ;
	
	
	public TransactionService(TransactionDao trDao) {
		this.trDao = trDao;
		aServ = new AccountService(new AccountDaoDB());
	}

	
	public void transfer(int source, int destination, float amount,User user) throws Exception {
		
		try
		{
	trDao.transfer(source, destination, amount, user);
		}
		catch(SQLException e) {
			Logging.logger.warn(e.getMessage());
		throw new Exception("Your request can't be done.");
		}
    		
	}	
	
	
	public void send(int source, int destination, float amount,User user) throws Exception {
		
		
		
		try
		{
		 Account acc =  aServ.getAccountByAccountNO(destination);
		 if(acc == null) {
			 throw new InvalidAccountNumberException();
		 }
		 else {
	trDao.send(source, acc.getId(), amount, user);
		}
		}
		catch(SQLException e) {
		Logging.logger.warn(e.getMessage());
		throw new Exception("Your request can't be done.");
		}
    		
	}	
	
}
