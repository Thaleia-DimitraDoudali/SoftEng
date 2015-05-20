package net.java.sip.communicator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

public class BlockDB {
	String connectionURL = "jdbc:mysql://localhost:3306/softeng";
	Connection connection = null;
	Statement statement = null;
	String dbuser = "root";
	String dbpass = "";	
	
	//Constructor
	public BlockDB() {
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

	public String getBlocks(String username) {
		String blocklist = "";
		try {
			if (connection == null) connect();			
			statement = connection.createStatement();
			String sql = String.format("SELECT * FROM blocking WHERE blockedFrom = '" + username + "' ");
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
                if (!rs.getString("blocked").equals(rs.getString("blockedFrom")))
                	blocklist = blocklist + rs.getString("blocked") + "\n";
			}
			if (blocklist.isEmpty()){
				blocklist = "no users blocked\n\t ...yet";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blocklist;
	}

	public void blockUser(String fromUser, String toUser)
			throws NoSuchElementException, RuntimeException {

		if (connection == null) connect();

		// first check if there is such user
		try {
			statement = connection.createStatement();
			String sql = String.format("SELECT * FROM users WHERE username = '" + toUser + "' ");
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			if ((rs == null) || (!rs.next()))
				throw new NoSuchElementException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ready to set the new forward first delete the old if exists and then
		 * set the new
		 */
		try {
			statement = connection.createStatement();
			String sql = String.format("SELECT * FROM blocking where blockedFrom = '" + fromUser + "' AND blocked = '" + toUser + "'");	
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next())
				return;
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		try {
			statement = connection.createStatement();
			String sql = String.format("INSERT INTO blocking (blockedFrom,blocked) VALUES ('" + fromUser + "','" + toUser + "')");
			System.out.println(sql);
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void unblockUser(String fromUser, String toUser) {
		try {
			if (connection == null) connect();
			statement = connection.createStatement();
			String sql = String.format("DELETE FROM blocking WHERE blockedFrom = '" + fromUser + "' AND blocked = '" + toUser + "'");
			System.out.println(sql);
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
