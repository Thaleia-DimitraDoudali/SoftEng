package gov.nist.sip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillingDB {
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
	public BillingDB() {
		connect();
	}
	
	public void addBillingRecord(String caller, String callee) {
		
		try {
			if (connection == null)
				connect();
			statement = connection.createStatement();
			String sql = String.format("INSERT INTO billing set caller = '%s', callee = '%s', start_time = '%s', duration = '%s'",
							caller, callee, System.currentTimeMillis(), -1);
			System.out.println(sql);
			statement.execute(sql);
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public String getBillingRecord(String caller, String callee) {
		try {
			if (connection == null)
				connect();
			statement = connection.createStatement();
			//Since BYE can be sent from either caller or callee, we search both combinations of caller, callee in order to find 
			//the record for the specific call we're on, the one with duration = -1, not set yet. 
			String sql = String.format("SELECT start_time FROM billing WHERE "
					+ "(caller = '%s' AND callee = '%s' AND duration = '-1') OR"
					+ "(caller = '%s' AND callee = '%s' AND duration = '-1')",
							caller, callee, callee, caller);
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			String time = "";
			if (rs.next())
				time = rs.getString("start_time");
			return time;
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	
}
