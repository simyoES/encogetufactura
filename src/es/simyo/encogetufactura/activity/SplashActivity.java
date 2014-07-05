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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import es.simyo.encogetufactura.R;

public class SplashActivity extends Activity {
	
	private final int SPLASH_DISPLAY_LENGTH = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				SplashActivity.this.finish();
		       	SplashActivity.this.startActivity(new Intent(SplashActivity.this, PlanSummaryActivity.class));
			}
		}, SPLASH_DISPLAY_LENGTH);
	}
}
