package gov.nist.sip.db;

import gov.nist.sip.proxy.billing.BillingObject;

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
	
	public void setBillingRecord(int id, long duration, double cost) {
		try {
			if (connection == null)
				connect();
			statement = connection.createStatement();
			String sql = String.format("UPDATE billing SET duration = %d, cost = %.2f WHERE id = %d LIMIT 1;", duration, cost, id);
			System.out.println(sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String getPlan(String caller) {
		String plan = "";
		try {
			statement = connection.createStatement();
			String sql = "SELECT plan FROM users where username = '" + caller + "'";
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next())
				plan = rs.getString("plan");
		} catch (SQLException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
		return plan;
	}
	
	public BillingObject getBillingRecord(String caller, String callee) {
		try {
			if (connection == null)
				connect();
			statement = connection.createStatement();
			//Since BYE can be sent from either caller or callee, we search both combinations of caller, callee in order to find 
			//the record for the specific call we're on, the one with duration = -1, not set yet. 
			String sql = String.format("SELECT * FROM billing WHERE "
					+ "(caller = '%s' AND callee = '%s' AND duration = '-1') OR"
					+ "(caller = '%s' AND callee = '%s' AND duration = '-1')",
							caller, callee, callee, caller);
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			int id = -1;
			Long time = (long) -1;
			String username = "";
			BillingObject obj = null;
			if (rs.next()) {
				id = rs.getInt("id");
				time = rs.getLong("start_time");
				username = rs.getString("caller");
				obj = new BillingObject(id, time, username);
			}
			return obj;
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	
}
