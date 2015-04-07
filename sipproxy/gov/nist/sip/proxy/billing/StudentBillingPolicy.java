package gov.nist.sip.proxy.billing;

public class StudentBillingPolicy implements BillingPolicy{
	
	double defaultCost = 5.0;
	double studentCost = defaultCost / 2.0;
	
	@Override
	public double calculateCost(long duration) {
		return duration*studentCost;
	}
}
