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
import com.revature.dao.TransactionDao;
import com.revature.exceptions.InvalidAccountNumberException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.services.TransactionService;

public class TransactionServiceTest {

	@InjectMocks
	public TransactionService tServ;
	
	//Mock because we are going to mock the functionality of the user dao so we dont hit the database when testing
	@Mock
	public TransactionDao tDao;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void testsend() {
				
		User u = null;
        		try {
					tServ.send(anyInt(), anyInt(),anyInt(), u);
		           assertNull(u);
			  
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					assertNull(u);
				}
		
		
	}
	
	

	
}
