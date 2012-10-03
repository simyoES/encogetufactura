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
package com.conzebit.myplan.android.util;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Settings {
	
	private static final String[] vatList = {"No", "Melilla (IPSI 4%)", "Canarias (IGIC 7%)", "Ceuta (IPSI 8%)", "Península y Baleares (IVA 21%)"};
	private static final String[] vatValueList = {"0", "4", "7", "8", "21"};

	public enum Setting {
		BILLINGDAY("billingday_preference"),
		TERMS_AND_CONDITIONS_SHOWN("terms_and_conditions_shown"),
		COUNT_SMS("count_sms"),
		VAT("vat"),
		APP_VERSION("app_version"),
		PLAN_CONFIG("plan.config"),
		SHOW_PLANS_CONFIG("show.plans.config");

		private String value;
		private Setting(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
	}
	
	public static String[] getVATList() {
		return vatList;
	}

	public static String[] getVATValueList() {
		return vatValueList;
	}
	
	public static String getVATItem(int vatValue) {
		return getVATItem("" + vatValue);
	}
	public static String getVATItem(String vatValue) {
		int pos = 0;
		for (String vatValueItem : vatValueList) {
			if (vatValueItem.equals(vatValue)) {
				break;
			}
			pos++;
		}
		return vatList[pos];
	}

    public static int getVATValue(Context context) {
    	String vat = PreferenceManager.getDefaultSharedPreferences(context).getString(Setting.VAT.toString(), "0");
    	return Integer.valueOf(vat);
    }

    public static void setVATValue(Context context, String value) {
    	Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    	editor.putString(Setting.VAT.toString(), value);
    	editor.commit();
    }
    
    public static String getBillingDay(Context context) {
    	return PreferenceManager.getDefaultSharedPreferences(context).getString(Setting.BILLINGDAY.toString(), "1");
    }

    public static boolean showTermsAndConditions(Context context) {
    	return "no".equals(PreferenceManager.getDefaultSharedPreferences(context).getString(Setting.TERMS_AND_CONDITIONS_SHOWN.toString(), "no"));
    }
    
    public static void setTermsShown(Context context, String value) {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(Setting.TERMS_AND_CONDITIONS_SHOWN.toString(), value);
        ed.commit();
    }
    
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> getAllConfig(Context context) {
    	return (HashMap<String, Object>) PreferenceManager.getDefaultSharedPreferences(context).getAll();
    }
}
