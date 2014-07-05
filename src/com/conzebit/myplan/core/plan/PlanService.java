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
package com.conzebit.myplan.core.plan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.conzebit.myplan.core.Chargeable;
import com.conzebit.myplan.core.call.Call;
import com.conzebit.myplan.core.msisdn.MsisdnType;

import es.simyo.encogetufactura.plan.ESSimyoPagaLoJusto;

public class PlanService {

	private ArrayList<AbstractPlan> plans;
	
	private ArrayList<Chargeable> data;
	
	private HashMap<String, PlanOperator> processedData;
	
	private static PlanService planService = null;
	
	private PlanService() {
		this.plans = new ArrayList<AbstractPlan>();

		this.plans.add(new ESSimyoPagaLoJusto());

		this.process(new ArrayList<Chargeable>());
	}
	
	public static PlanService getInstance() {
		if (planService == null) {
			planService = new PlanService();
		}
		return planService;
	}
	
	public void process(ArrayList<Chargeable> data) {
		this.data = data;
		process();
	}
	
	public PlanSummary process(ArrayList<Chargeable> data, String operator, String planName) {
		for (AbstractPlan plan : this.plans) {
			if (plan.getOperator().equals(operator) &&
			    plan.getPlanName().equals(planName)) {
				return plan.process(data);
			}
		}
		return null;
	}
	
	public void updateMisdnTypeAndProcess(String msisdn, MsisdnType msisdnType) {
		// TODO if Contact is the same instance then remove this loop
		if (msisdn != null) {
			for (Chargeable chargeable : this.data) {
				if (chargeable.getChargeableType() == Chargeable.CHARGEABLE_TYPE_CALL) {
					Call call = (Call) chargeable;
					if (msisdn.equals(call.getContact().getMsisdn())) {
						call.getContact().setMsisdnType(msisdnType);
					}
				}
			}
			process();
		}
	}
	
	private void process() {
		this.processedData = new HashMap<String, PlanOperator>();
		
		for (AbstractPlan plan : this.plans) {
			String operator = plan.getOperator();
			if (!this.processedData.containsKey(operator)) {
				this.processedData.put(operator, new PlanOperator(operator));
			}
			PlanOperator planOperator = this.processedData.get(operator);
		    PlanSummary planSummary = plan.process(this.data);
		    planOperator.addPlanSummary(planSummary);
		}
	}

	public ArrayList<PlanOperator> getOperators() {
		Collection<PlanOperator> ret = this.processedData.values();
		ArrayList<PlanOperator> ret2 = new ArrayList<PlanOperator>(ret);
		Collections.sort(ret2);
		return ret2;
	}

	public String[] getOperatorsAsStringArray() {
		ArrayList<PlanOperator> alpc = this.getOperators();
		String []ret = new String[alpc.size()];
		for (int i = 0; i < alpc.size(); i++) {
			ret[i] = alpc.get(i).getName();
		}
		return ret;
	}
	
	public ArrayList<PlanSummary> getPlanSummaries(String operator, Comparator<PlanSummary> comparator) {
		ArrayList<PlanSummary> ret2 = null;
		if (operator != null) {
			Collection<PlanSummary> ret = this.processedData.get(operator).getPlanSummaryList();
			ret2 = new ArrayList<PlanSummary>(ret);
		} else {
			ret2 = new ArrayList<PlanSummary>();
			for (PlanOperator planOperator : getOperators()) {
				ret2.addAll(planOperator.getPlanSummaryList());
			}
		}
		
		if (comparator == null) {
			Collections.sort(ret2);
		} else {
			Collections.sort(ret2, comparator);
		}
		
		return ret2;
	}

	public PlanConfig getPlanConfig(String operator, String planName) {
		for (AbstractPlan plan : this.plans) {
			if (plan.getOperator().equals(operator) && plan.getPlanName().equals(planName)) {
				return plan.getPlanConfig();
			}
		}
		return null;
	}
	
	public PlanSummary getPlanSummary(String operator, String planName) {
		return this.processedData.get(operator).getPlanSummary(planName);
	}
	
	
	public ArrayList<PlanChargeable> getPlanCalls(String operator, String planName) {
		return this.getPlanSummary(operator, planName).getPlanCalls();
	}
	
	public ArrayList<AbstractPlan> getPlans() {
		return this.plans;
	}
	
	public String[] getPlansAsStringArray() {
		String []ret = new String[this.plans.size()];
		for (int i = 0; i < plans.size(); i++) {
			ret[i] = plans.get(i).getPlanName();
		}
		return ret;
	}

	public String[] getPlansAsStringArray(String operator) {
		ArrayList<String> aux = new ArrayList<String>();
		for (AbstractPlan plan : this.plans) {
			if (operator.equals(plan.getOperator())) {
				aux.add(plan.getPlanName());
			}
		}
		Collections.sort(aux);
		String []ret = new String[aux.size()];
		for (int i = 0; i < aux.size(); i++) {
			ret[i] = aux.get(i);
		}
		return ret;
	}

	public void setUserConfig(HashMap<String, Object> allConfig) {
	    for (AbstractPlan plan : this.plans) {
	    	PlanConfig planConfig = plan.getPlanConfig();
	    	if (planConfig != null) {
	    		planConfig.setUserConfig(allConfig);
	    	}
	    }
	    
    }
}