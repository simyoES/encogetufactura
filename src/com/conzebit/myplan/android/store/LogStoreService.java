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
package com.conzebit.myplan.android.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.content.Context;

import com.conzebit.myplan.android.store.data.DataStore;
import com.conzebit.myplan.core.Chargeable;
import com.conzebit.myplan.core.call.Call;
import com.conzebit.myplan.core.message.ChargeableMessage;
import com.conzebit.util.AndroidUtils;

public class LogStoreService {

	public static ArrayList<Chargeable> getAll(Context context, Date startBillingDate, Date endBillingDate) {
		ArrayList<Chargeable> ret = null;
		if (AndroidUtils.isTestMode(context)) {
			ret = LogStoreServiceTest.getTestChargeable(context);
			Collections.sort(ret);
			ret.add(0, new ChargeableMessage(ChargeableMessage.MESSAGE_REGISTERED_CALLS_SMS));
		} else {
			ret = (ArrayList<Chargeable>) CallLogStore.getCalls(context, startBillingDate, endBillingDate);
			ret.addAll(SMSLogStore.getSMS(context, startBillingDate, endBillingDate));
			Collections.sort(ret);
			ret.add(0, new ChargeableMessage(ChargeableMessage.MESSAGE_REGISTERED_CALLS_SMS));
			ret.addAll(0, DataStore.getInstance(context).getDataRxTx(startBillingDate, endBillingDate));
		}
		return ret;
	}
	
	public static ArrayList<Chargeable> getCalls(Context context, Date startBillingDate, Date endBillingDate) {
		return CallLogStore.getCalls(context, startBillingDate, endBillingDate);
	}

	public static ArrayList<Chargeable> getSms(Context context, Date startBillingDate, Date endBillingDate) {
		return SMSLogStore.getSMS(context, startBillingDate, endBillingDate);
	}
	
	public static Call getLastCall(Context context) {
		return CallLogStore.getLastCall(context);
	}
}
