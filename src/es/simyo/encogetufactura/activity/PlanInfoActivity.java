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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.conzebit.util.AndroidUtils;

import es.simyo.encogetufactura.R;

public class PlanInfoActivity extends SherlockActivity {

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
        
        super.setContentView(R.layout.planinfo);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayUseLogoEnabled(true);
        ab.setLogo(R.drawable.simyo);
        
       	this.findViewById(R.id.planinfo_pura).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent intent = new Intent(PlanInfoActivity.this, PlanInfoDetailActivity.class);
		        Bundle bundle = new Bundle();
		        bundle.putInt("plan", R.id.planinfo_pura);
		        intent.putExtras(bundle);
		        startActivity(intent);
	        }
		});
       	this.findViewById(R.id.planinfo_5cents).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent intent = new Intent(PlanInfoActivity.this, PlanInfoDetailActivity.class);
		        Bundle bundle = new Bundle();
		        bundle.putInt("plan", R.id.planinfo_5cents);
		        intent.putExtras(bundle);
		        startActivity(intent);
	        }
		});
       	this.findViewById(R.id.planinfo_2cents).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent intent = new Intent(PlanInfoActivity.this, PlanInfoDetailActivity.class);
		        Bundle bundle = new Bundle();
		        bundle.putInt("plan", R.id.planinfo_2cents);
		        intent.putExtras(bundle);
		        startActivity(intent);
	        }
		});
       	this.findViewById(R.id.planinfo_0_6cents).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent intent = new Intent(PlanInfoActivity.this, PlanInfoDetailActivity.class);
		        Bundle bundle = new Bundle();
		        bundle.putInt("plan", R.id.planinfo_0_6cents);
		        intent.putExtras(bundle);
		        startActivity(intent);
	        }
		});

       
    	this.findViewById(R.id.planinfo_social_simyo).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
	        	AndroidUtils.openURL(PlanInfoActivity.this, "http://blogsimyo.es/");
	        }
		});
    	this.findViewById(R.id.planinfo_social_facebook).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
	        	AndroidUtils.openURL(PlanInfoActivity.this, "http://m.facebook.com/simyo");
	        }
		});
    	this.findViewById(R.id.planinfo_social_twitter).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
	        	AndroidUtils.openURL(PlanInfoActivity.this, "http://mobile.twitter.com/simyo_es");
	        }
		});
    	this.findViewById(R.id.planinfo_social_gplus).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
	        	AndroidUtils.openURL(PlanInfoActivity.this, "https://plus.google.com/100106244565090236985/posts");
	        }
		});
    	this.findViewById(R.id.planinfo_web).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
	        	AndroidUtils.openURL(PlanInfoActivity.this, "http://www.simyo.es/tarifas.html");
	        }
		});
    }
}