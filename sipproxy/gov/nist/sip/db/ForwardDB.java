package gov.nist.sip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForwardDB {
	
	String connectionURL = "jdbc:mysql://localhost:3306/softeng";
	Connection connection = null;
	PreparedStatement statement = null;
	String dbuser = "root";
	String dbpass = "";	
	
	public void connect () {
			try {
				connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public ForwardDB (){
		connect();
	}
	
	public String getForward (String caller) {
		String forward_to = null;
		
		try {
			
			statement = connection.prepareStatement("SELECT forward_to FROM forwarding where forward_from = ?");
			String tempFrom = caller; 
			while (true) {
				statement.setString(1, tempFrom);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					forward_to = rs.getString("forward_to");
					tempFrom = forward_to;
				} else
					break;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return forward_to;
	}
		
}
