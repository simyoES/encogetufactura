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
package com.conzebit.myplan.android.store.data;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.conzebit.myplan.android.store.MyPlanDBHelper;
import com.conzebit.myplan.core.datarxtx.DataRxTx;
import com.conzebit.util.AndroidUtils;
import com.conzebit.util.StringUtil;

/**
 * 
 * @author sanz
 *
 */
public class DataStore {
	
	private static final String LOG_TAG = "DataStore";

	private static DataStore instance = null;
	private MyPlanDBHelper myPlanDBHelper = null;
	private TelephonyManager tm = null;
	
	private DataStore(Context context) {
		this.myPlanDBHelper = new MyPlanDBHelper(context);
		this.tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public static DataStore getInstance(Context context) {
		if (instance == null){
			instance = new DataStore(context);
		}
		return instance;
	}
	
	public void storeDataRxTx(String countryCode) {
		if (StringUtil.isNotEmpty(countryCode)) {
			try {
				Device device = Device.getDevice();
				long rx = device.getCellRxBytes();
				long tx = device.getCellTxBytes();
				myPlanDBHelper.storeDataRxTx(rx, tx, countryCode);
			} catch (Exception e) {
				Log.e(LOG_TAG, "Error retrieving device rx/tx data", e);
			}
		}
	}
	
	public ArrayList<DataRxTx> getDataRxTx(Date startBillingDate, Date endBillingDate) {
		storeDataRxTx(AndroidUtils.getTelephonyManagerCountryCode(tm));
		long from = startBillingDate != null ? startBillingDate.getTime() : System.currentTimeMillis();
		long to = endBillingDate != null ? endBillingDate.getTime() : System.currentTimeMillis();
		return myPlanDBHelper.getDataRxTx(from, to);
	}
}
