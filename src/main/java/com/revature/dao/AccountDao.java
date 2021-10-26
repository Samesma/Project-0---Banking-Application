package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountUser;
import com.revature.models.User;

public interface AccountDao {
	
	List<Account> getAllAccounts();
	
	Account getAccountByAccountNO(int account_number);
	
	void getAccountByAccountNO(Account acc);
	
	void createAccount(Account acc) throws SQLException;
	
	boolean updateAccount(Account acc);
	
	void deleteAccount(Account acc);
	
	List<Account> getAccountsByUser(User user);

	void createJointAccount(Account acc) throws SQLException;
	
}
