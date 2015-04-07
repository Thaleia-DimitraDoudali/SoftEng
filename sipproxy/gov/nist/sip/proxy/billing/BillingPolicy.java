package gov.nist.sip.proxy.billing;

public interface BillingPolicy {
	
	public double calculateCost(long duration);
}
