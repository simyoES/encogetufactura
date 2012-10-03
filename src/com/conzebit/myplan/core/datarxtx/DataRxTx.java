/*
 * This file is part of myPlan.
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
package com.conzebit.myplan.core.datarxtx;

import java.util.Date;

import com.conzebit.myplan.core.Chargeable;

public class DataRxTx extends Chargeable {
	
	private Date from = null;
	private Date to = null;
	private Long rx = 0L;
	private Long tx = 0L;

	@Override
    public int getChargeableType() {
	    return CHARGEABLE_TYPE_DATARXTX;
    }
	
	public void setCountryWhereChargeWasMade(String country) {
		this.countryWhereChargeWasMade = country;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Long getRx() {
		return rx;
	}

	public void setRx(Long rx) {
		this.rx = rx;
	}

	public Long getTx() {
		return tx;
	}

	public void setTx(Long tx) {
		this.tx = tx;
	}
}
