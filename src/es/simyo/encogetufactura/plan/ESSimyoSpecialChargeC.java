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

public class ESSimyoSpecialChargeC implements SpecialChargeProcessor {
	
	private Double initialPrice;
	
	private Double first19seconds;
	
	private Double additionalPricePerSecond;

	protected ESSimyoSpecialChargeC(Double initialPrice, Double first19seconds, Double additionalPricePerSecond) {
		this.initialPrice = initialPrice;
		this.first19seconds = first19seconds;
		this.additionalPricePerSecond = additionalPricePerSecond;
	}
	
	public Double process(long duration) {
		Double ret = this.initialPrice != null ? this.initialPrice : 0.0;
		if (this.first19seconds != null) {
			ret += (duration < 19 ? duration : 19) * this.first19seconds;
			if (duration > 19 && this.additionalPricePerSecond != null) {
				ret += (duration - 19) * this.additionalPricePerSecond;
			}
		} else {
			ret += duration * this.additionalPricePerSecond;
		}
		
		return ret;
	}


}
