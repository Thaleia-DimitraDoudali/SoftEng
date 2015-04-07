package gov.nist.sip.proxy.billing;

public class GeneralBillingPolicy implements BillingPolicy{

	double defaultCost = 5.0;
	
	@Override
	public double calculateCost(long duration) {
		return duration*defaultCost;
	}

}
