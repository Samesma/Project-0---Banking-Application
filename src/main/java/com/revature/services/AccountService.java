package com.revature.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.InvalidUpdateAccountException;
import com.revature.logging.Logging;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountService {

	
		private AccountDao accDao;
		
			
			public AccountService(AccountDao accDao) {
				this.accDao = accDao;
			}
	
	
   public Account createAccount(Account acc)
   {
	   try {
		accDao.createAccount(acc);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
				Logging.logger.warn(e.getMessage());
		return acc;
		
	}
	   return acc;
   }
	
	
   public void RemoveAccount(Account acc)
   {
	   
   }
   
   public void ChangeStatus(Account acc)
   {
	   
   }
	
   public List<Account>  getAllAccounts()
   {	
	   List<Account> list;
	   list = accDao.getAllAccounts();
	   return list;	   
   }
   
   public void getBalances(int accountId)
   {
	   
   }

   public void getAccount(int accountId)
   {
	   
   }
   public List<Account> getAccountsByUser(User user) {
	   
	   List<Account> list;
	   list = accDao.getAccountsByUser(user);
	   return list;
	   
   }
   
  
   public Account getAccountByAccountNO(int account_number) {
		
	   	   
		return accDao.getAccountByAccountNO(account_number);
		
	}
   
   public boolean update(Account acc) throws InvalidUpdateAccountException  {
		
   	  
		boolean b = accDao.updateAccount(acc);
		if(b) {
		return true;
		}
		else
		{
			throw new InvalidUpdateAccountException();
			
		}
	
	}


public Account createJointAccount(Account acc) throws SQLException{
	
	  try
	  {
			accDao.createJointAccount(acc);
			  return acc;

	  }
	  catch(Exception e)
	  {
		  
		  throw new SQLException();
	  }
	
	
}



   
}
