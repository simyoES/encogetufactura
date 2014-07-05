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

import android.content.Context;

import com.conzebit.myplan.core.Chargeable;
import com.conzebit.myplan.core.call.Call;
import com.conzebit.myplan.core.msisdn.MsisdnTypeService;
import com.conzebit.myplan.core.sms.Sms;

public class LogStoreServiceTest {

	public static ArrayList<Chargeable> getTestChargeable(Context context) {
		ArrayList<Chargeable> ret = getTestCalls();
		ret.addAll(getTestSms());
		return ret;
	}
	
	private static ArrayList<Chargeable> getTestCalls() {
		MsisdnTypeService msisdnTypeService = MsisdnTypeService.getInstance();
		
		ArrayList<Chargeable> calls = new ArrayList<Chargeable>();

		
		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("610000000", "Alicia", msisdnTypeService), getTestDate(2014, 7, 2, 8, 0), "ES"));
		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (45 * 60 + 30), ContactStore.createContact("620000000", "Bernardo", msisdnTypeService), getTestDate(2014, 7, 2, 18, 15), "ES"));
		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (5 * 60 + 30), ContactStore.createContact("+440000000", "Charles", msisdnTypeService), getTestDate(2014, 7, 2, 12, 00), "UK"));
		
		//Llamadas nacionales
		// 6xxxxxxxx/+346xxxxxxxx/00346xxxxxxxx (móvil)
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("610000000", "Alice (móvil)", msisdnTypeService), getTestDate(2012, 8, 1, 8, 0), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+34610000000", "Alice (móvil)", msisdnTypeService), getTestDate(2012, 8, 1, 8, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("0034610000000", "Alice (móvil)", msisdnTypeService), getTestDate(2012, 8, 1, 8, 30), "ES"));
//		// 7Yxxxxxxx/+347Yxxxxxxx/00347Yxxxxxxx (móvil)
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("710000000", "Bob (móvil)", msisdnTypeService), getTestDate(2012, 8, 1, 9, 0), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+34710000000", "Bob (móvil)", msisdnTypeService), getTestDate(2012, 8, 1, 9, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("0034710000000", "Bob (móvil)", msisdnTypeService), getTestDate(2012, 8, 1, 9, 30), "ES"));
//		// 8Yxxxxxxx/+348Yxxxxxxx/00348Yxxxxxxx (fijo)
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("810000000", "Charles (fijo)", msisdnTypeService), getTestDate(2012, 8, 1, 10, 0), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+34810000000", "Charles (fijo)", msisdnTypeService), getTestDate(2012, 8, 1, 10, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("0034810000000", "Charles (fijo)", msisdnTypeService), getTestDate(2012, 8, 1, 10, 30), "ES"));
//		// 9Yxxxxxxx/+349Yxxxxxxx/00349Yxxxxxxx (fijo)
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("910000000", "David (fijo)", msisdnTypeService), getTestDate(2012, 8, 1, 11, 0), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+34910000000", "David (fijo)", msisdnTypeService), getTestDate(2012, 8, 1, 11, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("0034910000000", "David (fijo)", msisdnTypeService), getTestDate(2012, 8, 1, 11, 30), "ES"));
//		
//		// Llamadas especiales
//		// Números con menos de 3 dígitos
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("10", "No tarificado", msisdnTypeService), getTestDate(2012, 8, 1, 12, 00), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+3410", "No tarificado", msisdnTypeService), getTestDate(2012, 8, 1, 12, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("003410", "No tarificado", msisdnTypeService), getTestDate(2012, 8, 1, 12, 30), "ES"));
//		// Números con 3 dígitos empezando por 0XY
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("010", "010 (0,135)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 00), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("010", "010 (0,405)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 05), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("010", "010 (0,945)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 10), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("012", "012 (0,36)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("012", "012 (0,36)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("012", "012 (0,36)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 25), "ES"));
//		
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("016", "016 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("016", "016 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("016", "016 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 40), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("060", "060 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 45), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("060", "060 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 13, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("060", "060 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 0), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("061", "061 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 5), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("061", "061 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("061", "061 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 15), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("062", "062 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("062", "062 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("062", "062 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 30), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("080", "080 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("080", "080 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("080", "080 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 45), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("082", "082 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("082", "082 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 14, 55), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("082", "082 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 0), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("083", "083 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 5), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("083", "083 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("083", "083 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 15), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("085", "085 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("085", "085 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("085", "085 (0,0)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 30), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("091", "091 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("091", "091 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("091", "091 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 45), "ES"));
//		
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("092", "092 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("092", "092 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 15, 55), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("092", "092 (0,1202)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 0), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("096", "096 (0,9376)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 5), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("096", "096 (0,9376)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("096", "096 (0,9376)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 15), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("098", "098 (0,204)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("098", "098 (0,612)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("098", "098 (1,428)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 30), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("099", "099 (--)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("099", "099 (--)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("099", "099 (--)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 45), "ES"));
//
//		// Números de 4 a 8 dígitos
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (30), ContactStore.createContact("1006", "1006 (--)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("1006", "1006 (--)", msisdnTypeService), getTestDate(2012, 8, 1, 16, 55), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("1006", "1006 (--)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 0), "ES"));
//		
//		// 700xxxxxx/704xxxxxx/707xxxxxx
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("700000000", "700000000 (0,30)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 5), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("704000000", "704000000 (0,30)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("707000000", "707000000 (0,30)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 15), "ES"));
//		// 800xxxxxx 
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800000000", "800000000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800100000", "800100000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800200000", "800200000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800300000", "800300000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800400000", "800400000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800500000", "800500000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 45), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800600000", "800600000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800700000", "800700000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 17, 55), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800800000", "800800000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 0), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("800900000", "800900000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 5), "ES"));
//		// 803xxxxxx/806xxxxxx/807xxxxxx
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803000000", "803000000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803000000", "803000000 (2,331)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803100000", "803100000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803100000", "803100000 (2,331)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803200000", "803200000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803200000", "803200000 (3,668)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803300000", "803300000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803300000", "803300000 (3,668)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803400000", "803400000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803400000", "803400000 (4,432)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803500000", "803500000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803500000", "803500000 (4,432)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803600000", "803600000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803600000", "803600000 (6,342)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803700000", "803700000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 45), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803700000", "803700000 (6,342)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 45), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803800000", "803800000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803800000", "803800000 (11,308)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("803900000", "803900000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 55), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("803900000", "803900000 (23,532)", msisdnTypeService), getTestDate(2012, 8, 1, 18, 55), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806000000", "806000000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806000000", "806000000 (2,331)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806100000", "806100000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806100000", "806100000 (2,331)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806200000", "806200000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806200000", "806200000 (3,668)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806300000", "806300000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806300000", "806300000 (3,668)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806400000", "806400000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806400000", "806400000 (4,432)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806500000", "806500000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806500000", "806500000 (4,432)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806600000", "806600000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806600000", "806600000 (6,342)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806700000", "806700000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 45), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806700000", "806700000 (6,342)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 45), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806800000", "806800000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806800000", "806800000 (11,308)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("806900000", "806900000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 55), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("806900000", "806900000 (23,532)", msisdnTypeService), getTestDate(2012, 8, 1, 19, 55), "ES"));
//
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807000000", "807000000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807000000", "807000000 (2,331)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807100000", "807100000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807100000", "807100000 (2,331)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807200000", "807200000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807200000", "807200000 (3,668)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807300000", "807300000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807300000", "807300000 (3,668)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807400000", "807400000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807400000", "807400000 (4,432)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807500000", "807500000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807500000", "807500000 (4,432)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807600000", "807600000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807600000", "807600000 (6,342)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 40), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807700000", "807700000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 45), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807700000", "807700000 (6,342)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 45), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807800000", "807800000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807800000", "807800000 (11,308)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 50), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (19), ContactStore.createContact("807900000", "807900000 (0,23)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 55), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("807900000", "807900000 (23,532)", msisdnTypeService), getTestDate(2012, 8, 1, 20, 55), "ES"));
//		
//		// 900xxxxxx
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("900100000", "900100000 (0,00)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 0), "ES"));
//		
//		// 901xxxxxx/902xxxxxx/905xxxxxx 
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("901100000", "901100000 (0,99)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 5), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("902100000", "902100000 (1,41)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("905100000", "905100000 (0,75)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 15), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("905200000", "905300000 (1,050)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 20), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("905400000", "905400000 (1,650)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 25), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("905500000", "905500000 (1,650)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 30), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("905700000", "905700000 (1,050)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 35), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("905800000", "905800000 (1,650)", msisdnTypeService), getTestDate(2012, 8, 1, 21, 40), "ES"));
//
//		// Llamadas al extranjero
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("+47123456789", "Norway (1,40)", msisdnTypeService), getTestDate(2012, 8, 1, 22, 0), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("+53123456789", "Cuba (4,55)", msisdnTypeService), getTestDate(2012, 8, 1, 22, 05), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("+850123456789", "North Korea (4,55)", msisdnTypeService), getTestDate(2012, 8, 1, 22, 10), "ES"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (3 * 60 + 30), ContactStore.createContact("+252123456789", "Somalia (4,55)", msisdnTypeService), getTestDate(2012, 8, 1, 22, 15), "ES"));
//		
//		// Llamadas en roaming
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+34610000000", "Alice (roaming)", msisdnTypeService), getTestDate(2012, 8, 1, 23, 0), "FR"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+34710000000", "Bob (roaming)", msisdnTypeService), getTestDate(2012, 8, 1, 23, 5), "FR"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+34810000000", "Charles (roaming)", msisdnTypeService), getTestDate(2012, 8, 1, 23, 10), "FR"));
//		calls.add(new Call(Call.CALL_TYPE_SENT, (long) (1 * 60 + 30), ContactStore.createContact("+34910000000", "David (roaming)", msisdnTypeService), getTestDate(2012, 8, 1, 23, 15), "FR"));

		return calls;
	}
	
	private static ArrayList<Chargeable> getTestSms() {
		MsisdnTypeService msisdnTypeService = MsisdnTypeService.getInstance();
		ArrayList<Chargeable> sms = new ArrayList<Chargeable>();

		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("610000000", "David", msisdnTypeService), getTestDate(2014, 7, 2, 10, 55), "ES"));
		
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+34610000000", "Alice (0,09)", msisdnTypeService), getTestDate(2012, 8, 2, 8, 0), "ES"));
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+34710000000", "Bob (0,09)", msisdnTypeService), getTestDate(2012, 8, 2, 8, 5), "ES"));
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+34810000000", "Charles (0,09)", msisdnTypeService), getTestDate(2012, 8, 2, 8, 10), "ES"));
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+34910000000", "David (0,09)", msisdnTypeService), getTestDate(2012, 8, 2, 8, 15), "ES"));
//
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+34610000000", "Alice (roaming)", msisdnTypeService), getTestDate(2012, 8, 2, 9, 0), "FR"));
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+34710000000", "Bob (roaming)", msisdnTypeService), getTestDate(2012, 8, 2, 9, 5), "FR"));
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+34810000000", "Charles (roaming)", msisdnTypeService), getTestDate(2012, 8, 2, 9, 10), "FR"));
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+34910000000", "David (roaming)", msisdnTypeService), getTestDate(2012, 8, 2, 9, 15), "FR"));
//
//		sms.add(new Sms(Sms.SMS_TYPE_SENT, ContactStore.createContact("+47123456789", "Norway (0,15)", msisdnTypeService), getTestDate(2012, 8, 2, 10, 0), "ES"));
		return sms;
	}

	private static Calendar getTestDate(int year, int month, int date, int hourOfDay, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, hourOfDay, minute);
		return calendar;
	}

	
}
