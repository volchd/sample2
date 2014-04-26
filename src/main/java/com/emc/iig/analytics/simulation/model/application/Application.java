package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.ValuesUtility;

public class Application extends BasicEntity{

	public Application(String name,Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
		
//		getContext().put(ValuesUtility.APPLICATION_NAME, name);
//		getContext().put(ValuesUtility.APPLICATION_ID, getId());
		
		//new logic to support only object and parent object info in application context
		getContext().put(ValuesUtility.OBJECT_NAME, name);
		getContext().put(ValuesUtility.OBJECT_ID, getId());
		getContext().put(ValuesUtility.OBJECT_TYPE, ValuesUtility.APPLICATION);
		//original context
		Action action=new Action(new Date(), getContext());
		//Action action=new Action(new Date(), getSecondContext());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_APPLICATION));
		
	}
	List<Tenant> listOfTenants=new ArrayList<Tenant>();
	public List<Tenant> getListOfTenants() {
		return listOfTenants;
	}
	public void setListOfTenants(List<Tenant> listOfTenants) {
		this.listOfTenants = listOfTenants;
	}
	public void addTenant(Tenant t)
	{
		listOfTenants.add(t);
		getChildren().add(t);
	}
	public Tenant addTenant(String name) throws CloneNotSupportedException{
		Tenant tenant=new Tenant(name, getContext());
		addTenant(tenant);
		return tenant;
	}
	public void removeTenant(Tenant t)
	{
		listOfTenants.remove(t);
	}

}
