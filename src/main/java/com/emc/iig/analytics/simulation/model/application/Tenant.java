package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.ValuesUtility;

public class Tenant extends BasicEntity{

	public Tenant(String name, Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
	//	getContext().put(ValuesUtility.TENANT_NAME, name);
	//	getContext().put(ValuesUtility.TENANT_ID, getId());
		//new logic to support only object and parent object info in application context
		getContext().put(ValuesUtility.PARENT_OBJECT_NAME, getContext().get(ValuesUtility.OBJECT_NAME));
		getContext().put(ValuesUtility.PARENT_OBJECT_ID, getContext().get(ValuesUtility.OBJECT_ID));
		getContext().put(ValuesUtility.PARENT_OBJECT_TYPE, getContext().get(ValuesUtility.OBJECT_TYPE));
		getContext().put(ValuesUtility.OBJECT_NAME, name);
		getContext().put(ValuesUtility.OBJECT_ID, getId());
		getContext().put(ValuesUtility.OBJECT_TYPE, ValuesUtility.TENANT);

		Action action=new Action(new Date(), getContext());
		//Action action=new Action(new Date(), getSecondContext());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_TENANT));
	}
	public List<Workspace> getListOfWorkspaces() {
		return listOfWorkspaces;
	}
	public void setListOfWorkspaces(List<Workspace> listOfWorkspaces) {
		this.listOfWorkspaces = listOfWorkspaces;
	}

	private List<Workspace> listOfWorkspaces=new ArrayList<Workspace>();
	public void addWorkspace(Workspace w)
	{
		listOfWorkspaces.add(w);
		getChildren().add(w);
	}

	public void removeWorkspace( Workspace w)
	{
		listOfWorkspaces.remove(w);
	}
	public Workspace addWorkSpace(String name) throws CloneNotSupportedException{
		Workspace ws=new Workspace(name, getContext());
		addWorkspace(ws);
		return ws;
	}

}
