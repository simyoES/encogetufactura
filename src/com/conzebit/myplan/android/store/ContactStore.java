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

import com.conzebit.myplan.core.contact.Contact;
import com.conzebit.myplan.core.msisdn.MsisdnType;
import com.conzebit.myplan.core.msisdn.MsisdnTypeService;
import com.conzebit.util.MsisdnUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import es.simyo.encogetufactura.EncogeTuFacturaApp;

public class ContactStore {

	protected static Contact createContact(String callNumber, String contactName, MsisdnTypeService msisdnTypeService) {
		PhoneNumber phoneNumber = MsisdnUtil.getPhoneNumber(callNumber, EncogeTuFacturaApp.DEFAULT_COUNTRY);
		String isoCountryCode = MsisdnUtil.getISOCountryCode(phoneNumber, EncogeTuFacturaApp.DEFAULT_COUNTRY);
		String nsn = MsisdnUtil.getNSN(phoneNumber, callNumber);
		MsisdnType type = msisdnTypeService.getMsisdnType(isoCountryCode, nsn, EncogeTuFacturaApp.DEFAULT_COUNTRY);
		return new Contact(callNumber, contactName, type, isoCountryCode, nsn);
	}
}
