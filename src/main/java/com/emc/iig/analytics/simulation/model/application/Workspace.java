package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.ValuesUtility;

/**
 * workspace = Capital project
 * @author volchd
 *
 */
		
public class Workspace extends BasicEntity{

	public Workspace(String name,Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
	//	getContext().put(ValuesUtility.WORKSPACE_NAME, name);
	//	getContext().put(ValuesUtility.WORKSPACE_ID, getId());
		//new logic to support only object and parent object info in application context
		getContext().put(ValuesUtility.PARENT_OBJECT_NAME, getContext().get(ValuesUtility.OBJECT_NAME));
		getContext().put(ValuesUtility.PARENT_OBJECT_ID, getContext().get(ValuesUtility.OBJECT_ID));
		getContext().put(ValuesUtility.PARENT_OBJECT_TYPE, getContext().get(ValuesUtility.OBJECT_TYPE));
		getContext().put(ValuesUtility.OBJECT_NAME, name);
		getContext().put(ValuesUtility.OBJECT_ID, getId());
		getContext().put(ValuesUtility.OBJECT_TYPE, ValuesUtility.WORKSPACE);

		Action action=new Action(new Date(), getContext());
		//Action action=new Action(new Date(), getSecondContext());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_WORKSPACE));
	}
	private List<Project> listOfProjects=new ArrayList<Project>();

	public List<Project> getListOfProjects() {
		return listOfProjects;
	}

	public void setListOfProjects(List<Project> listOfProjects) {
		this.listOfProjects = listOfProjects;
	}
	public void addProject(Project p)
	{
		listOfProjects.add(p);
		getChildren().add(p);
	}
	public void removeProject(Project p)
	{
		listOfProjects.remove(p);
	}

	public Project addProject(String name) throws CloneNotSupportedException {
		Project p=new Project(name, getContext());
		addProject(p);
		return p;
	}

}
