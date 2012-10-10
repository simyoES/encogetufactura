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
package com.conzebit.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class AndroidUtils {
	
	private static final String TAG = "ANDROID_UTILS";

	public static Boolean isTestMode(Context context) {
		return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT);
    }

	public static int getAndroidAPILevel() {
		return android.os.Build.VERSION.SDK_INT;
    }
	
	public static String getAppVersionCode(Context context) {
        String ret = "";
        try {
        	int version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        	ret = String.valueOf(version);
        } catch (Exception e) {
        	Log.e(TAG, "error getting version number");
        }
        return ret;
	}
	
	public static String getTelephonyManagerCountryCode(TelephonyManager tm) {
		String countryCode = tm.getNetworkCountryIso();
		countryCode = (countryCode != null) ? countryCode.toUpperCase() : null;
		boolean ccOK = StringUtil.isNotEmpty(countryCode);
		boolean gsm = tm.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM;
		if (!ccOK && !gsm) {
			countryCode = "US";
		}
		return countryCode;
	}
	
	public static String getAppFullVersion(Context context) {
        StringBuffer ret = new StringBuffer();
        try {
        	ret.append(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        	ret.append('.');
        	int version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        	ret.append(version);
        } catch (Exception e) {
        	Log.e(TAG, "error getting version number");
        }
        return ret.toString();
	}
	
	public static void forceHideKeyboard(Context context, IBinder iBinder) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(iBinder, 0);
	}
	
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		if (imei == null) {
			imei = "000000000000000";
		}
		return imei;
	}
	
	public static void callPhone(Context context, String number) {
		String tel = "tel:" + number;
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
		context.startActivity(intent);
	}
	
	public static void openURL(Context context, String url) {
		Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
		context.startActivity(i);
	}
	
	public static boolean sendMail(Context context, String destination, String subject, String body) {
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
		String aEmailList[] = { destination };  
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
		if (subject != null) {
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		}
		if (body != null) {
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);			
		}
		emailIntent.setType("plain/text");
		
		boolean ret = true;
		try {
			context.startActivity(emailIntent);
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	public static boolean showMap(Context context, String address) {
		Uri addressUri = Uri.parse("geo:0,0?q=" + address);
		Intent searchAddress = new Intent(Intent.ACTION_VIEW, addressUri);

		boolean ret = true;
		try {
			context.startActivity(searchAddress);		
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	public static void installRemoteAPK(Context context, String packageName) {
		Uri installUri = Uri.fromParts("package", packageName, null); 
		Intent intent = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri);
		context.startActivity(intent);
	}
}
