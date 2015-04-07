package gov.nist.sip.proxy.billing;

//Object that keeps the record info that is returned from database
public class BillingObject {
	int id;
	long startTime;
	String caller;
	
	public BillingObject(int id, long startTime, String caller) {
		this.id = id;
		this.startTime = startTime;
		this.caller = caller;
	}
	
	public void print() {
		System.out.println("id = " + id + " start time = " + startTime + " caller = " + caller );
	}
}
