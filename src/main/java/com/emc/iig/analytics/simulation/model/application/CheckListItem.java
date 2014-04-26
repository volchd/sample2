package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.ValuesUtility;

/**
 * checklist item = deliverable
 * @author volchd
 *
 */
public class CheckListItem extends BasicEntity {
	public CheckListItem(String name,Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
	//	getContext().put(ValuesUtility.CHECK_LIST_ITEM_NAME, name);
	//	getContext().put(ValuesUtility.CHECK_LIST_ITEM_ID, getId());
		//new logic to support only object and parent object info in application context
		getContext().put(ValuesUtility.PARENT_OBJECT_NAME, getContext().get(ValuesUtility.OBJECT_NAME));
		getContext().put(ValuesUtility.PARENT_OBJECT_ID, getContext().get(ValuesUtility.OBJECT_ID));
		getContext().put(ValuesUtility.PARENT_OBJECT_TYPE, getContext().get(ValuesUtility.OBJECT_TYPE));
		getContext().put(ValuesUtility.OBJECT_NAME, name);
		getContext().put(ValuesUtility.OBJECT_ID, getId());
		getContext().put(ValuesUtility.OBJECT_TYPE, ValuesUtility.CHECKLIST_ITEM);

		Action action=new Action(new Date(), getContext());
		//Action action=new Action(new Date(), getSecondContext());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_CHECKLIST_ITEM));
	}
	private List<Content> listOfContents= new ArrayList<Content>();

	public List<Content> getListOfContents() {
		return listOfContents;
	}

	public void setListOfContents(List<Content> listOfContents) {
		this.listOfContents = listOfContents;
	}
	public void addContent(Content c)
	{
		listOfContents.add(c);
		getChildren().add(c);
	}
	public Content addContent(String name) throws CloneNotSupportedException
	{
		Content content=new Content(name, getContext());
		addContent(content);
		return content;
	}
	public void removeContent(Content c)
	{
		listOfContents.remove(c);
	}

}
