package gov.nist.sip.proxy.billing;

import java.util.Calendar;

public class FreeWeekendBillingPolicy implements BillingPolicy {

	double defaultCost = 5.0;
	
	@Override
	public double calculateCost(long duration) {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if ((day == 1) || (day == 7)){
			return 0.0;
		} else {
			return duration*defaultCost;
		}
	}

}
