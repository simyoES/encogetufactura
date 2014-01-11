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
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.conzebit.myplan.android.util.Settings;
import com.conzebit.myplan.core.plan.PlanSummary;
import com.conzebit.util.Formatter;

import es.simyo.encogetufactura.EncogeTuFacturaApp;
import es.simyo.encogetufactura.R;

public class PlanHelper {


	public final static void getPlanSummaryView(Context context, LinearLayout parent, PlanSummary planSummary, int layoutId) {

		LinearLayout view = (LinearLayout) LinearLayout.inflate(context, layoutId, parent);
        
        TextView planName = (TextView) view.findViewById(R.id.summary_line_planname);
		planName.setText(planSummary.getPlan().getScreenPlanName());
        
        TextView totalAmount = (TextView) view.findViewById(R.id.summary_line_totalamount);
        double vat = 1 + (((double) Settings.getVATValue(context)) / 100);
        double totalPrice = planSummary.getTotalPrice();
		String totalValue = new StringBuilder()
			.append(Formatter.formatDecimal(totalPrice))
			.append(" ")
			.append(planSummary.getPlan().getCurrency())
			.append(" ")
			.append(context.getString(R.string.settings_vat_novat))
			.append(" (")
			.append(Formatter.formatDecimal(totalPrice * vat))
			.append(" ")
			.append(planSummary.getPlan().getCurrency())
			.append(" ")
			.append(context.getString(R.string.settings_vat_vat))
			.toString();
        totalAmount.setText(totalValue);
	}
	
	public static final String getBillingPeriod(Activity activity) {
		EncogeTuFacturaApp myPlanApp = (EncogeTuFacturaApp) activity.getApplication();
    	return new StringBuilder()
        	.append(Formatter.formatDate(myPlanApp.getStartBillingDate()))
        	.append(" - ")
        	.append(Formatter.formatDate(myPlanApp.getEndBillingDate()))
        	.toString();
	}
}
