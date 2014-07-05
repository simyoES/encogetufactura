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

/**
 * Simyo Paga lo justo.
 * @author sanz
 */
public class ESSimyoPagaLoJusto extends ESSimyo {
	
	private double smsPrice = 0.09;
        
	public String getPlanName() {
		return "Tarifa Paga lo justo";
	}
	
	public String getPlanURL() {
		return "http://www.simyo.es/simyo/publicarea/store/start-shopping.htm?pricePlan=5";
	}

	public int getPlanResourceID() {
		return 0;
	}
	
	public PlanSummary process(ArrayList<Chargeable> data) {
		PlanSummary ret = new PlanSummary(this);
		double globalPrice = 0.0;
		int callsDurationSeconds = 0;
		int voiceCalls = 0;
		long dataTransferred = 0;
		int minutes = 0;
		int txrx = 0;
		for (Chargeable chargeable : data) {
			if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_MESSAGE) {
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
					break;
				case ES_SPECIAL:
					planChargeable = super.processSpecialChargeableCall(call);
					globalPrice += planChargeable.getPrice() != null? planChargeable.getPrice() : 0.0;
					break;
				case ES_INTERNATIONAL:
					planChargeable = super.processInternationalChargeableCall(call);
					globalPrice += planChargeable.getPrice() != null? planChargeable.getPrice() : 0.0;
					break;
				case UNKNOWN:
				default:
					callsDurationSeconds += call.getDuration();
					voiceCalls++;
				}
			} else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_SMS) {
				Sms sms = (Sms) chargeable;
				if (sms.getType() == Sms.SMS_TYPE_SENT) {
					PlanChargeable planChargeable = super.internalProcessSms(sms, smsPrice);
					globalPrice += planChargeable.getPrice() != null? planChargeable.getPrice() : 0.0;
				}
			} else if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_DATARXTX) {
				DataRxTx dataRxTx = (DataRxTx) chargeable;
				dataTransferred += dataRxTx.getRx() + dataRxTx.getTx();
			}
		}

		double callsDurationMinutes = callsDurationSeconds / 60;
		if (callsDurationMinutes == 0) {
		} else if (callsDurationMinutes < 30) {
			globalPrice += 1.65;
			minutes = 30;
		} else if (callsDurationMinutes < 100) {
			globalPrice += 2.89;
			minutes = 100;
		} else {
			globalPrice += 2.89 + ((callsDurationMinutes - 100) * 0.05) + (voiceCalls * 0.15);
			minutes = 100;
		}
		
		double dataTransferredMB = dataTransferred / 1024 / 1024;
		if (dataTransferredMB == 0) {
		} else if (dataTransferredMB < 100) {
			globalPrice += 0.83;
			txrx = 100;
		} else if (dataTransferredMB < 300) {
			globalPrice += 2.07;
			txrx = 300;
		} else if (dataTransferredMB < 1024) {
			globalPrice += 4.96;
			txrx = 1024;
		} else if (dataTransferredMB < 2048) {
			globalPrice += 9.09;
			txrx = 2048;
		} else {
			globalPrice += 9.09 + (dataTransferredMB - 2048) * 0.03;
			txrx = 2048;
		}
		
		ret.addPlanCall(new PlanChargeable(new ChargeableMessage("min"), (double) minutes, this.getCurrency()));
		ret.addPlanCall(new PlanChargeable(new ChargeableMessage("txrx"), (double) txrx, this.getCurrency()));
		ret.addPlanCall(new PlanChargeable(new ChargeableMessage("total"), globalPrice, this.getCurrency()));
		
		
		return ret;
	}
}