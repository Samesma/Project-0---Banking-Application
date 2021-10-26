package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.revature.logging.Logging;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class AccountDaoDB implements AccountDao{
	
	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	
	
	public AccountDaoDB()
	{
		
	}


	@Override
	public List<Account> getAllAccounts() {
		
		List<Account> acclist = new ArrayList<Account>();

		try {
			Connection con = conUtil.getConnection();
			
			String sql = "select acc.id, acc.acc_number ,acc.balance ,acc.status ,acc.acc_type,acc.start_date from accounts acc";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			 
			while(rs.next()) {
				Account acc = new Account();
				acc.setId(rs.getInt(1));
				acc.setNumber(rs.getInt(2));
				acc.setBalance(rs.getInt(3));
				acc.setStatus(rs.getString(4));
				acc.setType(rs.getString(5));
				acc.setStartdate(rs.getString(6));
				acclist.add(acc);
				}
			
			return acclist;
			
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		return null;
      		
		

	}


	@Override
	public Account getAccountByAccountNO(int account_number) {
		
		
		
		Account acc = new Account();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "select acc.id, acc.acc_number ,acc.balance ,acc.status ,acc.acc_type, auj.user_id from accounts acc, accounts_users_junction auj where acc.id = auj.account_id \r\n"
					+ "				and acc.acc_number =" + account_number + "";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			List<User> list =  new ArrayList<User>(); 
			while(rs.next()) {
				acc.setId(rs.getInt(1));
				acc.setNumber(rs.getInt(2));
				acc.setBalance(rs.getInt(3));
				acc.setStatus(rs.getString(4));
				acc.setType(rs.getString(5));
				 User u = new User();
				 u.setId(rs.getInt(6));
				list.add(u);
			}
			acc.setUsers(list);
			return acc;
			
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		return null;		
	}


	@Override
	public void getAccountByAccountNO(Account acc) {
		

		
		
	}


	@Override
	public void createAccount(Account acc) throws SQLException {
					
		try {
			
			Connection con = conUtil.getConnection();
			con.setAutoCommit(false);
			
			//Use this syntax for a stored procedure
			String sql = "CALL createaccount(?,?,?,?) ";
			
			PreparedStatement  cs = con.prepareStatement(sql);
			
			//cs.setInt(1, acc.getBalance());
			//cs.registerOutParameter(1, Types.OTHER);
			cs.setFloat(1, acc.getBalance());
			
			cs.setString(2, acc.getType());
			
			cs.setString(3, acc.getStatus());
			
			cs.setInt(4,acc.getUsers().get(0).getId());
			
			cs.execute();
			

			con.setAutoCommit(true);
			
			
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		
	}


	
	@Override
	public void createJointAccount(Account acc) throws SQLException {
					
		try {
			
			Connection con = conUtil.getConnection();
			con.setAutoCommit(false);
			
			//Use this syntax for a stored procedure
			String sql = "CALL createjointaccount(?,?,?,?,?) ";
			
			PreparedStatement  cs = con.prepareStatement(sql);
			
			//cs.setInt(1, acc.getBalance());
			//cs.registerOutParameter(1, Types.OTHER);
			cs.setFloat(1, acc.getBalance());
			
			cs.setString(2, acc.getType());
			
			cs.setString(3, acc.getStatus());
			
			cs.setInt(4,acc.getUsers().get(0).getId());
			cs.setInt(5,acc.getUsers().get(1).getId());
			
			cs.execute();
			

			con.setAutoCommit(true);
			
			
		} catch(SQLException e) {
			throw new SQLException();
		}
		
	}
	
	@Override
	public boolean updateAccount(Account acc) {
		
		try {
		Connection con = conUtil.getConnection();
		
		//We will still create the sql string, but with some small changes
		String sql = "update accounts set status=?, balance=? where id ="+acc.getId();
		PreparedStatement ps;

			ps = con.prepareStatement(sql);
	
		
		ps.setString(1, acc.getStatus());
		ps.setFloat(2, acc.getBalance());
		
		ps.execute();
		return true;
		}
		catch(SQLException e)
		{
		  		Logging.logger.warn(e.getMessage());
		}
		return false;
	}


	@Override
	public void deleteAccount(Account acc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Account> getAccountsByUser(User user) {
		
		List<Account> accList = new ArrayList<Account>(); 
		
		try {
			
			Connection con = conUtil.getConnection();
			con.setAutoCommit(false);
			
			//Use this syntax for a stored function
			String sql = "{?=call getaccountsbyuserid(?)}";
			
			CallableStatement cs = con.prepareCall(sql);
			
			cs.registerOutParameter(1, Types.OTHER);
			cs.setInt(2, user.getId());
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(1);
			
			while(rs.next()) {
				//Result set is based on the return from the function, it returns username, postID, authorID, wallID, content in that order
				Account acc = new Account(rs.getInt(1), rs.getInt(2), rs.getInt(3), 
						rs.getString(4), rs.getString(5),rs.getString(6));
				accList.add(acc);
			}
			cs.close();
			con.setAutoCommit(true);
			return accList;
			
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		
		return null;
	}


}
