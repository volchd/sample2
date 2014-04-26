package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.utility.Helper;
import com.emc.iig.analytics.utility.ValuesUtility;

/**
 * Transmittal - user selects set of checklist items and creates transmittal with all content included in these checklist items
 * @author volchd
 *
 */
public class Transmittal extends BasicEntity {

	public Transmittal(String name, Map<String, String> context,List<Content> listOfContents) throws CloneNotSupportedException {
		super(name, context);
		setListOfContents(listOfContents);
		getContext().put(ValuesUtility.TRANSMITTAL_NAME, name);
		getContext().put(ValuesUtility.TRANSMITTAL_ID, getId());
		Helper.currentTransmittalName=name;
		Helper.currentTransmittalID=getId();
		Action action=new Action(new Date(), getContext());
		Map<String, String> additionalInformation=new HashMap<String, String>();
		additionalInformation.put(ValuesUtility.NUMBER_OF_ITEMS, listOfContents.size()+"");
		getContext().put(ValuesUtility.NUMBER_OF_ITEMS, listOfContents.size()+"");
		additionalInformation.put(ValuesUtility.BULK_SIZE, getTransmittalSize()+"");
		getContext().put(ValuesUtility.BULK_SIZE, getTransmittalSize()+"");
		//setSize(Helper.getRandomInt(10000000)+10);
		action.setAdditionalInformationValue(additionalInformation.toString());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_TRANSMITTAL));
		for (Content content : listOfContents) {
			getEvents().addAll(content.downloadContent());
			//getChildren().add(content);
		}
		Helper.currentTransmittalName=null;
		Helper.currentTransmittalID=null;
	}

	public List<Content> getListOfContents() {
		return listOfContents;
	}

	public void setListOfContents(List<Content> listOfContents) {
		this.listOfContents = listOfContents;
	}
	public int getTransmittalSize()
	{
		int result=0;
		for (Content c : this.listOfContents) {
			result+=c.getSize();
		}
		return result;
	}

	List<Content> listOfContents=new ArrayList<Content>();
}
