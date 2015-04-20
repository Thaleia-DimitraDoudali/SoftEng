package net.java.sip.communicator.forwarding;

import java.util.NoSuchElementException;

import net.java.sip.communicator.db.ForwardDB;

public class ForwardClient {
	ForwardDB dbManager;
	
	public ForwardClient() {
		dbManager = new ForwardDB();
	}
	
	public String getForward(String username) {
		return dbManager.getForward(username);
	}
	
	/**
	 * 
	 * @param fromUser
	 * @param toUser
	 * @throws NoSuchElementException in case no such user found
	 * @throws RuntimeException in case of a circle forwarding
	 */
	public void setForward(String fromUser, String toUser) throws NoSuchElementException, RuntimeException{
		dbManager.setForward(fromUser, toUser);
	}
	
	public void resetForward(String fromUser) {
		dbManager.resetForward(fromUser);
	}
}
