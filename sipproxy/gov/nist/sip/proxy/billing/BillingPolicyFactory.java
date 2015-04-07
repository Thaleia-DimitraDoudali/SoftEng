package gov.nist.sip.proxy.billing;

public class BillingPolicyFactory {
	
	public BillingPolicy getBillingPolicy(String plan) {
		if (plan.equals("General"))
			return new GeneralBillingPolicy();
		else if (plan.equals("Student"))
			return new StudentBillingPolicy();
		else if (plan.equals("FreeWeekend"))
			return new FreeWeekendBillingPolicy();
		return null;
	}
}
