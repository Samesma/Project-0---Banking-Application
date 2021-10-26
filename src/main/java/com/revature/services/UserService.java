package com.revature.services;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.revature.dao.FileIO;
import com.revature.dao.UserDao;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.logging.Logging;
import com.revature.models.User;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserDoesNotExistException;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserService {
	
 
	private UserDao uDao;
	
		
		public UserService(UserDao u) {
			this.uDao = u;
		}
		
		public User signUp(User user) throws UsernameAlreadyExistsException {
			
			User u = new User(user.getFirstName(), user.getLastName(), user.getEmail(), 
					user.getUsername(), user.getPassword(),  user.getSsn(), user.getUsertype());
			
			try {
				uDao.createUser(u);
				Logging.logger.info("New user was registered");
			} catch (SQLException e) {
				Logging.logger.warn("Username created that already exists in the database");
				throw new UsernameAlreadyExistsException();
			}
			
			return u;
		}
		
		public User signIn(String username, String password) throws UserDoesNotExistException, InvalidCredentialsException{
			
			User u = uDao.getUserByUsername(username);
			
			if(u.getId() == 0) {
				Logging.logger.warn("User tried logging in that does not exist");
				throw new UserDoesNotExistException();
			}
			else if(!u.getPassword().equals(password)) {
				Logging.logger.warn("User tried to login with invalid credentials");
				throw new InvalidCredentialsException();
			}
			else {
				Logging.logger.info("User was logged in");
				return u;
			}
			
		}
		
		
		public List<User> getAllUsers()
		{
			List<User> list = new ArrayList<User>();
			
			list = uDao.getAllUsers();
			return list;
		}

		public User getUserbySSN(String ssn) {
			User user = new User();
			  user =uDao.getUserBySSN(ssn);
			  if(user == null)
			  {
					Logging.logger.warn("User does not exist");
					throw new UserDoesNotExistException();
			  }
			  
			  return user;
		}

	
		
	}
