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

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.conzebit.util.AndroidUtils;

import es.simyo.encogetufactura.EncogeTuFacturaApp;
import es.simyo.encogetufactura.R;

public class AppDetailActivity extends SherlockActivity {

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
        
        super.setContentView(R.layout.app_detail);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayUseLogoEnabled(true);
        ab.setLogo(R.drawable.simyo);
        
        WebView webView = (WebView) this.findViewById(R.id.appdetail_webview);
        webView.loadData(getString(R.string.appdetail_text), "text/html", "UTF-8");

        this.findViewById(R.id.summary_call_button).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AndroidUtils.callPhone(AppDetailActivity.this, EncogeTuFacturaApp.CALL_NUMBER);
			}
		});

   }
}