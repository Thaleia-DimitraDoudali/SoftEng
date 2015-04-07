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
		BillingObject billObj = billingDB.getBillingRecord(caller, callee);
		billObj.print();
		
		//Calculate duration
		long duration = (long) Math.ceil((System.currentTimeMillis() - billObj.startTime) / (double) 1000);
		System.out.println("Duration = " + duration);
		
		String plan = billingDB.getPlan(billObj.caller);
		System.out.println(plan);
		
		//Calculate cost through factory method
		BillingPolicyFactory billFactory = new BillingPolicyFactory();
		BillingPolicy billPolicy = billFactory.getBillingPolicy(plan);
		double cost = billPolicy.calculateCost(duration);
		System.out.println(cost);
		
		//Set duration on billing record AND COST
		billingDB.setBillingRecord(billObj.id, duration, cost);
	}
	

}
