package net.java.sip.communicator.blocking;

import java.util.NoSuchElementException;

import net.java.sip.communicator.db.BlockDB;

public class BlockClient {

	BlockDB dbManager;
	
	public BlockClient() {
		dbManager = new BlockDB();
	}
	
	public void enableBlock(){
		
	}
	public String getBlocks(String username) {
		return dbManager.getBlocks(username);
	}
	
	/**
	 * 
	 * @param fromUser
	 * @param toUser
	 * @throws NoSuchElementException in case no such user found
	 * @throws RuntimeException in case of a circle forwarding
	 */
	public void blockUser(String fromUser, String toUser) throws NoSuchElementException, RuntimeException{
		dbManager.blockUser(fromUser, toUser);
	}
	
	public void unblockUser(String fromUser, String toUser) {
		dbManager.unblockUser(fromUser, toUser);
	}
}
