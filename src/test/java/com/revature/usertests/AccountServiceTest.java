package com.revature.usertests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.AccountDao;
import com.revature.models.Account;
import com.revature.services.AccountService;

public class AccountServiceTest {

	@InjectMocks
	public AccountService aServ;
	
	//Mock because we are going to mock the functionality of the user dao so we dont hit the database when testing
	@Mock
	public AccountDao aDao;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void testgetAccountByAccountNO() {
		Account a1 = new Account(1,123456,120,"i","Open","07/10/2021 10:54:39AM");
		when(aDao.getAccountByAccountNO(anyInt())).thenReturn(a1);
		
		Account acc = aServ.getAccountByAccountNO(123456);		
		assertEquals(a1.getNumber(), acc.getNumber());
	}
	
	
	@Test
	public void testcreateAccount() {
		Account a1 = new Account(2,190085,0,"i","Pending","07/10/2021 10:54:39AM");
		Account a2 = aServ.createAccount(a1);
		List<Account> accList = aServ.getAllAccounts();
		
		assertNotNull(a1);

	}
	
	
	@Test
	public void testcreateJointAccount() {
		Account a1 = new Account(3,199085,0,"i","Pending","07/10/2021 10:54:39AM");
		Account a2 = null;
		try {
			a2 = aServ.createJointAccount(a1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			assertNull(a2);
		}
		
		assertNotNull(a2);

	}
	
	@Test
	public void testupdate() {
		
		Account a1 = new Account(4,1990485,0,"i","Pending","07/10/2021 10:54:39AM");
        Account a2 = a1;
        a2.setBalance(100);
		boolean b = aDao.updateAccount(a2);
		if(b) {
		assertNotEquals(a1, a2);
		}
		
	}
}
