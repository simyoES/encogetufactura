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
package com.conzebit.myplan.core;

import java.util.Calendar;

import com.conzebit.myplan.core.contact.Contact;
import com.conzebit.util.ToStringBuilder;

public abstract class Chargeable implements Comparable<Chargeable> {

	public static final int CHARGEABLE_TYPE_CALL = 0;
	public static final int CHARGEABLE_TYPE_SMS = 1;
	public static final int CHARGEABLE_TYPE_MESSAGE = 2;
	public static final int CHARGEABLE_TYPE_DATARXTX = 3;
	
    public abstract int getChargeableType();
    
	protected Calendar date;
	
	protected Contact contact;
	
	protected String countryWhereChargeWasMade;
    
	public Calendar getDate() {
		return this.date;
	}
    
	public Contact getContact() {
		return this.contact;
	}
	
	public String getCountryWhereChargeWasMade() {
		return this.countryWhereChargeWasMade;
	}
	
    public int compareTo(Chargeable chargeable) {
		if (this.date == null) {
			return -1;
		}
		if (chargeable.date == null) {
			return 1;
		}
		
		return chargeable.date.compareTo(this.date);
    }
    
    @Override
    public String toString() {
    	return new ToStringBuilder(this)
    		.append("date", date)
    		.append("contact", contact)
    		.append("countryWhereChargeWasMade", countryWhereChargeWasMade)
    		.toString();
    }
}
