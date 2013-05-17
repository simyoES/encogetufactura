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
package es.simyo.encogetufactura.plan;

import java.util.HashMap;

import com.conzebit.myplan.core.call.Call;
import com.conzebit.myplan.core.datarxtx.DataRxTx;
import com.conzebit.myplan.core.msisdn.MsisdnType;
import com.conzebit.myplan.core.plan.PlanChargeable;
import com.conzebit.myplan.core.plan.SpecialChargeProcessor;
import com.conzebit.myplan.core.sms.Sms;
import com.conzebit.myplan.ext.es.ESPlan;


public abstract class ESSimyo extends ESPlan {

	private static final double INTERNATIONAL_INITIAL_PRICE = 0.35;
	private static final double INTERNATIONAL_PRICE_PER_SECOND = 0.30 / 60;
	private static final double INTERNATIONAL_PRICE_PER_SECOND_CU_KP_SO = 1.2 / 60;
	private static final double INTERNATIONAL_SMS_PRICE = 0.15;
	
	private static final double DATARXTX_KB_EXPRA_PRICE = 0.03 / 1024; // 3cent/MB
	
	@SuppressWarnings("serial")
	private static final HashMap<String, SpecialChargeProcessor> SPECIAL_CHARGE_NUMBERS = new HashMap<String, SpecialChargeProcessor>() {{
		put("010", new ESSimyoSpecialChargeA(null, null, 0.0045, 0.0045));
		put("012", new ESSimyoSpecialChargeA(0.36, null, null, null));
		put("016", new ESSimyoSpecialChargeA(null, null, null, null));
		put("060", new ESSimyoSpecialChargeA(null, null, null, null));
		put("061", new ESSimyoSpecialChargeA(0.1202, null, null, null));
		put("062", new ESSimyoSpecialChargeA(0.1202, null, null, null));
		put("080", new ESSimyoSpecialChargeA(0.1202, null, null, null));
		put("082", new ESSimyoSpecialChargeA(null, null, null, null));
		put("083", new ESSimyoSpecialChargeA(null, null, null, null));
		put("085", new ESSimyoSpecialChargeA(null, null, null, null));
		put("091", new ESSimyoSpecialChargeA(0.1202, null, null, null));
		put("092", new ESSimyoSpecialChargeA(0.1202, null, null, null));
		put("096", new ESSimyoSpecialChargeA(0.9376, null, null, null));
		put("098", new ESSimyoSpecialChargeA(null, null, 0.0068, 0.0068));
		put("700", new ESSimyoSpecialChargeB(0.15, 0.10));
		put("704", new ESSimyoSpecialChargeB(0.15, 0.10));
		put("707", new ESSimyoSpecialChargeB(0.15, 0.10));
		put("8030", new ESSimyoSpecialChargeC(0.154, 0.004, 0.011));
		put("8031", new ESSimyoSpecialChargeC(0.154, 0.004, 0.011));
		put("8032", new ESSimyoSpecialChargeC(0.154, 0.004, 0.018));
		put("8033", new ESSimyoSpecialChargeC(0.154, 0.004, 0.018));
		put("8034", new ESSimyoSpecialChargeC(0.154, 0.004, 0.022));
		put("8035", new ESSimyoSpecialChargeC(0.154, 0.004, 0.022));
		put("8036", new ESSimyoSpecialChargeC(0.154, 0.004, 0.032));
		put("8037", new ESSimyoSpecialChargeC(0.154, 0.004, 0.032));
		put("8038", new ESSimyoSpecialChargeC(0.154, 0.004, 0.058));
		put("8039", new ESSimyoSpecialChargeC(0.154, 0.004, 0.122));
		put("8060", new ESSimyoSpecialChargeC(0.154, 0.004, 0.011));
		put("8061", new ESSimyoSpecialChargeC(0.154, 0.004, 0.011));
		put("8062", new ESSimyoSpecialChargeC(0.154, 0.004, 0.018));
		put("8063", new ESSimyoSpecialChargeC(0.154, 0.004, 0.018));
		put("8064", new ESSimyoSpecialChargeC(0.154, 0.004, 0.022));
		put("8065", new ESSimyoSpecialChargeC(0.154, 0.004, 0.022));
		put("8066", new ESSimyoSpecialChargeC(0.154, 0.004, 0.032));
		put("8067", new ESSimyoSpecialChargeC(0.154, 0.004, 0.032));
		put("8068", new ESSimyoSpecialChargeC(0.154, 0.004, 0.058));
		put("8069", new ESSimyoSpecialChargeC(0.154, 0.004, 0.122));
		put("8070", new ESSimyoSpecialChargeC(0.154, 0.004, 0.011));
		put("8071", new ESSimyoSpecialChargeC(0.154, 0.004, 0.011));
		put("8072", new ESSimyoSpecialChargeC(0.154, 0.004, 0.018));
		put("8073", new ESSimyoSpecialChargeC(0.154, 0.004, 0.018));
		put("8074", new ESSimyoSpecialChargeC(0.154, 0.004, 0.022));
		put("8075", new ESSimyoSpecialChargeC(0.154, 0.004, 0.022));
		put("8076", new ESSimyoSpecialChargeC(0.154, 0.004, 0.032));
		put("8077", new ESSimyoSpecialChargeC(0.154, 0.004, 0.032));
		put("8078", new ESSimyoSpecialChargeC(0.154, 0.004, 0.058));
		put("8079", new ESSimyoSpecialChargeC(0.154, 0.004, 0.122));
		put("901", new ESSimyoSpecialChargeB(0.15, 0.24));
		put("902", new ESSimyoSpecialChargeB(0.15, 0.36));
		put("9051", new ESSimyoSpecialChargeB(0.750, null));
		put("9052", new ESSimyoSpecialChargeB(1.050, null));
		put("9054", new ESSimyoSpecialChargeB(1.650, null));
		put("9055", new ESSimyoSpecialChargeB(1.650, null));
		put("9057", new ESSimyoSpecialChargeB(1.050, null));
		put("9058", new ESSimyoSpecialChargeB(1.650, null));
	}};
	
