/*
 * This file is part of Simyo - Encoge tu factura.
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
package es.simyo.encogetufactura.activity;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
import com.conzebit.myplan.android.util.Settings;

import es.simyo.encogetufactura.EncogeTuFacturaApp;
import es.simyo.encogetufactura.R;

public class SettingsActivity extends SherlockPreferenceActivity {
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
        case android.R.id.home:
            finish();
	    }
        return(true);
	}	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setLogo(R.drawable.simyo);
        super.setPreferenceScreen(createPreferenceHierarchy());
    }

    private PreferenceScreen createPreferenceHierarchy() {
    	
        // Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
        
        // Billing Day
        ListPreference billingDayPref = new ListPreference(this);
        String [] billingDayList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        billingDayPref.setEntries(billingDayList);
        billingDayPref.setEntryValues(billingDayList);
        billingDayPref.setDialogTitle(R.string.settings_billingday);
        billingDayPref.setKey(Settings.Setting.BILLINGDAY.toString());
        billingDayPref.setTitle(R.string.settings_billingday);
        billingDayPref.setSummary(Settings.getBillingDay(this));
        
        billingDayPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary(newValue.toString());
				EncogeTuFacturaApp app = (EncogeTuFacturaApp) SettingsActivity.this.getApplication();
				app.setBillingPeriod(Integer.parseInt(newValue.toString()));
				return true;
			}
        });
        root.addPreference(billingDayPref);

        // VAT
        final ListPreference vatPref = new ListPreference(this);
        vatPref.setEntries(R.array.settings_vat);
        vatPref.setEntryValues(R.array.settings_vat_value);
        vatPref.setDialogTitle(R.string.settings_taxes);
        vatPref.setKey(Settings.Setting.VAT.toString());
        vatPref.setTitle(R.string.settings_taxes);
       	vatPref.setSummary(Settings.getVATItem(this, Settings.getVATValue(this)));
        vatPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
		  	public boolean onPreferenceChange(Preference preference, Object newValue) {
			  	vatPref.setSummary(Settings.getVATItem(SettingsActivity.this, newValue.toString()));
			  	Settings.setVATValue(SettingsActivity.this, newValue.toString());
				return true;
			}
        });
        root.addPreference(vatPref);
        
        
        return root;
    }
}
