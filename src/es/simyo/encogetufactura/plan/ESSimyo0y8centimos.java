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
import com.conzebit.myplan.core.msisdn.MsisdnType;
import com.conzebit.myplan.core.plan.PlanChargeable;
import com.conzebit.myplan.core.plan.PlanSummary;
import com.conzebit.myplan.core.sms.Sms;

import es.simyo.encogetufactura.R;



/**
 * Simyo 0 y 8 centimos
 * @author sanz
 */
public class ESSimyo0y8centimos extends ESSimyo {
    
	private double initialPrice = 0.15;
	private double pricePerSecond = 0.08 / 60;
	private double pricePerSecondSimyo = 0.09 / 60;
	private int maxFreeSeconds = 10 * 60;
	private double smsPrice = 0.09;
	private int dataMBIncluded = 0;
    
	public String getPlanName() {
		return "Tarifa 0/8 cent.";
	}
	
	public String getPlanURL() {
		return "http://www.simyo.es/tarifas.html";
	}
	
	public int getPlanResourceID() {
		return R.id.planinfo_0_6cents;
	}
	
	public PlanSummary process(ArrayList<Chargeable> data) {
		PlanSummary ret = new PlanSummary(this);
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
				case ES_SIMYO:
					Double callPriceSimyo = null;
					if (call.getDuration() <= maxFreeSeconds) {
						callPriceSimyo = initialPrice;
					} else {
						callPriceSimyo = initialPrice + ((call.getDuration() - maxFreeSeconds) * pricePerSecondSimyo);
					}
					planChargeable = new PlanChargeable(call, callPriceSimyo, this.getCurrency());
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