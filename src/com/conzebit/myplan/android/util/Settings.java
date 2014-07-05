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
import es.simyo.encogetufactura.R;

public class Settings {
	
	public enum Setting {
		BILLINGDAY("billingday_preference"),
		TERMS_AND_CONDITIONS_SHOWN("terms_and_conditions_shown"),
		COUNT_SMS("count_sms"),
		VAT("vat"),
		MONTHLY_FEE("monthly_fee"),
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
	
	public static int getVATItem(Context context, int vatValue) {
		return getVATItem(context, "" + vatValue);
	}
	public static int getVATItem(Context context, String vatValue) {
		int pos = 0;
		for (String vatValueItem : context.getResources().getStringArray(R.array.settings_vat_value)) {
			if (vatValueItem.equals(vatValue)) {
				break;
			}
			pos++;
		}
		switch (pos) {
		case 0:
			return R.string.settings_vat_ipsi_melilla;
		case 1:
			return R.string.settings_vat_igic_canarias;
		case 2:
			return R.string.settings_vat_ipsi_ceuta;
		case 3:
			return R.string.settings_vat_peninsula_baleares;
		default:
			return R.string.settings_vat_peninsula_baleares;
		}
	}

    public static int getVATValue(Context context) {
    	String vat = PreferenceManager.getDefaultSharedPreferences(context).getString(Setting.VAT.toString(), "21");
    	if ("8".equals(vat)) {
    		setVATValue(context, "10");
    	}
    	return Integer.valueOf(vat);
    }

    public static void setVATValue(Context context, String value) {
    	Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    	editor.putString(Setting.VAT.toString(), value);
    	editor.commit();
    }
    
    public static Integer getMonthlyFee(Context context) {
    	int ret = PreferenceManager.getDefaultSharedPreferences(context).getInt(Setting.MONTHLY_FEE.toString(), -1);
    	if (ret == -1) {
    		return null;
    	} else {
    		return ret;
    	}
    }
    
    public static void setMontlyFee(Context context, Integer value) {
    	Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    	if (value != null) {
    		editor.putInt(Setting.MONTHLY_FEE.toString(), value);
    	} else {
    		editor.remove(Setting.MONTHLY_FEE.toString());
    	}
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
