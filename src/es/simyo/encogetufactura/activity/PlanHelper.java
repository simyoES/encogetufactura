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
package es.simyo.encogetufactura.activity;

import android.app.Activity;

import com.conzebit.util.Formatter;

import es.simyo.encogetufactura.EncogeTuFacturaApp;

public class PlanHelper {
	
	public static final String getBillingPeriod(Activity activity) {
		EncogeTuFacturaApp myPlanApp = (EncogeTuFacturaApp) activity.getApplication();
    	return new StringBuilder()
        	.append(Formatter.formatDate(myPlanApp.getStartBillingDate()))
        	.append(" - ")
        	.append(Formatter.formatDate(myPlanApp.getEndBillingDate()))
        	.toString();
	}
}
