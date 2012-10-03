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
package com.conzebit.myplan.core.contact;

import com.conzebit.myplan.core.msisdn.MsisdnType;
import com.conzebit.util.ToStringBuilder;


/**
 * Represents a contact
 * @author jmsanzg@gmail.com
 */
public class Contact {

	private String msisdn;
	
	private String contactName;
	
	private MsisdnType msisdnType;
	
	private String isoCountryCode;
	
	private String nsn;

	public Contact(String msisdn, String contactName, MsisdnType msisdnType, String isoCountryCode, String nsn) {
		this.msisdn = msisdn;
		this.contactName = contactName;
		this.msisdnType = msisdnType;
		this.isoCountryCode = isoCountryCode;
		this.nsn = nsn;

		if (this.contactName == null) {
			this.contactName = this.msisdn;
		}
	}
	
	public String getMsisdn() {
		return this.msisdn;
	}
	
	public String getContactName() {
		return this.contactName;
	}
	
	public MsisdnType getMsisdnType() {
		return this.msisdnType;
	}
	
	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	public String getNSN() {
		return nsn;
	}

	public void setMsisdnType(MsisdnType msisdnType) {
		this.msisdnType = msisdnType;
	}

	@Override
	public boolean equals(Object another) {
		Contact anotherContact = null;
		if (another instanceof ContactValue) {
			anotherContact = ((ContactValue) another).getContact();
		}
		if (this.nsn == null || anotherContact == null || anotherContact.nsn == null) {
			return false;
		}
		
		return this.nsn.equals(anotherContact.nsn);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("msisdn", msisdn)
			.append("msisdnType", msisdnType)
			.append("contactName", contactName)
			.append("nsn", nsn)
			.append("isoCountryCode", isoCountryCode)
			.toString();
	}
}