package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.Helper;
import com.emc.iig.analytics.utility.ValuesUtility;

public class Submittal extends BasicEntity {

	public Submittal(String name, Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
		getContext().put(ValuesUtility.SUBMITTAL_NAME, name);
		getContext().put(ValuesUtility.SUBMITTAL_ID, getId());
		Helper.currentSubmittalName=name;
		Helper.currentSubmittalID=getId();
		int numberOfitems=Helper.getRandomInt(ValuesUtility.MAX_NUMBER_OF_ITEMS_SUBMITTALS);

		for (int i = 0; i < numberOfitems; i++) {
			addContent(getId()+ValuesUtility.SUBMITTAL_ITEM+i);
		}
		getContext().put(ValuesUtility.BULK_SIZE, getSubmittalSize()+"");
		getContext().put(ValuesUtility.NUMBER_OF_ITEMS, numberOfitems+"");
		Action action=new Action(new Date(), getContext());
		//setSize(Helper.getRandomInt(10000000)+10);
		action.setAdditionalInformationValue(getSubmittalSize()+"");
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_SUBMITTAL));
		for (Content content : listOfContents) {
			//getEvents().addAll(content.downloadContent());
			content.excuteAction();
			getEvents().addAll(content.getEvents());
			//getChildren().add(content);
		}
		Helper.currentSubmittalName=null;
		Helper.currentSubmittalID=null;
		
	}
	List<Content> listOfContents=new ArrayList<Content>();
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
	public void addContent(Content c)
	{
		listOfContents.add(c);
		getChildren().add(c);
	}
	public int getSubmittalSize()
	{
		int result=0;
		for (Content c : this.listOfContents) {
			result+=c.getSize();
		}
		return result;
	}
}
