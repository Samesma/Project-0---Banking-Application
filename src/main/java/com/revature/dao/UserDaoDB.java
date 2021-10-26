package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.logging.Logging;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDaoDB implements UserDao{
	
	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	
	//Use Statements to talk to our database
	@Override
	public List<User> getAllUsers() {
		
	List<User> userlist = new ArrayList<User>();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "SELECT id,first_name,last_name,email,username,passwd,ssn,usertype FROM users";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setUsername(rs.getString(5));
				user.setPassword(rs.getString(6));
				user.setSsn(rs.getString(7));
				user.setUsertype(rs.getString(8));
				userlist.add(user);
			}
			
			return userlist;
			
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		
		User user = new User();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "SELECT  id,first_name,last_name,email,username,passwd,ssn,usertype FROM users WHERE users.username = '" + username + "'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setUsername(rs.getString(5));
				user.setPassword(rs.getString(6));
				user.setSsn(rs.getString(7));
				user.setUsertype(rs.getString(8));
			}
			
			return user;
			
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		return null;
	}

	//We use prepared statements to precompile the sql query and protect against SQL Injection
	
	@Override
	public void createUser(User u) throws SQLException {
		
		Connection con = conUtil.getConnection();
		
		//We will still create the sql string, but with some small changes
		String sql = "INSERT INTO users(first_name, last_name, email, username, passwd,ssn,usertype) values"
				+ "(?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, u.getFirstName());
		ps.setString(2, u.getLastName());
		ps.setString(3, u.getEmail());
		ps.setString(4, u.getUsername());
		ps.setString(5, u.getPassword());
		ps.setString(6, u.getSsn());
		ps.setString(7, u.getUsertype());
		
		ps.execute();
		
	}

	@Override
	public void updateUser(User u) {
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, username = ?, password = ?"
					+ "WHERE users.id = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getUsername());
			ps.setString(5, u.getPassword());
			ps.setInt(6, u.getId());
			
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		
		
	}

	@Override
	public void deleteUser(User u) {

		try {
			Connection con = conUtil.getConnection();
			String sql = "DELETE FROM users WHERE users.id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, u.getId());
			ps.execute();
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		
		
	}

	@Override
	public User getUserBySSN(String ssn) {
		
		User user = new User();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "SELECT  id,first_name,last_name,email,username,passwd,ssn,usertype FROM users WHERE usertype='Customer' and users.ssn = '" + ssn + "'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setUsername(rs.getString(5));
				user.setPassword(rs.getString(6));
				user.setSsn(rs.getString(7));
				user.setUsertype(rs.getString(8));
			}
			
			return user;
			
		} catch(SQLException e) {
					Logging.logger.warn(e.getMessage());
		}
		return null;
	}
}
