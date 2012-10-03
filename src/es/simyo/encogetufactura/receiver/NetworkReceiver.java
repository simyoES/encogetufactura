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
package es.simyo.encogetufactura.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import com.conzebit.myplan.android.store.CountryStore;
import com.conzebit.myplan.android.store.data.DataStore;
import com.conzebit.util.AndroidUtils;
import com.conzebit.util.StringUtil;

public class NetworkReceiver extends BroadcastReceiver {
	
	private final static long SHORT_DELAY = 5 * 1000; // ten seconds
	private final static long LONG_DELAY = 60 * 60 * 1000; // each hour
	
	private final static String SHEDULED = "SCHEDULED";
	
	
    @Override
    public void onReceive(Context context, Intent intent) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = AndroidUtils.getTelephonyManagerCountryCode(tm);
		if (StringUtil.isNotEmpty(countryCode)) {
	    	if (intent != null && intent.getAction() != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
	    		CountryStore.getInstance(context).storeCountry(countryCode);
	    	}
			DataStore.getInstance(context).storeDataRxTx(countryCode);
		}
    	schedule(context, LONG_DELAY);
    }

	public static void schedule(Context context) {
		schedule(context, SHORT_DELAY);
	}

	private static void schedule(Context context, Long delay) {
		Intent serviceIntent = new Intent(context, NetworkReceiver.class);
		serviceIntent.setAction(SHEDULED + delay);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + delay, pendingIntent);
	}
}