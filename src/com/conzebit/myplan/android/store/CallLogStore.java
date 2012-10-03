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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.conzebit.myplan.android.store.MyPlanDBHelper.CountryInterval;
import com.conzebit.myplan.core.Chargeable;
import com.conzebit.myplan.core.call.Call;
import com.conzebit.myplan.core.contact.Contact;
import com.conzebit.myplan.core.msisdn.MsisdnType;
import com.conzebit.myplan.core.msisdn.MsisdnTypeService;
import com.conzebit.util.MsisdnUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import es.simyo.encogetufactura.EncogeTuFacturaApp;

class CallLogStore {
	
	/**
	 * Obtain all calls since last invoice
	 * 
	 * @param activity
	 *            caller activity
	 * @param startBillingDay
	 *            starting billing day
	 * @return ArrayList with all calls made
	 */
	public static ArrayList<Chargeable> getCalls(Context context, Date startBillingDate, Date endBillingDate) {

		MsisdnTypeService msisdnTypeService = MsisdnTypeService.getInstance();
		
		ArrayList<Chargeable> calls = new ArrayList<Chargeable>();

		String[] projection = {
				CallLog.Calls.TYPE,
				CallLog.Calls.NUMBER,
		        CallLog.Calls.CACHED_NAME,
		        CallLog.Calls.DURATION,
		        CallLog.Calls.DATE};
		
		String selection = null;
		if (startBillingDate != null) {
			selection = CallLog.Calls.DATE + " > " + startBillingDate.getTime();
			if (endBillingDate != null) {
				selection = selection + " AND " + CallLog.Calls.DATE + " < " + endBillingDate.getTime();
			}
		}
		
		Cursor cursor = context.getContentResolver().query(
		        CallLog.Calls.CONTENT_URI, projection, selection, null,
		        CallLog.Calls.DATE + " DESC");

		int typeColumn = cursor.getColumnIndex(CallLog.Calls.TYPE);
		int numberColumn = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int contactColumn = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int durationColumn = cursor.getColumnIndex(CallLog.Calls.DURATION);
		int dateColumn = cursor.getColumnIndex(CallLog.Calls.DATE);

		try {
			if (cursor.moveToFirst()) {
				CountryStore countryStore = CountryStore.getInstance(context);
				CountryInterval countryInterval = null;
				do {
					int callType;
					switch (cursor.getInt(typeColumn)) {
					case CallLog.Calls.OUTGOING_TYPE:
						callType = Call.CALL_TYPE_SENT;
						break;
					case CallLog.Calls.INCOMING_TYPE:
						callType = Call.CALL_TYPE_RECEIVED;
						break;
					case CallLog.Calls.MISSED_TYPE:
						callType = Call.CALL_TYPE_RECEIVED_MISSED;
						break;
					default:
						continue;
					}
					String contactName = cursor.getString(contactColumn);
					String callNumber = cursor.getString(numberColumn);
					Long callDuration = cursor.getLong(durationColumn);
					if (callDuration == 0 && callType == Call.CALL_TYPE_SENT) {
						callType = Call.CALL_TYPE_SENT_MISSED;
					}
					Calendar callCal = Calendar.getInstance();
					long millis = cursor.getLong(dateColumn);
					callCal.setTimeInMillis(millis);
					if (countryInterval == null || countryInterval.to < millis) {
						countryInterval = countryStore.getCountryIntervalForDate(millis, EncogeTuFacturaApp.DEFAULT_COUNTRY);
					}
					String countryOfPhone = countryInterval.countryCode;

					PhoneNumber phoneNumber = MsisdnUtil.getPhoneNumber(callNumber, EncogeTuFacturaApp.DEFAULT_COUNTRY);
					String isoCountryCode = MsisdnUtil.getISOCountryCode(phoneNumber, EncogeTuFacturaApp.DEFAULT_COUNTRY);
					String nsn = MsisdnUtil.getNSN(phoneNumber, callNumber);
					MsisdnType type = msisdnTypeService.getMsisdnType(isoCountryCode, nsn, EncogeTuFacturaApp.DEFAULT_COUNTRY);
					Contact contact = new Contact(callNumber, contactName, type, isoCountryCode, nsn);
					Call call = new Call(callType,
							callDuration,
							contact,
					        callCal,
					        countryOfPhone
					        );
					calls.add(call);
	
				} while (cursor.moveToNext());
			}
		} finally {
			cursor.close();
		}
		return calls;
	}

	/**
	 * Obtain last call since last invoice
	 * 
	 * @param call
	 *            caller context
	 * @return ArrayList with all calls made
	 */
	public static Call getLastCall(Context context) {

		MsisdnTypeService msisdnTypeService = MsisdnTypeService.getInstance();
		
		String[] projection = { CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
		        CallLog.Calls.CACHED_NAME, CallLog.Calls.DURATION,
		        CallLog.Calls.DATE };

		Cursor cursor = context.getContentResolver().query(
		        CallLog.Calls.CONTENT_URI, projection, null, null,
		        CallLog.Calls.DATE + " DESC");

		int typeColumn = cursor.getColumnIndex(CallLog.Calls.TYPE);
		int numberColumn = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int contactColumn = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int durationColumn = cursor.getColumnIndex(CallLog.Calls.DURATION);
		int dateColumn = cursor.getColumnIndex(CallLog.Calls.DATE);

		Call call = null;
		try {
			if (cursor.moveToFirst()) {
				int callType;
				switch (cursor.getInt(typeColumn)) {
				case CallLog.Calls.OUTGOING_TYPE:
					callType = Call.CALL_TYPE_SENT;
					break;
				case CallLog.Calls.INCOMING_TYPE:
					callType = Call.CALL_TYPE_RECEIVED;
					break;
				case CallLog.Calls.MISSED_TYPE:
					callType = Call.CALL_TYPE_RECEIVED_MISSED;
					break;
				default:
					return null;
				}
				String contactName = cursor.getString(contactColumn);
				String callNumber = cursor.getString(numberColumn);
				Long callDuration = cursor.getLong(durationColumn);
				if (callDuration == 0 && callType == Call.CALL_TYPE_SENT) {
					callType = Call.CALL_TYPE_SENT_MISSED;
				}
				Calendar callCal = Calendar.getInstance();
				long millis = cursor.getLong(dateColumn);
				callCal.setTimeInMillis(millis);
				String countryOfPhone = CountryStore.getInstance(context).getCountryForDate(millis, EncogeTuFacturaApp.DEFAULT_COUNTRY);
				
				Contact contact = ContactStore.createContact(callNumber, contactName, msisdnTypeService);
				call = new Call(callType,
						callDuration,
						contact,
				        callCal,
				        countryOfPhone);
			}
		} finally {
			cursor.close();
		}
		return call;
	}
	
}
