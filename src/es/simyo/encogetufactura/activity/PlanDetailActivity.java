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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.conzebit.myplan.core.plan.PlanChargeable;
import com.conzebit.myplan.core.plan.PlanService;
import com.conzebit.myplan.core.plan.PlanSummary;
import com.conzebit.util.AndroidUtils;

import es.simyo.encogetufactura.EncogeTuFacturaApp;
import es.simyo.encogetufactura.R;

public class PlanDetailActivity extends SherlockListActivity {

	private String operator;
	private String planName;
	private int planResourceID;
	
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
        
        super.setContentView(R.layout.detail);
        this.getListView().setCacheColorHint(0);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        ab.setTitle(R.string.plan_detail_title);
        ab.setDisplayUseLogoEnabled(true);
        ab.setLogo(R.drawable.simyo);

        Bundle bundle = this.getIntent().getExtras();
        operator = bundle.getString("operator");
        planName = bundle.getString("planname");
        planResourceID = bundle.getInt("planresourceid");
        Log.d("LL", "planresourceid" + planResourceID);

        EncogeTuFacturaApp myPlanApp = (EncogeTuFacturaApp) this.getApplication();
        PlanService planService = myPlanApp.getPlanService();
        ArrayList<PlanChargeable> planCalls = planService.getPlanCalls(operator, planName);
        
        LinearLayout header = (LinearLayout) this.findViewById(R.id.detail_summary);
        header.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PlanDetailActivity.this, PlanInfoDetailActivity.class);
		        Bundle bundle = new Bundle();
		        bundle.putInt("plan", planResourceID);
		        Log.d("MM", "planresourceid" + planResourceID);
		        intent.putExtras(bundle);
		        startActivity(intent);
			} 
		});
        PlanSummary planSummary = planService.getPlanSummary(operator, planName);
        PlanHelper.getPlanSummaryView(this, header, planSummary, R.layout.detail_header);
        
        this.findViewById(R.id.purchase).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AndroidUtils.callPhone(PlanDetailActivity.this, EncogeTuFacturaApp.CALL_NUMBER);
			} 
		});
		this.findViewById(R.id.appdetail_button).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(PlanDetailActivity.this, AppDetailActivity.class));
			}
		});
        
        PlanDetailAdapter detailAdapter = new PlanDetailAdapter(this, 0, planCalls);
        setListAdapter(detailAdapter);
    }
}