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

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class MsisdnUtil {

	private static PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
	
	public static PhoneNumber getPhoneNumber(String msisdn, String defaultCountry) {
		PhoneNumber ret = null;
		try {
			ret = phoneUtil.parse(msisdn, defaultCountry);
		} catch (Exception e) {
		}
		return ret;
	}
	
	public static String getNSN(PhoneNumber phoneNumber, String defaultNSN) {
		String ret = defaultNSN;
		if (phoneNumber != null) {
			ret = phoneUtil.getNationalSignificantNumber(phoneNumber);
		}
		return ret;
	}

	public static String getISOCountryCode(PhoneNumber phoneNumber, String defaultCountry) {
		String ret = defaultCountry;
		if (phoneNumber != null) {
			ret = phoneUtil.getRegionCodeForNumber(phoneNumber);
		}
		return ret;
	}


}
