package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class TransactionDaoDB implements TransactionDao {
	
	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();


	@Override
	public void send(int source, int destination, float amount,User user) throws SQLException {
	
	
		Connection con = conUtil.getConnection();
	
		Statement stmt = con.createStatement(
		         ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		      
		      String insertEmp1 = "INSERT INTO transactions(source, destination, amount, requserid)"
		      		+ " values("+source+","+destination+","+amount+","+user.getId()+")";
		      String insertEmp2 = "update accounts set balance = balance -"+amount+"where id="+source;
		      String insertEmp3 = "update accounts set balance = balance +"+amount+"where id="+destination;
		      con.setAutoCommit(false);
		      
		      stmt.addBatch(insertEmp1);
		      stmt.addBatch(insertEmp2);
		      stmt.addBatch(insertEmp3);
		
		      stmt.executeBatch();
		      con.setAutoCommit(true);
		
	}
	
	
	@Override
	public void transfer(int source, int destination, float amount, User user) throws SQLException {
		Connection con = conUtil.getConnection();
		
		Statement stmt = con.createStatement(
		         ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		      
		      String insertEmp1 = "INSERT INTO transactions(source, destination, amount, requserid)"
		      		+ " values("+source+","+destination+","+amount+","+user.getId()+")";
		      String insertEmp2 = "update accounts set balance = balance -"+amount+"where id="+source;
		      String insertEmp3 = "update accounts set balance = balance +"+amount+"where id="+destination;
		      con.setAutoCommit(false);
		      
		      stmt.addBatch(insertEmp1);
		      stmt.addBatch(insertEmp2);
		      stmt.addBatch(insertEmp3);
		
		      stmt.executeBatch();
		      con.setAutoCommit(true);
			
	}		
	

	@Override
	public List<Transaction> getAllTransactionsbyUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getAllTransactionsbyAccount(Account acc) {
		// TODO Auto-generated method stub
		return null;
	}


}