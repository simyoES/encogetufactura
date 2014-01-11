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

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.conzebit.myplan.android.util.AndroidResourcesUtil;
import com.conzebit.myplan.android.util.Settings;
import com.conzebit.myplan.core.Chargeable;
import com.conzebit.myplan.core.call.Call;
import com.conzebit.myplan.core.datarxtx.DataRxTx;
import com.conzebit.myplan.core.message.ChargeableMessage;
import com.conzebit.myplan.core.plan.PlanChargeable;
import com.conzebit.myplan.core.sms.Sms;
import com.conzebit.util.Formatter;
import com.conzebit.util.StringUtil;

import es.simyo.encogetufactura.EncogeTuFacturaApp;
import es.simyo.encogetufactura.R;

public class PlanDetailAdapter extends ArrayAdapter<PlanChargeable> {

    public PlanDetailAdapter(Context context, int textViewResourceId, List<PlanChargeable> planCalls) { 
	    super(context, textViewResourceId, planCalls);
    }

    public boolean isEnabled(int position) {
    	return false;
    }
	
    public View getView(int position, View convertView, ViewGroup parent) { 
        PlanChargeable planCall = super.getItem(position);
        return new PlanCallAdapterView(super.getContext(), planCall);
   }
    
    class PlanCallAdapterView extends LinearLayout {        
        public PlanCallAdapterView(Context context, PlanChargeable planChargeable) {
            super(context);

            Chargeable chargeable = planChargeable.getChargeable();
            
            if (chargeable != null) {

            	boolean roaming = !EncogeTuFacturaApp.DEFAULT_COUNTRY.equals(chargeable.getCountryWhereChargeWasMade());
            	
	            if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_CALL) {
	            	Call call = (Call) chargeable;
	            	
	            	int operatorLogo = AndroidResourcesUtil.getMsisdnTypeResourceImage(call.getContact().getMsisdnType());
	            	String contactName = call.getContact().getContactName();
	            	String msisdn = call.getContact().getMsisdn();
	            	String text = new StringBuilder()
	            		.append(Formatter.formatFullDate(call.getDate()))
	            		.append(' ')
	            		.append(Formatter.formatDurationAsString(call.getDuration()))
	            		.append(roaming?" ROAMING":"").toString();
	            	addChargeableLine(operatorLogo, contactName, msisdn, text, planChargeable, context);

	            } else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_SMS) {
	            	Sms sms = (Sms) chargeable;
	            	int operatorLogo = R.drawable.logo_sms;
	            	String contactName = sms.getContact().getContactName();
	            	String msisdn = sms.getContact().getMsisdn();
	            	String text = new StringBuilder()
	            		.append(Formatter.formatFullDate(sms.getDate()))
	            		.append(roaming?" ROAMING":"").toString();
	            	addChargeableLine(operatorLogo, contactName, msisdn, text, planChargeable, context);

	            } else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_MESSAGE) {
	            	ChargeableMessage message = (ChargeableMessage) chargeable;
	            	int resourceID = getResources().getIdentifier(message.getMessage(), "string", context.getPackageName());

	            	if (planChargeable.getPrice() == null) {
	            		View view = super.inflate(context, R.layout.detail_title, this);
	            		TextView title = (TextView) view.findViewById(R.id.detail_line_title);
	            		title.setText(resourceID);
	            	} else {
	            		Integer operatorLogo = null;
	            		String contactName = getResources().getText(resourceID).toString();
	            		String msisdn = "";
	            		String text = "";
		               	addChargeableLine(operatorLogo, contactName, msisdn, text, planChargeable, context);
	            	}
	            } else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_DATARXTX) {
	            	DataRxTx dataRxTx = (DataRxTx) chargeable;
	            	Integer operatorLogo = null;
	               	String contactName = getResources().getText(R.string.plan_data).toString();
	               	String msisdn = "";
	               	String text = new StringBuilder()
	               		.append(Formatter.formatDate(dataRxTx.getFrom()))
	               		.append(" - ")
	               		.append(Formatter.formatDate(dataRxTx.getTo()))
	               		.append(" ")
	               		.append(Formatter.formatBytesToMb(dataRxTx.getRx() + dataRxTx.getTx()))
	               		.append(roaming?" ROAMING":"").toString();
	               	addChargeableLine(operatorLogo, contactName, msisdn, text, planChargeable, context);
	            }
            }
            
        }
        
        private void addChargeableLine(Integer operatorLogo, String contactName, String msisdn, String date, PlanChargeable planCall, Context context) {
            View view = super.inflate(context, R.layout.detail_line, this);
            ImageView operatorLogoImageView = (ImageView) view.findViewById(R.id.detail_line_operatorlogo);
            TextView contactNameTextView = (TextView) view.findViewById(R.id.detail_line_contactname);
            TextView msisdntTextView = (TextView) view.findViewById(R.id.detail_line_msisdn);
            TextView dateTextView = (TextView) view.findViewById(R.id.detail_line_dateandduration);
            TextView planAmountTextView = (TextView) view.findViewById(R.id.detail_line_totalamount);
            
            if (operatorLogo != null) {
            	operatorLogoImageView.setImageResource(operatorLogo);
            }
            if (StringUtil.isNotBlank(contactName)) {
            	contactNameTextView.setText(contactName);
            } else {
            	contactNameTextView.setText(msisdn);
            }
            msisdntTextView.setText(msisdn);
            dateTextView.setText(date);

            double vat = 1 + (((double) Settings.getVATValue(context)) / 100);
            Double price = planCall.getPrice();
            String totalValue = null;
            if (price != null) {
        		totalValue = new StringBuilder()
        			.append(Formatter.formatDecimal(price))
        			.append(" ")
					.append(planCall.getCurrency())
					.append(" ")
					.append(context.getString(R.string.settings_vat_novat))
					.append(" (")
					.append(Formatter.formatDecimal(price * vat))
					.append(" ")
					.append(planCall.getCurrency())
					.append(" ")
					.append(context.getString(R.string.settings_vat_vat))
					.toString();
            } else {
            	totalValue = "-- " + planCall.getCurrency();
            }
			planAmountTextView.setText(totalValue);
       }
	}
}