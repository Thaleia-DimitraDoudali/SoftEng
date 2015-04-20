package gov.nist.sip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlockDB {

	String connectionURL = "jdbc:mysql://localhost:3306/softeng";
	Connection connection = null;
	Statement statement = null;
	String dbuser = "root";
	String dbpass = "";	
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
		} catch (SQLException | ClassNotFoundException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Constructor
	public BlockDB() {
		connect();
	}


	public boolean getBlock(String blocked, String blockedFrom) {
		try {
			if (connection == null)
				connect();
			statement = connection.createStatement();
			String sql = String.format("SELECT blocked FROM blocking where blockedFrom = '" + blockedFrom + "' AND blocked = '" + blocked + "'");
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next()) {
				if (blocked.contentEquals(rs.getString("blocked"))){
					return true;
				}
			} else
				return false;
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} return false;				
	}
}
