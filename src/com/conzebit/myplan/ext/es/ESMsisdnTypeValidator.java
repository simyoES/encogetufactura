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
package com.conzebit.myplan.ext.es;

import com.conzebit.myplan.core.msisdn.IMsisdnTypeValidator;
import com.conzebit.myplan.core.msisdn.MsisdnType;


public class ESMsisdnTypeValidator implements IMsisdnTypeValidator {
	
	private static final String COUNTRY_CODE = "ES";
	
    public String getCountryCode() {
	    return COUNTRY_CODE;
    }

    public MsisdnType getMsisdnType(String isoCountryCode, String nsn) {
    	if (nsn == null) {
    	    return MsisdnType.UNKNOWN;
    	}

    	if (!COUNTRY_CODE.equals(isoCountryCode)) {
	    	return MsisdnType.ES_INTERNATIONAL;
	    }
    	
    	if (nsn.length() < 3) {
    		return MsisdnType.UNKNOWN;
    	} else if (nsn.length() == 3) {
    		return MsisdnType.ES_SPECIAL;
    	} else if (nsn.length() > 3 && nsn.length() < 9) {
    		return MsisdnType.UNKNOWN;
    	}

    	// 800xxxxxx/900xxxxx are free of charge
	    if (nsn.startsWith("800") || nsn.startsWith("900")) {
	    	return MsisdnType.ES_SPECIAL_ZER0;
	    }
    	
	    // 801, 802, ... -> Special
	    if (nsn.startsWith("70") || nsn.startsWith("80") || nsn.startsWith("90")) {
	    	return MsisdnType.ES_SPECIAL;
	    }

	    // 91, 92 -> National
	    if (nsn.startsWith("6") || nsn.startsWith("7")) {
	    	return MsisdnType.MOBILE;
	    }

	    // 91, 92 -> National
	    if (nsn.startsWith("8") || nsn.startsWith("9")) {
	    	return MsisdnType.ES_LAND_LINE;
	    }
	    
	    return MsisdnType.UNKNOWN;
    }
}
