package com.revature.usertests;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.UserDao;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserDoesNotExistException;
import com.revature.models.User;
import com.revature.services.UserService;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;


public class UserServiceTest {
	
	//We want to be able to mock the UserService and UserDao, so we don't actually hit the db
	//Inject Mocks because we are going to inject the mocked uDao functionality into user service
	@InjectMocks
	public UserService uServ;
	
	//Mock because we are going to mock the functionality of the user dao so we dont hit the database when testing
	@Mock
	public UserDao uDao;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testValidLogin() {
		User u1 = new User(1, "test", "user", "test@mail.com", "testuser", "testpass","98152369","Customer");
		
		when(uDao.getUserByUsername(anyString())).thenReturn(u1);
		
		User u = null;
		try {
			u = uServ.signIn("testuser", "testpass");
		} catch (UserDoesNotExistException e) {
			
		assertNull(u);	
		} catch (InvalidCredentialsException e) {
			
			assertNull(u);
		}
		
		assertEquals(u1.getId(), u.getId());
	}
	

	
	
	
}