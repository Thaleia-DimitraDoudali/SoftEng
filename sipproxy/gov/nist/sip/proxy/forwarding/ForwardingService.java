package gov.nist.sip.proxy.forwarding;

import java.text.ParseException;

import gov.nist.sip.db.ForwardDB;
import gov.nist.sip.proxy.Proxy;

import javax.sip.address.URI;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;

public class ForwardingService {
	
	Proxy proxy;
	ForwardDB dbManager;

	public ForwardingService(Proxy p) {
		this.proxy = p;
		dbManager = new ForwardDB();
	}
	
	private String getUsernameFromHeader(ToHeader header) {
		URI uri = header.getAddress().getURI();
		String uriString = uri.toString();
		return uriString.substring(uriString.indexOf("sip:") + 4,
				uriString.indexOf("@"));
	}
	
	public Request checkAndSetForwarding(Request request) {
		ToHeader header = (ToHeader) request.getHeader(ToHeader.NAME);
		String oldToUser = getUsernameFromHeader(header);
		String toUser = dbManager.getForward(oldToUser);
		if (toUser != null) {
			String originalUri = header.getAddress().toString();
			String newUri = "sip:" + toUser
					+ originalUri.substring(originalUri.indexOf("@"));
			URI newURI;
			try {
				newURI = proxy.getAddressFactory().createURI(newUri);
				ToHeader newTo = proxy.getHeaderFactory().createToHeader(
						proxy.getAddressFactory().createAddress(newURI), null);
				Request newreq = (Request) request.clone();
				newreq.setHeader(newTo);
				return newreq;

			} catch (ParseException e) {
				e.printStackTrace();
			}

		} else
			return request;
		
		return null;
	}
	
}
