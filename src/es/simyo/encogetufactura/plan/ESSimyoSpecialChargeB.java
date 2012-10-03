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
package es.simyo.encogetufactura.plan;

import com.conzebit.myplan.core.plan.SpecialChargeProcessor;

public class ESSimyoSpecialChargeB implements SpecialChargeProcessor {
	
	private Double initialPrice = null;
	
	private Double pricePerSecond = null;

	protected ESSimyoSpecialChargeB(Double initialPrice, Double pricePerMinute) {
		this.initialPrice = initialPrice;
		if (pricePerMinute != null) {
			this.pricePerSecond = pricePerMinute / 60;
		}
	}
	
	public Double process(long duration) {
		Double ret = this.initialPrice != null ? this.initialPrice : 0.0;
		if (this.pricePerSecond != null) {
			ret += duration * this.pricePerSecond;
		}
		return ret;
	}
}
