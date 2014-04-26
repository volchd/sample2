package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.ValuesUtility;

/**
 * chechlist = deliverable list
 * @author volchd
 *
 */
public class CheckList extends BasicEntity {
	public CheckList(String name,Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
	//	getContext().put(ValuesUtility.CHECK_LIST_NAME, name);
	//	getContext().put(ValuesUtility.CHECK_LIST_ID, getId());
		//new logic to support only object and parent object info in application context
		getContext().put(ValuesUtility.PARENT_OBJECT_NAME, getContext().get(ValuesUtility.OBJECT_NAME));
		getContext().put(ValuesUtility.PARENT_OBJECT_ID, getContext().get(ValuesUtility.OBJECT_ID));
		getContext().put(ValuesUtility.PARENT_OBJECT_TYPE, getContext().get(ValuesUtility.OBJECT_TYPE));
		getContext().put(ValuesUtility.OBJECT_NAME, name);
		getContext().put(ValuesUtility.OBJECT_ID, getId());
		getContext().put(ValuesUtility.OBJECT_TYPE, ValuesUtility.CHECKLIST);

		Action action=new Action(new Date(), getContext());
		//Action action=new Action(new Date(), getSecondContext());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_CHECKLIST));
	}
	private List<CheckListItem> listOfCheckListItems=new ArrayList<CheckListItem>();
	public void addCheckListItem(CheckListItem c)
	{
		listOfCheckListItems.add(c);
		getChildren().add(c);
	}
	public CheckListItem addCheckListItem(String name) throws CloneNotSupportedException
	{
		CheckListItem checkListItem=new CheckListItem(name, getContext());
		addCheckListItem(checkListItem);
		return checkListItem;
	}
	public void removeCheckListItem(CheckListItem c)
	{
		listOfCheckListItems.remove(c);
	}
	public List<CheckListItem> getListOfCheckListItems() {
		return listOfCheckListItems;
	}
	public void setListOfCheckListItems(List<CheckListItem> listOfCheckListItems) {
		this.listOfCheckListItems = listOfCheckListItems;
	}
	

}
