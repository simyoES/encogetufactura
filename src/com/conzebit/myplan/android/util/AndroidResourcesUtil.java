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

import es.simyo.encogetufactura.R;
import com.conzebit.myplan.core.msisdn.MsisdnType;

public class AndroidResourcesUtil {

	public static int getMsisdnTypeResourceImage(MsisdnType msisdnType) {
		switch (msisdnType) {
		case ES_LAND_LINE:
			return R.drawable.logo_landline;
		case ES_SPECIAL: 
		case ES_SPECIAL_ZER0:
			return R.drawable.telephone;
		case MOBILE:
			return R.drawable.mobile_phone;
		case ES_INTERNATIONAL:
			return R.drawable.mobile_phone_plus;
		default:
			return R.drawable.logo_landlinespecial;
		}
	}
	
	public static int getOperatorResourceImage(String operatorName) {
		return getMsisdnTypeResourceImage(MsisdnType.fromString(operatorName));
	}
	
}
