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

import java.util.ArrayList;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.conzebit.myplan.core.call.CallStat;

import es.simyo.encogetufactura.R;

public class StatisticsDetailActivity extends SherlockListActivity {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
        case android.R.id.home:
            finish();
	    }
        return(true);
	}	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setTitle(R.string.stats_title);
        ab.setDisplayUseLogoEnabled(true);
        ab.setLogo(R.drawable.simyo);
        
        super.setContentView(R.layout.statistics);
        this.getListView().setCacheColorHint(0);
        
        Bundle bundle = this.getIntent().getExtras();
        Integer stat = bundle.getInt("stat");
        
        ArrayList<CallStat> callStats = StatisticsHelper.getStatisticsData(this, null, null, stat);
        
        StatisticsAdapter statsAdapter = new StatisticsAdapter(this, 0, callStats, R.layout.statistics_line);
        setListAdapter(statsAdapter);
    }
}