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

import com.conzebit.myplan.android.store.MyPlanDBHelper.CountryInterval;

import android.content.Context;

public class CountryStore {
	
	private static CountryStore instance = null;
	private MyPlanDBHelper myPlanDBHelper = null;

	private CountryStore(Context context) {
		this.myPlanDBHelper = new MyPlanDBHelper(context);
	}
	
	public static CountryStore getInstance(Context context) {
		if (instance == null){
			instance = new CountryStore(context);
		}
		return instance;
	}
	
	public void storeCountry(String countryCode) {
		this.myPlanDBHelper.storeCountryIfNew(countryCode.toUpperCase());
	}
	
	public String getCountryForDate(long dateInMillis, String defaultCountry) {
		return this.myPlanDBHelper.getCountryForDate(dateInMillis, defaultCountry);
	}
	
	public CountryInterval getCountryIntervalForDate(long dateInMillis, String defaultCountry) {
		return this.myPlanDBHelper.getCountryIntervalForDate(dateInMillis, defaultCountry);
	}

}
