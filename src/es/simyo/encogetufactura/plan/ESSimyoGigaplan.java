/*
 * This file is part of myPlan.
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
package es.simyo.encogetufactura.plan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import com.conzebit.myplan.core.Chargeable;
import com.conzebit.myplan.core.call.Call;
import com.conzebit.myplan.core.datarxtx.DataRxTx;
import com.conzebit.myplan.core.message.ChargeableMessage;
import com.conzebit.myplan.core.msisdn.MsisdnType;
import com.conzebit.myplan.core.plan.PlanChargeable;
import com.conzebit.myplan.core.plan.PlanSummary;
import com.conzebit.myplan.core.sms.Sms;

import es.simyo.encogetufactura.R;



/**
 * Gigaplan 150.
 * 
 * @author aalmenar
 */
public class ESSimyoGigaplan extends ESSimyo {
    
	private double monthFee = 19.95;
	private double initialPrice = 0;
	private double pricePerSecond = 0.07 / 60;
	private double smsPrice = 0.09;
	private int maxSecondsMonth = 150 * 60;
	private int dataMBIncluded = 2 * 1024;
    
	public String getPlanName() {
		return "Tarifa GigaPLAN + 2 GB";
	}
	
	public String getPlanURL() {
		return "http://www.simyo.es/simyo/publicarea/store/start-shopping.htm?pricePlan=9";
	}

	public int getPlanResourceID() {
		return R.id.planinfo_gigaplan;
	}
	
	public PlanSummary process(ArrayList<Chargeable> data) {
		PlanSummary retAux = new PlanSummary(this);

		Set<String> recipients = new HashSet<String>(); 
		
		int secondsTotal = 0;
		for (ListIterator<Chargeable> iter = data.listIterator(data.size()); iter.hasPrevious();) {
			Chargeable chargeable = iter.previous();
			if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_MESSAGE) {
				retAux.addPlanCall(new PlanChargeable(chargeable, null, this.getCurrency()));				
			} else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_CALL) {
				Call call = (Call) chargeable;
			
				if (call.getType() != Call.CALL_TYPE_SENT) {
					continue;
				}
				
				PlanChargeable planChargeable = null;
				MsisdnType msisdnType = call.getContact().getMsisdnType();
				msisdnType = this.getCountryISO().equals(call.getCountryWhereChargeWasMade())?msisdnType:MsisdnType.UNKNOWN;
				switch(msisdnType) {
				case ES_SPECIAL_ZER0:
					planChargeable = new PlanChargeable(call, 0.0, this.getCurrency());
					break;
				case UNKNOWN:
					planChargeable = new PlanChargeable(call, null, this.getCurrency());
					break;
				case ES_SPECIAL:
					planChargeable = super.processSpecialChargeableCall(call);
					break;
				case ES_INTERNATIONAL:
					planChargeable = super.processInternationalChargeableCall(call);
					break;
				default:
					recipients.add(call.getContact().getMsisdn());
					secondsTotal += call.getDuration();
					boolean insidePlan = secondsTotal <= maxSecondsMonth;
					Double callPrice = 0.0;
					if (!insidePlan) {
						long duration = (secondsTotal > maxSecondsMonth) && (secondsTotal - call.getDuration() <= maxSecondsMonth)? secondsTotal - maxSecondsMonth : call.getDuration();  
						callPrice += initialPrice + (duration * pricePerSecond);
					}
					planChargeable = new PlanChargeable(call, callPrice, this.getCurrency());
				}
				retAux.addPlanCall(planChargeable);
			} else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_SMS) {
				Sms sms = (Sms) chargeable;
				if (sms.getType() == Sms.SMS_TYPE_SENT) {
					PlanChargeable planChargeable = super.internalProcessSms(sms, smsPrice);
					retAux.addPlanCall(planChargeable);
				}
			} else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_DATARXTX) {
				retAux.addPlanCall(super.processDataRxTx((DataRxTx) chargeable, dataMBIncluded, false));
			}
		}

		PlanSummary ret = new PlanSummary(this);
		ret.addPlanCall(new PlanChargeable(new ChargeableMessage(ChargeableMessage.MESSAGE_MONTH_FEE), monthFee, this.getCurrency()));
		for (ListIterator<PlanChargeable> iter = retAux.getPlanCalls().listIterator(retAux.getPlanCalls().size()); iter.hasPrevious();) {
			ret.addPlanCall(iter.previous());
		}

		return ret;
	}
}