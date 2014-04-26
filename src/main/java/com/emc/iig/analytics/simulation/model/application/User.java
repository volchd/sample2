package com.emc.iig.analytics.simulation.model.application;

import java.util.Date;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.ValuesUtility;

public class User extends BasicEntity {

	public User(String name,Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
		getContext().put(ValuesUtility.USER_NAME, name);
		getContext().put(ValuesUtility.USER_ID, getId());
		Action action=new Action(new Date(), getContext());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_USER));
	}


}
