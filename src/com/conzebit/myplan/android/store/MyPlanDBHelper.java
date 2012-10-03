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
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.conzebit.myplan.core.datarxtx.DataRxTx;

public class MyPlanDBHelper extends SQLiteOpenHelper {
	
	private static final String LOG_TAG = "MyPlanDBHelper";

    private static final String DATABASE_NAME = "myplan.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COUNTRY_TABLE_CREATE = "CREATE TABLE country(date NUMBER, code TEXT)";
    private static final String COUNTRY_INDEX_CREATE = "CREATE INDEX country_index ON country (date DESC)";
    private static final String COUNTRY_SELECT_LAST = "SELECT code FROM country ORDER BY date DESC LIMIT 1";
    private static final String COUNTRY_SELECT_DATE_FROM = "SELECT date, code FROM country WHERE date < ? ORDER BY date DESC LIMIT 1";
    private static final String COUNTRY_SELECT_DATE_TO = "SELECT date, code FROM country WHERE date > ? ORDER BY date LIMIT 1";
    private static final String COUNTRY_INSERT = "INSERT INTO country(date, code) VALUES (?, ?)";
    
    private static final String GPRS_TABLE_CREATE = "CREATE TABLE gprs (date NUMBER, rx NUMBER, tx NUMBER, country TEXT)";
    private static final String GPRS_INDEX_CREATE = "CREATE INDEX gprs_index ON gprs (date DESC)";
    private static final String GPRS_INSERT = "INSERT INTO gprs(date, rx, tx, country) VALUES (?, ?, ?, ?)";
    private static final String GPRS_SELECT_BETWEEN = "SELECT date, rx, tx, country FROM gprs WHERE date > ? and date < ? ORDER BY date";
    private static final String GPRS_SELECT_LAST = "SELECT date, rx, tx, country FROM gprs ORDER BY date DESC LIMIT 1";
	private static final long GPRS_MIN_DIFF = 512L * 1024L; // 512KB
	private static final String GPRS_SELECT_ALL = "SELECT date, rx, tx, country FROM gprs ORDER BY date";
    
    public class CountryInterval {
    	public long from;
    	public long to;
    	public String countryCode;
    }
    
    public class DataInterval {
    	public Long fromTimestamp = null;
    	public Long toTimestamp = null;
    	public Long rx = null;
    	public Long tx = null;
    }
    
