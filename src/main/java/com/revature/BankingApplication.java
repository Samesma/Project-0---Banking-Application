package com.revature;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountDaoDB;
import com.revature.dao.UserDaoDB;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserDoesNotExistException;
import com.revature.logging.Logging;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.ScannerService;
import com.revature.services.UserService;

public class BankingApplication {
	
		
	
	public static void main(String[] args) {
		
		
		initialize();
		
	
		
		try {
		ScannerService sSrv = new ScannerService();
         sSrv.startMenu();  
		
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
					Logging.logger.warn(e.getMessage());
		}
           		

}

	private static void initialize() {
		// TODO Auto-generated method stub
		
	}

}
