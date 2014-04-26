package com.emc.iig.analytics.simulation.model.application;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.flows.Action;
import com.emc.iig.analytics.simulation.model.BusinessEvent;
import com.emc.iig.analytics.utility.Helper;
import com.emc.iig.analytics.utility.ValuesUtility;

public class Content extends BasicEntity {
	public Content(String name,Map<String, String> context) throws CloneNotSupportedException {
		super(name, context);
	//	getContext().put(ValuesUtility.CONTENT_NAME, name);
	//	getContext().put(ValuesUtility.CONTENT_ID, getId());
		setSize(Helper.getRandomInt(10000000)+10);
		getContext().put(ValuesUtility.CONTENT_SIZE, getSize()+"");
		//new logic to support only object and parent object info in application context
		getContext().put(ValuesUtility.PARENT_OBJECT_NAME, getContext().get(ValuesUtility.OBJECT_NAME));
		getContext().put(ValuesUtility.PARENT_OBJECT_ID, getContext().get(ValuesUtility.OBJECT_ID));
		getContext().put(ValuesUtility.PARENT_OBJECT_TYPE, getContext().get(ValuesUtility.OBJECT_TYPE));
		getContext().put(ValuesUtility.OBJECT_NAME, name);
		getContext().put(ValuesUtility.OBJECT_ID, getId());
		getContext().put(ValuesUtility.OBJECT_TYPE, ValuesUtility.CONTENT);

		getContext().put(ValuesUtility.CONTENT_SIZE, getSize()+"");
		//Action action=new Action(new Date(), getContext());
		//Action action=new Action(new Date(), getSecondContext());
	}
	public void excuteAction() throws CloneNotSupportedException
	{
		Map<String, String> context=getContext();
		if (Helper.currentSubmittalName!=null) {
			context.put(ValuesUtility.SUBMITTAL_NAME, Helper.currentSubmittalName);
			context.put(ValuesUtility.SUBMITTAL_ID, Helper.currentSubmittalID);
		}
		
		Action action=new Action(new Date(), context);
		//Action action=new Action(new Date(), getSecondContext());
		Map<String, String> additionalInformation=new HashMap<String, String>();
		additionalInformation.put(ValuesUtility.CONTENT_SIZE,getSize()+"");
		action.setAdditionalInformationValue(additionalInformation.toString());
		getEvents().addAll(action.generateFlow(ValuesUtility.CREATE_CONTENT));
	}

	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public List<BusinessEvent> downloadContent() throws CloneNotSupportedException
	{
		Map<String,String> context=getContext();
		context.put(ValuesUtility.USER_NAME, Helper.currentUser);
		context.put(ValuesUtility.USER_ID,Helper.currentUserID);
		context.put(ValuesUtility.TRANSMITTAL_NAME, Helper.currentTransmittalName);
		context.put(ValuesUtility.TRANSMITTAL_ID, Helper.currentTransmittalID);
		Action action=new Action(new Date(),context);
		action.setAdditionalInformationValue(getSize()+"");
		return action.generateFlow(ValuesUtility.GET_CONTENT);
	}


}
