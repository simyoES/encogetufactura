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
import java.util.Comparator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.conzebit.myplan.android.store.LogStoreService;
import com.conzebit.myplan.android.util.Settings;
import com.conzebit.myplan.core.plan.PlanChargeable;
import com.conzebit.myplan.core.plan.PlanService;
import com.conzebit.myplan.core.plan.PlanSummary;
import com.conzebit.util.AndroidUtils;
import com.conzebit.util.Formatter;

import es.simyo.encogetufactura.EncogeTuFacturaApp;
import es.simyo.encogetufactura.R;
import es.simyo.encogetufactura.receiver.NetworkReceiver;

public class PlanSummaryActivity extends SherlockActivity {
	
	private boolean nextBillingPeriodEnabled = false;
	private boolean showTermsAndConditions = false;
	private boolean showSettings = false;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        if (!Settings.showTermsAndConditions(this)) {
        	super.onCreateOptionsMenu(menu);
			MenuInflater menuInflater = getSupportMenuInflater();
			menuInflater.inflate(R.menu.summary_menu, menu);
        }
		return true;
    }
	
	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch(item.getItemId()) {
			case R.id.menu_settings:
				showSettings = true;
				startActivity(new Intent(this, SettingsActivity.class));
    			break;
			case R.id.menu_stats:
				startActivity(new Intent(this, StatisticsSummaryActivity.class));
    			break;
			case R.id.menu_share:
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
			    shareIntent.setType("text/plain");
			    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Paga lo Justo con Simyo");  
			    shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=es.simyo.encogetufactura");
			    startActivity(shareIntent);
			default:
	            return super.onOptionsItemSelected(item);
		}
		return true;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showTermsAndConditions = Settings.showTermsAndConditions(this);
        if (showTermsAndConditions) {
        	this.setContentView(R.layout.terms_and_conditions);
        	ActionBar ab = getSupportActionBar();
            ab.setHomeButtonEnabled(false);
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayUseLogoEnabled(true);
            ab.setLogo(R.drawable.simyo);
            //ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

            WebView webView = (WebView) this.findViewById(R.id.terms_and_conditions_text);
            webView.loadData(getString(R.string.disclaimer_text), "text/html", "UTF-8");

        	Button button = (Button) this.findViewById(R.id.disclaimer_ok_button);
        	button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
		        	Settings.setTermsShown(v.getContext(), "yes");
		        	PlanSummaryActivity.this.startActivity(new Intent(PlanSummaryActivity.this, SettingsActivity.class).putExtra("showbuttons", true));
		        }
			});
        } else {
        	loadView();
        }
    }
    
    @Override
	protected void onResume() {
    	super.onResume();
        if ((!Settings.showTermsAndConditions(this) && showTermsAndConditions) || showSettings) {
        	showTermsAndConditions = false;
        	showSettings = false;
        	loadView();
        }
	}

	private void loadView() {
    	NetworkReceiver.schedule(this);
    	
        super.setContentView(R.layout.summary);
        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayUseLogoEnabled(true);
        ab.setLogo(R.drawable.simyo);
        
		this.findViewById(R.id.summary_call_button).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AndroidUtils.callPhone(PlanSummaryActivity.this, EncogeTuFacturaApp.CALL_NUMBER);
			}
		});
		
		final EncogeTuFacturaApp app = (EncogeTuFacturaApp) getApplication();

		final ImageView billingPeriodNext = (ImageView) findViewById(R.id.billing_period_next);
		billingPeriodNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (nextBillingPeriodEnabled) {
					nextBillingPeriodEnabled = app.nextBillingPeriod();
					if (nextBillingPeriodEnabled) {
						billingPeriodNext.setImageResource(R.drawable.square_direction_right);
					} else {
						billingPeriodNext.setImageResource(R.drawable.square_direction_right_disabled);
					}
			        loadMyPlanData();
				}
			}
		});
		
		findViewById(R.id.billing_period_previous).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				app.previousBillingPeriod();
				nextBillingPeriodEnabled = true;
				billingPeriodNext.setImageResource(R.drawable.square_direction_right);
		        loadMyPlanData();
			}
		});
		
        loadMyPlanData();
    }

    private void loadMyPlanData() {
		new LoadDataTask().execute();
    	TextView billingPeriod = (TextView) findViewById(R.id.billing_period);
    	billingPeriod.setText(PlanHelper.getBillingPeriod(this));
    }

	private class LoadDataTask extends AsyncTask<Void, Void, ArrayList<PlanSummary>> {
		private ProgressDialog dialog = new ProgressDialog(PlanSummaryActivity.this);

		protected void onPreExecute() {
			this.dialog.setMessage(PlanSummaryActivity.this.getText(R.string.summary_calculating));
			this.dialog.show();
		}

		protected ArrayList<PlanSummary> doInBackground(final Void... args) {
			EncogeTuFacturaApp app = (EncogeTuFacturaApp) PlanSummaryActivity.this.getApplication();		
	        PlanService planService = app.getPlanService();
	      	planService.process(LogStoreService.getAll(PlanSummaryActivity.this, app.getStartBillingDate(), app.getEndBillingDate()));
	        
	        ArrayList<PlanSummary> planSummaries = planService.getPlanSummaries(null, new Comparator<PlanSummary>() {
				public int compare(PlanSummary arg0, PlanSummary arg1) {
					double arg0Total = arg0.getTotalPrice(); 
					double arg1Total = arg1.getTotalPrice();
					if (arg0Total < arg1Total) {
						return -1;
					} else if (arg0Total > arg1Total) {
						return 1;
					}
					return 0;
				}
	        });

	        ArrayList<PlanSummary> ret = new ArrayList<PlanSummary>();
	        for (PlanSummary ps : planSummaries) {
	       		ret.add(ps);
	        }
	        return ret;
		}

		protected void onPostExecute(final ArrayList<PlanSummary> planSummaries) {
			try {
				this.dialog.dismiss();
				this.dialog = null;
			} catch (Exception e) {
				Log.e("simyo", "error", e);
			}
			PlanSummary ps = planSummaries.get(0); // just one ;)
			ArrayList<PlanChargeable> chargeableList = ps.getPlanCalls();
			Integer minutes = chargeableList.get(0).getPrice().intValue();
			Integer mb = chargeableList.get(1).getPrice().intValue();
			Double price = chargeableList.get(2).getPrice();
	        double vat = 1 + (((double) Settings.getVATValue(PlanSummaryActivity.this)) / 100);
	        Integer monthlyFee = Settings.getMonthlyFee(PlanSummaryActivity.this);

			StringBuilder content = new StringBuilder();

			content.append("<html><body><p align=\"center\"><b>");
			content.append("Crea <span style=\"color:#ff5900\">tu Tarifa").append("<br/><br/>");
			content.append(minutes).append("MIN + ").append(mb >= 1024?(mb/1024) + "GB":mb + "MB").append("<br/><br/>");
			content.append("por<br/><br/>");
			content.append("</span><span align=\"center\" style=\"color:#ff5900;font-size:250%\">").append(Formatter.formatDecimal(price * vat)).append("</span><span style=\"color:#ff5900\">&euro;/mes</span>").append("<br/>");
			content.append("</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size:40%\">(").append(PlanSummaryActivity.this.getString(R.string.settings_vat_vat)).append(")</span>").append("<br/><br/>");
			if (monthlyFee != null && (minutes > 0 || mb > 0)) {
				int savings = (int) ((monthlyFee.doubleValue() - (price * vat)) * 12);
				if (savings > 0) {
					content.append("<b>¡Ah&oacute;rrate unos").append("<br/>");
					content.append("<span style=\"color:#ff5900\">").append(savings).append("&euro; al a&ntilde;o!</span></b>");
				}
			}
			content.append("</p></body></html>");
			
			WebView webView = (WebView) PlanSummaryActivity.this.findViewById(R.id.summary_webview);
	        webView.loadDataWithBaseURL(null, content.toString(), "text/html", "UTF-8", null);
		}
	}
}