	public String getOperator() {
		return MsisdnType.ES_SIMYO.toString();
	}
	
	protected PlanChargeable processSpecialChargeableCall(Call call) {
		Double price = null;
		String msisdn = call.getContact().getMsisdn();
		for (HashMap.Entry<String, SpecialChargeProcessor> entry : SPECIAL_CHARGE_NUMBERS.entrySet()) {
			if (msisdn.startsWith(entry.getKey())) {
				price = entry.getValue().process(call.getDuration());
				break;
			}
		}
		return new PlanChargeable(call, price, this.getCurrency());
	}

	protected PlanChargeable processSpecialChargeableSMS(Sms sms) {
		return new PlanChargeable(sms, null, this.getCurrency()); // do not charge premium SMS
	}
	
	protected PlanChargeable processInternationalChargeableCall(Call call) {
		Double price = null;
		String destinationCountry = call.getContact().getIsoCountryCode();
		if ("CU".equals(destinationCountry) || "KP".equals(destinationCountry) || "SO".equals(destinationCountry)) {
			price = INTERNATIONAL_INITIAL_PRICE + (call.getDuration() * INTERNATIONAL_PRICE_PER_SECOND_CU_KP_SO);
		} else {
			price = INTERNATIONAL_INITIAL_PRICE + (call.getDuration() * INTERNATIONAL_PRICE_PER_SECOND);
		}
		return new PlanChargeable(call, price, this.getCurrency());
	}

	protected PlanChargeable processInternationalChargeableSMS(Sms sms) {
		return new PlanChargeable(sms, INTERNATIONAL_SMS_PRICE, this.getCurrency());
	}

	public PlanChargeable processDataRxTx(DataRxTx dataRxTx, int dataMBIncluded, boolean chargeExtraMB) {
		return this.processDataRxTx(dataRxTx, dataMBIncluded, chargeExtraMB, DATARXTX_KB_EXPRA_PRICE);
	}
	
	protected PlanChargeable processDataRxTx(DataRxTx dataRxTx, int dataMBIncluded, boolean chargeExtraMB, double extraPricePerKB) {
		PlanChargeable planChargeable = null;
		Double price = null;  // roaming data, not charged for now
		if (this.getCountryISO().equals(dataRxTx.getCountryWhereChargeWasMade())) {
			price = 0.0;
			if (chargeExtraMB) {
				double bytesIncluded = dataMBIncluded * 1024 * 1024;
				double dataTransferred = dataRxTx.getRx() + dataRxTx.getTx();
				if (dataTransferred > bytesIncluded) {
					price = (double) ((int) ((dataTransferred - bytesIncluded) / 1024)) * extraPricePerKB;
				}
			}
		}
		planChargeable = new PlanChargeable(dataRxTx, price, this.getCurrency());
		return planChargeable;
	}

	public PlanChargeable internalProcessSms(Sms sms, double smsPrice) {
		PlanChargeable planChargeable = null;
		MsisdnType msisdnType = sms.getContact().getMsisdnType();
		msisdnType = this.getCountryISO().equals(sms.getCountryWhereChargeWasMade())?msisdnType:MsisdnType.UNKNOWN;
		switch (msisdnType) {
		case ES_SPECIAL_ZER0:
			planChargeable = new PlanChargeable(sms, 0.0, this.getCurrency());
			break;
		case UNKNOWN:
			planChargeable = new PlanChargeable(sms, null, this.getCurrency());
			break;
		case ES_SPECIAL:
			planChargeable = processSpecialChargeableSMS(sms);
			break;
		case ES_INTERNATIONAL:
			planChargeable = processInternationalChargeableSMS(sms);
			break;
		default:
			planChargeable = new PlanChargeable(sms, smsPrice, this.getCurrency());
		}
		return planChargeable;
	}
}