package net.java.sip.communicator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillingDB {
	String connectionURL = "jdbc:mysql://localhost:3306/softeng";
	Connection connection = null;
	Statement statement = null;
	String dbuser = "root";
	String dbpass = "";	
	
	//Constructor
	public BillingDB() {
		connect();
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
		} catch (SQLException | ClassNotFoundException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getCalls(String username) {
		String history = "Callee\t Duration\t Cost\t\n\n";
		try {
			if (connection == null) connect();			
			statement = connection.createStatement();
			String sql = String.format("SELECT * FROM billing WHERE caller = '" + username + "' ");
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				history = history + rs.getString("callee") + "\t " + rs.getString("duration") 
						+ "\t " + rs.getString("cost") + "\t\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return history;
	}
}
