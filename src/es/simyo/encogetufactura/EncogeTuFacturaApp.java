/*
 * This file is part of Simyo - Encoge tu factura.
 *
 * Plan is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Plan is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with myPlan.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.simyo.encogetufactura;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Application;

import com.conzebit.myplan.android.store.AndroidMsisdnTypeStore;
import com.conzebit.myplan.android.util.Settings;
import com.conzebit.myplan.core.msisdn.MsisdnTypeService;
import com.conzebit.myplan.core.plan.PlanService;

public class EncogeTuFacturaApp extends Application {
	
	private MsisdnTypeService msisdnTypeService = null;
	private PlanService planService = null;
	private Date startBillingDate = null;
	private Date endBillingDate = null;
	
	public static final String DEFAULT_COUNTRY = "ES";
	public static final String CALL_NUMBER = "1645";

	@Override
    public void onCreate() {
	    super.onCreate();

	    setBillingPeriod(Integer.valueOf(Settings.getBillingDay(this)));
	    
		msisdnTypeService = MsisdnTypeService.getInstance(new AndroidMsisdnTypeStore(this));
    	planService = PlanService.getInstance();
    	planService.setUserConfig(Settings.getAllConfig(this));
    }
	
	public PlanService getPlanService() {
		return this.planService;
	}

	public MsisdnTypeService getMsisdnTypeService() {
		return this.msisdnTypeService;
	}
	
	public void setBillingPeriod(int billingDay) {
	    Calendar cal = Calendar.getInstance();
	    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

	    cal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), billingDay);
	    if (dayOfMonth < billingDay) {
	    	cal.add(Calendar.MONTH, -1);
	    }
	    startBillingDate = cal.getTime();
    	cal.add(Calendar.MONTH, 1);
    	cal.add(Calendar.MILLISECOND, -1);
    	endBillingDate = cal.getTime();
	}
	
	public boolean nextBillingPeriod() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startBillingDate);
		cal.add(Calendar.MONTH, 1);
		startBillingDate = cal.getTime();
    	cal.add(Calendar.MONTH, 1);
    	cal.add(Calendar.MILLISECOND, -1);
    	endBillingDate = cal.getTime();
		Calendar now = Calendar.getInstance();
		return cal.compareTo(now) < 0;
	}
	
	public void previousBillingPeriod() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startBillingDate);
		cal.add(Calendar.MONTH, -1);
		startBillingDate = cal.getTime();
    	cal.add(Calendar.MONTH, 1);
    	cal.add(Calendar.MILLISECOND, -1);
    	endBillingDate = cal.getTime();
	}
	
	public Date getStartBillingDate() {
		return startBillingDate;
	}
	
	public Date getEndBillingDate() {
		return endBillingDate;
	}
}