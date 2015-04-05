package gov.nist.sip.db;

import java.sql.*;

public class RegisterDB {

	String connectionURL = "jdbc:mysql://localhost:3306/softeng";
	Connection connection = null;
	Statement statement = null;
	String dbuser = "root";
	String dbpass = "";	
	
	//Constructor
	public RegisterDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Check if there is an existing user with that username
	public boolean checkRegister(String username) {

		try {
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
}
