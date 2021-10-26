package com.revature.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.plaf.multi.MultiInternalFrameUI;

import com.revature.dao.AccountDaoDB;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoDB;
import com.revature.dao.UserDaoDB;
import com.revature.exceptions.InvalidAccountNumberException;
import com.revature.exceptions.InvalidAcountBalanceException;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.InvalidUpdateAccountException;
import com.revature.logging.Logging;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public class ScannerService {

	
private static Scanner in;
private static UserService uServ;
private static AccountService accServ;
private static TransactionService tServ;
private boolean done = false;
private User currentUser = null;
	
	public ScannerService() {
		in= new Scanner(System.in);	
		uServ = new UserService(new UserDaoDB());
		accServ = new AccountService(new AccountDaoDB());
		tServ =  new TransactionService(new TransactionDaoDB());
	}
	
	
	
	public void startMenu()
	{
	
	
	

	
	while(!done) {
		
		if(currentUser == null) {
			
			
	        
			System.out.println("\t\t\t********Banking Appilication- Version 1.0.0********\n\n");
			System.out.println("Login or Signup? Press 1 to Login, Press 2 to SignUp");
			int choice = Integer.parseInt(in.nextLine());
			if(choice == 1) {
				
                    if(login()) {
                    	showUserMenu();
        			}
				
				
				}
			
			else {
				System.out.print("Please enter you first name: ");
				String first = in.nextLine();
				System.out.print("Please enter your last name: ");
				String last = in.nextLine();
				System.out.print("Please enter you email: ");
				String email = in.nextLine();
				System.out.print("Please enter you SSN: ");
				String ssn = in.nextLine();
				System.out.print("Please enter your username: ");
				String username = in.nextLine();
				System.out.print("Please enter your password: ");
				String password = in.nextLine();
				
				String usertype = "Customer";
				
				User u = new User (first, last, email,username, password, ssn,  usertype);
				
				try {
					 u = uServ.signUp(u);
					System.out.println("You may now sign in with the username: " + u.getUsername());
				} catch(Exception e) {
							Logging.logger.warn(e.getMessage());
					System.out.println("Sorry the username is already taken");
					System.out.println("Please try signing up with a different one");
				}
			}
				continue;
			}
	}	
}

	private void showUserMenu() {
		// Get user type then show the corresponding menu
		switch(currentUser.getUsertype().toUpperCase().trim())
		{
		case "SYS_ADMIN":
			sysadminMenu();
			break;
		case "CUSTOMER":
			
			customerMenu();
			break;
		case "BANK_ADMIN":
			bankAdminMenu();
			break;
		case "EMPLOYEE"://":
			employeeMenu();
			break;
		default:
			break;
		
		}

	}


	private void employeeMenu() {
		
		System.out.println("Employee menu:");
		System.out.println("View all accounts=1,View all users' info=2,sign out=3");
		int c = Integer.parseInt(in.nextLine());
		if(c==3)
		{
			currentUser = null;
			return;
		}
    	if(c == 1)
    	{
    		
    		System.out.println("All bank accounts' details:");
			List<Account> acclist = accServ.getAllAccounts();
			if(acclist.size() == 0)
			{
				System.out.println("\tThere are no accounts.");	
				employeeMenu();
				return;
			}

			for(Account acc1:acclist)
			{
				System.out.println("\t***\t***\t***\t***\t***\t***\t***\t***\t***\t***\t***");
				System.out.println("\t"+acc1);

			}

    		System.out.println("Would you like to perform a transaction over the accounts?(Y/N)");
    		String st = in.nextLine();
    		if(st.toUpperCase().equals("Y"))
    		{
    			System.out.println("Choose one service:");
    			System.out.println("1. Approving/denying accounts");
    			System.out.println("2. retuen to main menu");
    			c = Integer.parseInt(in.nextLine());
    			if(c == 1)
    			{
    				System.out.println("Enter the account number to approve/deny:");
					int acc = Integer.parseInt(in.nextLine());
					Account account=acclist.stream().filter(a-> a.getNumber() == acc && a.getStatus().equals("Pending") ).findFirst().orElse(null);
					if(account == null)
					{
					System.out.println("The account number is invalid or the status is not 'Pending'.");
					in.nextLine();
					employeeMenu();
					return;
					}
					else {
						System.out.println("Approve=1,Deny=2");
						int status = Integer.parseInt(in.nextLine());
						account.setStatus((status==1)?"Open":"Denied");
						
					try {
						accServ.update(account);
						System.out.println("The account is updated.Press enter to continue...");
						in.nextLine();
					} catch (Exception e) {
						// TODO Auto-generated catch block
								Logging.logger.warn(e.getMessage());
					}
					employeeMenu();
					return;
					}
				}
    			else if(c == 2)
				{
					
    				employeeMenu();
					return;

					}
				}
    		else {
				
				employeeMenu();
				return;
    		}
    	}
    	else if(c==2)
    	{
    		List<User> ulist = uServ.getAllUsers();
			if(ulist.size() == 0)
			{
				System.out.println("\tThere are no Users");	
				employeeMenu();
				return;
			}

			for(User u1:ulist)
			{
				if(u1.getUsertype().equals("Customer"))
				{
				System.out.println("\t***\t***\t***\t***\t***\t***\t***\t***\t***\t***\t***");
				System.out.println("\t"+u1);
				}
			}
			System.out.println("Press enter to continue...");
			in.nextLine();
			employeeMenu();
			return;
    	}
	}


	@SuppressWarnings("finally")
	private void bankAdminMenu() {
		
		System.out.println("Bank admin menu:");
		System.out.println("View all accounts=1,sign out=2");
		int c = Integer.parseInt(in.nextLine());
		if(c==2)
		{
			currentUser = null;
			return;
		}
    	if(c == 1)
    	{
    		
    		System.out.println("All bank accounts' details:");
			List<Account> acclist = accServ.getAllAccounts();
			if(acclist.size() == 0)
			{
				System.out.println("\tThere are no accounts.");	
				bankAdminMenu();
				return;
			}

			for(Account acc1:acclist)
			{
				System.out.println("\t***\t***\t***\t***\t***\t***\t***\t***\t***\t***\t***");
				System.out.println("\t"+acc1);

			}

    		System.out.println("Would you like do an operation over the accounts?(Y/N)");
    		String st = in.nextLine();
    		if(st.toUpperCase().equals("Y"))
    		{
    			System.out.println("Choose one service:");
    			System.out.println("1. Transfer money between the accounts");
    			System.out.println("2. Approving/denying accounts");
    			System.out.println("3. Canceling accounts");
    			System.out.println("4. return to main menu");
    			c = Integer.parseInt(in.nextLine());
    			if(c == 1)
    			{
    			    System.out.println("Enter the source account number:");
    				int accSrc = Integer.parseInt(in.nextLine());
    				int accSrcId;
    				
    				try {
    				if(acclist.stream().filter(a ->a.getNumber() == accSrc && a.getStatus().equals("Open")).findFirst().equals(Optional.empty()))
    				{
    					throw new InvalidAccountNumberException();
    				}
    				else
    					{
    					accSrcId =acclist.stream().filter(a ->a.getNumber() == accSrc).findFirst().get().getId();
    					}
    				
    					System.out.println("Enter your destination account number:");
    				int accDst = Integer.parseInt(in.nextLine());
    				int accDstId;
    				if(acclist.stream().filter(a ->a.getNumber() == accDst && a.getStatus().equals("Open")).findFirst().equals(Optional.empty()))
    				{
    					throw new InvalidAccountNumberException();
    				}
    				else
    				{
    					accDstId = acclist.stream().filter(a ->a.getNumber() == accDst).findFirst().get().getId();
    				}
    				System.out.println("Enter the amount to transfer:");
    				float amount = Float.parseFloat(in.nextLine());
    				if(amount<=0 || acclist.stream().filter(a ->a.getNumber() == accSrc && a.getBalance() - amount >=0).findFirst().equals(Optional.empty()))
    				{
    					throw new InvalidAcountBalanceException();
    				}
    				
    				tServ.transfer(accSrcId,accDstId,amount,currentUser);
    				System.out.println("Your request has been done. Press Enter to continue..:");
    				}
    		
    				catch(InvalidAccountNumberException e)
    				{
    				System.out.println(e.getMessage());
    				}
    				catch(InvalidAcountBalanceException e)
    				{
    				System.out.println(e.getMessage());
    				}
    				
    			catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					
				}
    				finally {
    					in.nextLine();
    					bankAdminMenu();
        				return;
    			}
    		}
    			else if(c == 2)
				{
					System.out.println("Enter the account number to approve/deny:");
					int acc = Integer.parseInt(in.nextLine());
					Account account=acclist.stream().filter(a-> a.getNumber() == acc && a.getStatus().equals("Pending") ).findFirst().orElse(null);
					if(account == null)
					{
					System.out.println("The account number is invalid or the status is not 'Pending'");
					bankAdminMenu();
					return;
					}
					else {
						System.out.println("Approve=1,Deny=2");
						int status = Integer.parseInt(in.nextLine());
						account.setStatus((status==1)?"Open":"Denied");
						
					try {
						accServ.update(account);
						System.out.println("The account is updated.Press enter to continue...");
						in.nextLine();
					} catch (Exception e) {
						// TODO Auto-generated catch block
								Logging.logger.warn(e.getMessage());
					}
					bankAdminMenu();
					return;

					}
				}
    			else if(c == 3)
				{
					System.out.println("Enter the account number to cancel:");
					int acc = Integer.parseInt(in.nextLine());
					Account account=acclist.stream().filter(a-> a.getNumber() == acc && a.getStatus().equals("Open")).findFirst().orElse(null);
					if(account == null)
					{
					System.out.println("The account number is invalid or the status is not 'Open'");
					bankAdminMenu();
					return;
					}
					else {
					
						account.setStatus("Canceled");
						account.setBalance(0);
						
					try {
						accServ.update(account);
						System.out.println("The account is cancelled.Press enter to continue...");
						in.nextLine();
					} catch (Exception e) {
						// TODO Auto-generated catch block
								Logging.logger.warn(e.getMessage());
					}
					bankAdminMenu();
					return;

					}
				}
    			else if(c == 4)
    			{
    				bankAdminMenu();
					return;
    			}

    		}
    		else
    		{
    			bankAdminMenu();
				return;
    		}
    	}
	}
	
	@SuppressWarnings({ "finally" })
	private void customerMenu() {
		
		System.out.println("Customer menu:");
		System.out.println("View your accounts=1,Open a new account=2,sign out=3");
		//in.nextLine();
		int c =Integer.parseInt(in.nextLine());
		if(c==3)
		{
			currentUser = null;
			return;
		}
    	if(c == 1)
    	{
    		
    		System.out.println("Your accounts' details:");
			List<Account> acclist = accServ.getAccountsByUser(currentUser);
			if(acclist.size() == 0)
			{
				System.out.println("\tYou don't have any account with us yet!");	
				customerMenu();
				return;
			}
			for(Account acc1:acclist)
			{
				System.out.println("\t***\t***\t***\t***\t***\t***\t***\t***\t***\t***\t***");
				System.out.println("\t"+acc1);

			}
    		System.out.println("Would you like do a new transaction?(Y/N)");
    		String st = in.nextLine();
    		if(st.toUpperCase().equals("Y"))
    		{
    			System.out.println("Choose one service:");
    			System.out.println("1. Transfer money between your account");
    			System.out.println("2. Send money to others");
    			System.out.println("3. Close your account");
    			System.out.println("4. Back to main menu");
    			c = Integer.parseInt(in.nextLine());
    			if(c == 1)
    			{
    				if(acclist.size()<2) {
    					System.out.println("You just have one account!");
    					customerMenu();
    					return;
    				}
    				System.out.println("Enter your source account number:");
    				     int accSrc = Integer.parseInt(in.nextLine());
    				int accSrcId;
    				
    				try {
    				if(acclist.stream().filter(a ->a.getNumber() == accSrc && a.getStatus().equals("Open")).findFirst().equals(Optional.empty()))
    				{
    					throw new InvalidAccountNumberException();
    				}
    				else
    					{
    					accSrcId =acclist.stream().filter(a ->a.getNumber() == accSrc).findFirst().get().getId();
    					}
    				
    				System.out.println("Enter your destination account number:");
    				int accDst = Integer.parseInt(in.nextLine());
    				int accDstId;
    				if(acclist.stream().filter(a ->a.getNumber() == accDst && a.getStatus().equals("Open")).findFirst().equals(Optional.empty()))
    				{
    					throw new InvalidAccountNumberException();
    				}
    				else
    				{
    					accDstId = acclist.stream().filter(a ->a.getNumber() == accDst).findFirst().get().getId();
    				}
    				System.out.println("Enter the amount to transfer:");
    				float amount = Float.parseFloat(in.nextLine());
    				if(amount<=0 || acclist.stream().filter(a ->a.getNumber() == accSrc && a.getBalance() - amount >=0).findFirst().equals(Optional.empty()))
    				{
    					throw new InvalidAcountBalanceException();
    				}
    				tServ.transfer(accSrcId,accDstId,amount,currentUser);
    				System.out.println("Your request has been done. Press Enter to continue..:");
    				
    				}
    				catch(InvalidAccountNumberException e)
    				{
        				System.out.println(e.getMessage());

    					
    				}
    				catch(InvalidAcountBalanceException e)
    				{
    					System.out.println(e.getMessage());
    				} catch (Exception e) {
						// TODO Auto-generated catch block
    					System.out.println(e.getMessage());
						
					}
    				finally
    				{
    					in.nextLine();
        				customerMenu();
        				return;
    				}
    				
    				
    			}
    			else if(c == 2)
    			{
    				System.out.println("Enter your source account number:");
    				int accSrc = Integer.parseInt(in.nextLine());
    				int accSrcId;
    				
    				try {
    				if(acclist.stream().filter(a ->a.getNumber() == accSrc && a.getStatus().equals("Open")).findFirst().equals(Optional.empty()))
    				{
    					throw new InvalidAccountNumberException();
    				}
    				else
    					{
    					accSrcId =acclist.stream().filter(a ->a.getNumber() == accSrc).findFirst().get().getId();
    					}
    				
    				System.out.println("Enter your destination account number:");
    				int accDst = Integer.parseInt(in.nextLine());
    				System.out.println("Enter the amount to send:");
    				float amount = Float.parseFloat(in.nextLine());
    				if(amount<=0 || acclist.stream().filter(a ->a.getNumber() == accSrc && a.getBalance() - amount >=0).findFirst().equals(Optional.empty()))
    				{
    					throw new InvalidAcountBalanceException();
    				}
    				tServ.send(accSrcId,accDst,amount,currentUser);
    				System.out.println("Your request has been done. Press Enter to continue..:");
    				
    				}
    				catch(InvalidAccountNumberException e)
    				{
        				System.out.println(e.getMessage());
        				
    					
    				}
    				catch(InvalidAcountBalanceException e)
    				{
    					System.out.println(e.getMessage());
    					
    				} catch (Exception e) {
						// TODO Auto-generated catch block
    					System.out.println(e.getMessage());
    					
					}
    				finally
    				{
    					in.nextLine();
        				customerMenu();
        				return;
    				}
    				
    			}
    				else if(c == 3)
    				{
    					System.out.println("Enter the account number to cancel:");
    					int acc = Integer.parseInt(in.nextLine());
    					Account account=acclist.stream().filter(a-> a.getNumber() == acc && a.getStatus().equals("Open")).findFirst().orElse(null);
    					if(account == null)
    					{
    					System.out.println("The account number is invalid or account is not open.press enter to continue");
    					customerMenu();
    					return;
    					}
    					else {
    						account.setBalance(0);
    						account.setStatus("Canceled");
    					try {
							accServ.update(account);
							System.out.println("The account is canceled.Press enter to continue...");
							in.nextLine();
						} catch (Exception e) {
							// TODO Auto-generated catch block
									Logging.logger.warn(e.getMessage());
						}
    					customerMenu();
    					return;

    					}
    				}
    				else
    				{
    					customerMenu();
    					return;
    					
    				}
    			
    		}else
    		{
    			customerMenu();
				return;

    		}

			
    		
    	}else {
    	
			System.out.println("Would you like to open an individual or joint account?(1=individual,2=joint)");
			 c = Integer.parseInt(in.nextLine());

			if(c == 1)
			{
				
				 
				Account acc = new Account();
				acc.setBalance(0);
				acc.setType("i");
				acc.setStatus("Pending");
				List<User> list = new ArrayList<User>();
				list.add(currentUser);
				acc.setUsers(list); 
				accServ.createAccount(acc);
				System.out.println("we got your request to open a new individual account, we will check your request and inform you about that.");
				System.out.println("Press enter to continue...");
				in.nextLine();
				customerMenu();
				return;
						}
			else
			{
				System.out.println("Thank you! for openning a new joint acoount, we need the other party information");
				System.out.println("Enter the second individual's SSN");							
				String ssn = in.nextLine();
			
			try {
			User user= uServ.getUserbySSN(ssn);
			
			Account acc = new Account();
			acc.setBalance(0);
			acc.setType("j");
			acc.setStatus("Pending");
			List<User> list = new ArrayList<User>();
			list.add(currentUser);
			list.add(user);
			acc.setUsers(list); 
			accServ.createJointAccount(acc);
			
			System.out.println("we recieved your request to open a new joint accont, we will check your request and inform you about that.");    

			}
			catch(Exception e)
			{
				System.out.println("There is no user with this SSN number, first the other party needs to sign up!");    

			}
			finally {
				System.out.println("Press enter to continue...");
				in.nextLine();
				customerMenu();
				return;
			}
			}
    	}
	}

	private void sysadminMenu() {
		// TODO Auto-generated method stub
		System.out.println("System admin menu:");
		
	}



	private boolean login() {
		
		System.out.print("Please enter your username: ");
		String username = in.nextLine();
		System.out.print("Please enter your password: ");
		String password = in.nextLine();
		
			try {
				currentUser = uServ.signIn(username, password);
				System.out.println("Welcome " + currentUser.getFirstName()+" "+ currentUser.getLastName());
				return true;
			} catch(Exception e) 
			{
				System.out.println("Username or password was incorrect, try again!");
			}
		return false;
		
	}
}