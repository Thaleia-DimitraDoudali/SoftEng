package net.java.sip.communicator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterDB {
	String connectionURL = "jdbc:mysql://localhost:3306/softeng";
	Connection connection = null;
	Statement statement = null;
	String dbuser = "root";
	String dbpass = "";	
	
	//Constructor
	public RegisterDB() {
		register();
	}
	
	public void register() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
		} catch (SQLException | ClassNotFoundException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Check if there is an existing user with that username
	public boolean checkRegister(String username) {

		try {
			if (connection == null)
				register();
			statement = connection.createStatement();
			String sql = "SELECT userId FROM users where username = '" + username + "'";
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public void registerToDB(String username, String passwd, String email, String creditCard) {
		
		try {
			if (connection == null)
				register();
			statement = connection.createStatement();
			String sql = String
					.format("INSERT INTO users set username = '%s', email = '%s', password = '%s', creditCard = '%s'",
							username, email, passwd, creditCard);
			System.out.println(sql);
			statement.execute(sql);
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
