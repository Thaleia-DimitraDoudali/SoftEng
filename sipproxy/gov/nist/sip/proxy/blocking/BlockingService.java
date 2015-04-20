package gov.nist.sip.proxy.blocking;


import gov.nist.sip.db.BlockDB;
import gov.nist.sip.proxy.Proxy;

import javax.sip.address.URI;
import javax.sip.header.FromHeader;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;

public class BlockingService {

	Proxy proxy;
	BlockDB dbManager;

	public BlockingService(Proxy p) {
		this.proxy = p;
		dbManager = new BlockDB();
	}

	private String getUsernameFromHeader(ToHeader header) {
		URI uri = header.getAddress().getURI();
		String uriString = uri.toString();
		return uriString.substring(uriString.indexOf("sip:") + 4,
				uriString.indexOf("@"));
	}

	private String getUsernameFromHeader(FromHeader header) {
		URI uri = header.getAddress().getURI();
		String uriString = uri.toString();
		return uriString.substring(uriString.indexOf("sip:") + 4,
				uriString.indexOf("@"));
	}

	public boolean checkIfBlock(Request request) {
		ToHeader header = (ToHeader) request.getHeader(ToHeader.NAME);
		String blockedFrom = getUsernameFromHeader(header);
		FromHeader fromheader = (FromHeader) request.getHeader(FromHeader.NAME);
		String blocked = getUsernameFromHeader(fromheader);
		boolean isBlocked = dbManager.getBlock(blocked, blockedFrom);
		if (isBlocked) {
			return true;
		} else
			return false;
	}
}
