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
 * Simyo 2 cents.
 * @author sanz
 */
public class ESSimyo2centimos extends ESSimyo {
    
	private double monthFee = 4.90;
	private double initialPrice = 0.15;
	private double pricePerSecond = 0.02 / 60;
	private double smsPrice = 0.09;
	private int dataMBIncluded = 700;
    
	public String getPlanName() {
		return "Tarifa Habla + 700 MB";
	}
	
	public String getPlanURL() {
		return "http://www.simyo.es/simyo/publicarea/store/start-shopping.htm?pricePlan=7";
	}
	
	public int getPlanResourceID() {
		return R.id.planinfo_2cents;
	}
	
	public PlanSummary process(ArrayList<Chargeable> data) {
		PlanSummary ret = new PlanSummary(this);
		ret.addPlanCall(new PlanChargeable(new ChargeableMessage(ChargeableMessage.MESSAGE_MONTH_FEE), monthFee, this.getCurrency()));
		for (Chargeable chargeable : data) {
			if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_MESSAGE) {
				ret.addPlanCall(new PlanChargeable(chargeable, null, this.getCurrency()));				
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
					Double callPrice = initialPrice + (call.getDuration() * pricePerSecond);
					planChargeable = new PlanChargeable(call, callPrice, this.getCurrency());
				}
				ret.addPlanCall(planChargeable);
			} else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_SMS) {
				Sms sms = (Sms) chargeable;
				if (sms.getType() == Sms.SMS_TYPE_SENT) {
					PlanChargeable planChargeable = super.internalProcessSms(sms, smsPrice);
					ret.addPlanCall(planChargeable);
				}
			} else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_DATARXTX) {
				ret.addPlanCall(super.processDataRxTx((DataRxTx) chargeable, dataMBIncluded, true));
			}
		}
		return ret;
	}
}