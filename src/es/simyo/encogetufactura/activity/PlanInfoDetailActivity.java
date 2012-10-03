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
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.conzebit.util.AndroidUtils;

import es.simyo.encogetufactura.EncogeTuFacturaApp;
import es.simyo.encogetufactura.R;

public class PlanInfoDetailActivity extends SherlockActivity {

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
        
        super.setContentView(R.layout.planinfo_detail);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayUseLogoEnabled(true);
        ab.setLogo(R.drawable.simyo);
        
        Bundle bundle = this.getIntent().getExtras();
        int plan = bundle.getInt("plan");
        int resid = 0;
        String data =null;
        switch (plan) {
        case R.id.planinfo_gigaplan:
        	resid = R.string.planinfo_gigaplan;
        	data = getString(R.string.planinfo_gigaplan_detail);
        	break;
        case R.id.planinfo_5cents:
        	resid = R.string.planinfo_5cents;
        	data = getString(R.string.planinfo_5cents_detail);
        	break;
        case R.id.planinfo_3cents:
        	resid = R.string.planinfo_3cents;
        	data = getString(R.string.planinfo_3cents_detail);
        	break;
        case R.id.planinfo_0_6cents:
        	resid = R.string.planinfo_0_6cents;
        	data = getString(R.string.planinfo_0_6cents_detail);
        	break;
        }
        TextView name = (TextView) this.findViewById(R.id.planinfo_detail_planname);
        name.setText(resid);
        
        WebView webView = (WebView) this.findViewById(R.id.planinfo_detail_webview);
        webView.loadData(data, "text/html", "UTF-8");
        
		this.findViewById(R.id.planinfo_detail_call).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AndroidUtils.callPhone(PlanInfoDetailActivity.this, EncogeTuFacturaApp.CALL_NUMBER);
			}
		});
    	this.findViewById(R.id.planinfo_detail_web).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
	        	AndroidUtils.openURL(PlanInfoDetailActivity.this, "http://www.simyo.es/tarifas.html");
	        }
		});
   }
}