package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.ValuesUtility;

/**
 * project = Contract
 * @author volchd
 *
 */
public class Project extends BasicEntity {
	

	public Project(String name, Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
	//	getContext().put(ValuesUtility.PROJECT_NAME, name);
	//	getContext().put(ValuesUtility.PROJECT_ID, getId());
		//new logic to support only object and parent object info in application context
		getContext().put(ValuesUtility.PARENT_OBJECT_NAME, getContext().get(ValuesUtility.OBJECT_NAME));
		getContext().put(ValuesUtility.PARENT_OBJECT_ID, getContext().get(ValuesUtility.OBJECT_ID));
		getContext().put(ValuesUtility.PARENT_OBJECT_TYPE, getContext().get(ValuesUtility.OBJECT_TYPE));
		getContext().put(ValuesUtility.OBJECT_NAME, name);
		getContext().put(ValuesUtility.OBJECT_ID, getId());
		getContext().put(ValuesUtility.OBJECT_TYPE, ValuesUtility.PROJECT);

		Action action=new Action(new Date(), getContext());
		//Action action=new Action(new Date(), getSecondContext());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_PROJECT));
	}
	private List<CheckList> listOfCheckLists=new ArrayList<CheckList>();
	private List<User> listOfUsers=new ArrayList<User>();

	public List<CheckList> getListOfCheckLists() {
		return listOfCheckLists;
	}

	public void setListOfCheckLists(List<CheckList> listOfCheckLists) {
		this.listOfCheckLists = listOfCheckLists;
	}
	public void addCheckList(CheckList c)
	{
		listOfCheckLists.add(c);
		
		getChildren().add(c);
	}
	public void removeCheckList(CheckList c)
	{
		listOfCheckLists.remove(c);
	}

	public List<User> getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(List<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}
	public void addUser(User c)
	{
		listOfUsers.add(c);
		getChildren().add(c);
	}
	public User addUser(String name) throws CloneNotSupportedException
	{
		User user = new User(name, getContext());
		addUser(user);
		return user;
	}
	public void removeUser(User c)
	{
		listOfUsers.remove(c);
	}

	public CheckList addCheckList(String name) throws CloneNotSupportedException {
		CheckList checkList=new CheckList(getId()+name, getContext());
		addCheckList(checkList);
		return checkList;
	}
}