    public MyPlanDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	try {
	        db.execSQL(COUNTRY_TABLE_CREATE);
	        db.execSQL(COUNTRY_INDEX_CREATE);
	        db.execSQL(GPRS_TABLE_CREATE);
	        db.execSQL(GPRS_INDEX_CREATE);
    	} catch (Exception e) {
    		Log.e(LOG_TAG, "Error creating schema", e);
    	}
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Do nothing for now
	}
	
	public void storeCountryIfNew(String countryCode) {
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			Cursor cursor = db.rawQuery(COUNTRY_SELECT_LAST, null);
			boolean insert = true;
			if (cursor != null && cursor.moveToNext()) {
				String dbCountryCode = cursor.getString(0);
				insert = !countryCode.equals(dbCountryCode);
			}
			if (insert) {
				Object[] args = {System.currentTimeMillis(), countryCode.toUpperCase()};
				db.execSQL(COUNTRY_INSERT, args);
				Log.d(LOG_TAG, "Storing new countryCode: " + countryCode);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error storing country", e);
		} finally {
			db.endTransaction();
		}
		db.close();
	}

	public String getCountryForDate(long dateInMillis, String defaultCountryCode) {
		SQLiteDatabase db = getReadableDatabase();
		String countryCode = null;
		try {
			String[] args = {"" + dateInMillis};
			Cursor cursor = db.rawQuery(COUNTRY_SELECT_DATE_FROM, args);
			if (cursor != null && cursor.moveToNext()) {
				countryCode = cursor.getString(1);
			} else {
				countryCode = defaultCountryCode;
			}
			cursor.close();
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error retrieving country", e);
		}
		db.close();
		return countryCode;
	}
	
	public CountryInterval getCountryIntervalForDate(long dateInMillis, String defaultCountryCode) {
		SQLiteDatabase db = getReadableDatabase();
		CountryInterval ret = new CountryInterval();
		try {
			String[] args = {"" + dateInMillis};
			Cursor cursor = db.rawQuery(COUNTRY_SELECT_DATE_FROM, args);
			if (cursor != null && cursor.moveToNext()) {
				ret.from = cursor.getLong(0);
				ret.countryCode = cursor.getString(1);
			} else {
				ret.from = dateInMillis;
				ret.countryCode = defaultCountryCode;
			}
			cursor.close();
			cursor = db.rawQuery(COUNTRY_SELECT_DATE_TO, args);
			if (cursor != null && cursor.moveToNext()) {
				ret.to = cursor.getLong(0);
			} else {
				ret.to = System.currentTimeMillis();
			}
			cursor.close();
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error retrieving country", e);
		}
		db.close();
		return ret;
	}
	
	public void storeDataRxTx(long rx, long tx, String countryCode) {
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			Cursor cursor = db.rawQuery(GPRS_SELECT_LAST, null);
			boolean insert = true;
			if (cursor != null && cursor.moveToNext()) {
				Long dbRx = cursor.getLong(1);
				Long dbTx = cursor.getLong(2);
				String dbCountryCode = cursor.getString(3);
				insert = !countryCode.equals(dbCountryCode) || (rx - dbRx >= GPRS_MIN_DIFF) || (tx - dbTx >= GPRS_MIN_DIFF) || (rx - dbRx < 0) || (tx - dbTx < 0);
			}
			if (insert) {
				Object[] args = {System.currentTimeMillis(), rx, tx, countryCode};
				db.execSQL(GPRS_INSERT, args);
				Log.d(LOG_TAG, "Inserting rx/tx: " + rx + "," + tx + "-" + countryCode);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error storing country", e);
		} finally {
			db.endTransaction();
		}
		db.close();
	}

	public ArrayList<DataRxTx> getDataRxTx(long from, long to) {
		HashMap<String, DataRxTx> ret = new HashMap<String, DataRxTx>();
		SQLiteDatabase db = getReadableDatabase();
		try {
			String[] args = {"" + from, "" + to};
			Cursor cursor = db.rawQuery(GPRS_SELECT_BETWEEN, args);
			if (cursor != null && cursor.moveToNext()) {
				Date fromTimestamp = null;
				Date toTimestamp = null;
				long previousRx = 0;
				long previousTx = 0;
				String previousCountry = null;
				do {
					long timestamp = cursor.getLong(0);
					long rx = cursor.getLong(1);
					long tx = cursor.getLong(2);
					String country = cursor.getString(3);
					if (cursor.isFirst()) {
						fromTimestamp = new Date(timestamp);
						previousRx = rx;
						previousTx = tx;
						previousCountry = country; 
					}
					if (cursor.isLast()) {
						toTimestamp = new Date(timestamp);
					}
					if (rx < previousRx || tx < previousTx) {
						previousRx = 0;
						previousTx = 0;
					}
					DataRxTx dataRxTx = ret.get(previousCountry);
					if (dataRxTx == null) {
						dataRxTx = new DataRxTx();
						dataRxTx.setCountryWhereChargeWasMade(previousCountry);
						dataRxTx.setFrom(fromTimestamp);
						ret.put(previousCountry, dataRxTx);
					}
					dataRxTx.setRx(dataRxTx.getRx() + rx - previousRx);
					dataRxTx.setTx(dataRxTx.getTx() + tx - previousTx);
					
					previousRx = rx;
					previousTx = tx;
					previousCountry = country;
				} while (cursor.moveToNext());
				for (DataRxTx value : ret.values()) { // end timestamp not known until the end
					value.setTo(toTimestamp);
				}
			}
			cursor.close();
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error retrieving gprs data", e);
		}
		db.close();
		return new ArrayList<DataRxTx>(ret.values());
	}
	
	public String getAll() {
		StringBuilder ret = new StringBuilder();
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor cursor = db.rawQuery(GPRS_SELECT_ALL, null);
			if (cursor != null && cursor.moveToNext()) {
				do {
					long timestamp = cursor.getLong(0);
					long rx = cursor.getLong(1);
					long tx = cursor.getLong(2);
					String country = cursor.getString(3);
					ret.append(timestamp)
						.append(',')
						.append(rx)
						.append(',')
						.append(tx)
						.append(',')
						.append(country)
						.append(',')
						.append(cursor.isFirst())
						.append(',')
						.append(cursor.isLast())
						.append('\n');
				} while (cursor.moveToNext());
			}
			cursor.close();
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error retrieving gprs data", e);
		}
		db.close();
		return ret.toString();
	}
	
}
