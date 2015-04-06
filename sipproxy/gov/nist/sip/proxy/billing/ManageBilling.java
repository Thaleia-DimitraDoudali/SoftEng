package gov.nist.sip.proxy.billing;

import gov.nist.sip.db.BillingDB;

import javax.sip.address.URI;
import javax.sip.header.FromHeader;
import javax.sip.header.HeaderAddress;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;

public class ManageBilling {

	private BillingDB billingDB;
	
	public ManageBilling() {
		billingDB = new BillingDB();
	}
	
	private String getNameFromHeader(HeaderAddress header) {
		
		URI uri = header.getAddress().getURI();
		String uriString = uri.toString();
		return uriString.substring(uriString.indexOf("sip:") + 4,
				uriString.indexOf("@"));
	}
	
	public void startBilling(Request request) {
		HeaderAddress header = (HeaderAddress) request.getHeader(FromHeader.NAME);
		String caller = getNameFromHeader(header);
		header = (HeaderAddress) request.getHeader(ToHeader.NAME);
		String callee = getNameFromHeader(header);
		billingDB.addBillingRecord(caller, callee);
	}
	
	public void stopBilling(Request request) {
		HeaderAddress header = (HeaderAddress) request.getHeader(FromHeader.NAME);
		String caller = getNameFromHeader(header);
		header = (HeaderAddress) request.getHeader(ToHeader.NAME);
		String callee = getNameFromHeader(header);
		String start_time = billingDB.getBillingRecord(caller, callee);
		System.out.println("start_time = " + start_time);
	}
	
	
	
	
	
}
