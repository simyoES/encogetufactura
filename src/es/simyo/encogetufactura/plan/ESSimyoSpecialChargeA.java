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

public class ESSimyoSpecialChargeA implements SpecialChargeProcessor {
	
	private Double initialPrice;
	
	private Double first60seconds;
	
	private Double first180seconds;
	
	private Double additionalPricePerSecond;

	protected ESSimyoSpecialChargeA(Double initialPrice, Double first60seconds, Double first180seconds, Double additionalPricePerSecond) {
		this.initialPrice = initialPrice;
		this.first60seconds = first60seconds;
		this.first180seconds = first180seconds;
		this.additionalPricePerSecond = additionalPricePerSecond;
	}
	
	public Double process(long duration) {
		Double ret = this.initialPrice != null ? this.initialPrice : 0.0;
		if (this.first60seconds != null) {
			ret += (duration < 60 ? duration : 60) * this.first60seconds;
			if (duration > 60 && this.additionalPricePerSecond != null) {
				ret += (duration - 60) * this.additionalPricePerSecond;
			}
		} else if (this.first180seconds != null) {
			ret += (duration < 180 ? duration : 180) * this.first180seconds;
			if (duration > 180 && this.additionalPricePerSecond != null) {
				ret += (duration - 180) * this.additionalPricePerSecond; 
			}
		} else {
			if (this.additionalPricePerSecond != null) {
				ret += duration * this.additionalPricePerSecond;
			}
		}
		
		return ret;
	}


}
