package com.emc.iig.analytics.simulation.flows;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.iig.analytics.simulation.model.BusinessEvent;
import com.emc.iig.analytics.utility.Helper;
import com.emc.iig.analytics.utility.ValuesUtility;

public  class Action implements UserAction{


	private static final Logger logger = LoggerFactory.getLogger(Action.class);
	public List<BusinessEvent> generateFlow(String flowName) throws CloneNotSupportedException {
		List<BusinessEvent> result = new ArrayList<BusinessEvent>();

		result.addAll(generateNGISevents(flowName,null));
		return result;
	}

	public BusinessEvent getBe() {
		return be;
	}

	public void setBe(BusinessEvent be) {
		this.be = be;
	}

	private Date currentEventTime=null;
	private BusinessEvent be=new BusinessEvent();
	private String additionalInformationValue="";
	Random generator = new Random();
@Deprecated
	public Action(Date actionStartTime, String tenantName, String tenantID,String applicationName, String applicationID, String checklist, String checklistID, String checkilistItem, String checklistItemID, String enagementSpaceName, String engagementSpaceID,String projectName,String projectID,  String performerName,String performerID) {
		super();
		this.currentEventTime = actionStartTime;
		// create action context
		be.setTenantName(tenantName);
		be.setTenantID(tenantID);
		be.setApplicationName(applicationName);
		be.setApplicationID(applicationID);
		String applicationSpecificContext="enagementSpaceName="+enagementSpaceName+";\nengagementSpaceID="+engagementSpaceID+";\nprojectName="+projectName+";\nprojectID="+projectID;
		be.setApplicationSpecificContext(applicationSpecificContext);
	
		
		be.setPerformerName(performerName);
		be.setPerformerID(performerID);
		be.setEventTime(actionStartTime);
		
	}
	public Action(Date actionStartTime,Map<String, String> context) {
		super();
		this.currentEventTime = actionStartTime;
//		// create action context
//		be.setTenantName(context.get(ValuesUtility.TENANT_NAME));
//		be.setTenantID(context.get(ValuesUtility.TENANT_ID));
//		be.setApplicationName(context.get(ValuesUtility.APPLICATION_NAME));
//		be.setApplicationID(context.get(ValuesUtility.APPLICATION_ID));
//	//	String applicationSpecificContext="enagementSpaceName="+context.get(ValuesUtility.WORKSPACE_NAME)+";engagementSpaceID="+context.get(ValuesUtility.WORKSPACE_ID)+";projectName="+context.get(ValuesUtility.PROJECT_NAME)+";projectID="+context.get(ValuesUtility.PROJECT_ID);
//		
//	//	be.setApplicationSpecificContext(applicationSpecificContext);
//		be.setEnagementSpaceName(context.get(ValuesUtility.WORKSPACE_NAME));
//		be.setEngagementSpaceID(context.get(ValuesUtility.WORKSPACE_ID));
//		be.setProjectName(context.get(ValuesUtility.PROJECT_NAME));
//		be.setProjectID(context.get(ValuesUtility.PROJECT_ID));
//		be.setPerformerName(context.get(ValuesUtility.USER_NAME));
//		be.setPerformerID(context.get(ValuesUtility.USER_ID));
		be.setEventTime(Helper.getNextActionDate());
		context.put(ValuesUtility.EVENT_TIME,be.getEventTimeStrUTC());
		this.currentEventTime = be.getEventTime();
		be.setContext(context);
		
	}
	public Action(BusinessEvent be)
	{
		this.be=be;
	}
	private List<BusinessEvent> generateUIevents(String flowName)
			throws CloneNotSupportedException {
		List<BusinessEvent> result=new ArrayList<BusinessEvent>();
		
		BusinessEvent be = getBe();
		// initiate workspace creation
		// create UI event
		be.setSource(ValuesUtility.sources[generator.nextInt(3)]);
		be.getContext().put(ValuesUtility.EVENT_SOURCE, ValuesUtility.sources[generator.nextInt(3)]);
		be.setDeviceType(ValuesUtility.deviceTypes[generator.nextInt(3)]);
		be.getContext().put(ValuesUtility.EVENT_DEVICE, ValuesUtility.deviceTypes[generator.nextInt(3)]);
		be.setOperation(flowName);
		be.getContext().put(ValuesUtility.EVENT_OPERATION_NAME, flowName);
		be.setOperationStatus(ValuesUtility.operationStatuses[0]);
		be.getContext().put(ValuesUtility.EVENT_OPERATION_STATUS, ValuesUtility.operationStatuses[0]);
	//	be.setAdditionalInformation(ValuesUtility.CONTENT_SIZE);
	//	be.setAdditionalInformationValue(getAdditionalInformationValue());
		// generate correlation ID
		String UIcorrelationID = generateCorrelationID();
		be.setCorrelationID(UIcorrelationID);
		be.getContext().put(ValuesUtility.EVENT_CORRELATION_ID, UIcorrelationID);
		logger.debug(ValuesUtility.EVENT_CORRELATION_ID+"="+UIcorrelationID+", log message 1");
		result.add(be);
		//create complete UI event
		BusinessEvent be1 = (BusinessEvent) be.clone();
		//Map<String, String> nc=be.getContext();
		be1.setContext(new HashMap<String, String>(be.getContext()));
		be1.setOperationStatus(ValuesUtility.operationStatuses[1]);
		be1.getContext().put(ValuesUtility.EVENT_OPERATION_STATUS, ValuesUtility.operationStatuses[1]);
		//set complete operation time
		be1.setEventTime(getNextEventTime(true));
		be1.getContext().put(ValuesUtility.EVENT_TIME, be1.getEventTimeStrUTC());
		be1.setDuration(be1.getEventTime().getTime()-be.getEventTime().getTime());
		be1.getContext().put(ValuesUtility.ACTION_DURATION, be1.getDuration()+"");
		logger.debug(ValuesUtility.EVENT_CORRELATION_ID+"="+UIcorrelationID+", log message 2");
		result.add(be1);
//		if (be1.getOperationStatus().equals(ValuesUtility.operationStatuses[1]))
//		{
		//	result.addAll(generateXCPevents(flowName,be1));
//		}
		return result;
	}
	private List<BusinessEvent> generateXCPevents(String flowName,BusinessEvent invokingEvent)
			throws CloneNotSupportedException {
		List<BusinessEvent> result = new ArrayList<BusinessEvent>();
		Random generator = new Random();
		BusinessEvent be = (BusinessEvent) invokingEvent.clone();
		be.setParentCorrelationID(invokingEvent.getCorrelationID());
		be.getContext().put(ValuesUtility.EVENT_PARENT_CORRELATION_ID, invokingEvent.getCorrelationID());
		String correlationID = generateCorrelationID();
		be.setCorrelationID(correlationID);
		be.getContext().put(ValuesUtility.EVENT_CORRELATION_ID, correlationID);
		be.setLocation(ValuesUtility.hosts[generator.nextInt(3)]+":"+ValuesUtility.ports[generator.nextInt(7)]);
		be.getContext().put(ValuesUtility.EVENT_LOCATION, ValuesUtility.hosts[generator.nextInt(3)]+":"+ValuesUtility.ports[generator.nextInt(7)]);
		be.setEventTime(getNextEventTime(false));
		be.getContext().put(ValuesUtility.EVENT_TIME, be.getEventTimeStrUTC());
		be.setOperation(flowName+ValuesUtility.XCP_SERVICE);
		be.getContext().put(ValuesUtility.EVENT_OPERATION_NAME, be.getOperation());
		be.setOperationStatus(ValuesUtility.operationStatuses[0]);
		be.getContext().put(ValuesUtility.EVENT_OPERATION_STATUS, be.getOperationStatus());
		result.add(be);
		// create complete UI event
		BusinessEvent be1 = (BusinessEvent) be.clone();
		be1.setContext(new HashMap<String, String>(be.getContext()));
		be1.setOperationStatus(ValuesUtility.operationStatuses[1]);
		be.getContext().put(ValuesUtility.EVENT_OPERATION_STATUS, be1.getOperationStatus());
		// set complete operation time
		be1.setEventTime(getNextEventTime(false));
		be1.getContext().put(ValuesUtility.EVENT_TIME, be1.getEventTimeStrUTC());
		be1.setDuration(be1.getEventTime().getTime()-be.getEventTime().getTime());
		be1.getContext().put(ValuesUtility.ACTION_DURATION, be1.getDuration()+"");
		result.add(be1);
		result.addAll(generateNGISevents(flowName,be1));
		return result;
	}
	private List<BusinessEvent> generateNGISevents(String flowName,BusinessEvent invokingEvent)
			throws CloneNotSupportedException {
		if (null==invokingEvent)
			invokingEvent=getBe();
		List<BusinessEvent> result = new ArrayList<BusinessEvent>();
		Random generator = new Random();
		BusinessEvent be = (BusinessEvent) invokingEvent.clone();
		be.setParentCorrelationID(invokingEvent.getCorrelationID());
		be.getContext().put(ValuesUtility.EVENT_PARENT_CORRELATION_ID,be.getParentCorrelationID());
		String correlationID = generateCorrelationID();
		be.setCorrelationID(correlationID);
		be.getContext().put(ValuesUtility.EVENT_CORRELATION_ID, be.getCorrelationID());
		be.getContext().put(ValuesUtility.EVENT_TYPE, ValuesUtility.eventTypes[0]);
		//be.setLocation(ValuesUtility.hosts[generator.nextInt(3)]+":"+ValuesUtility.ports[generator.nextInt(7)]);
		be.getContext().put(ValuesUtility.EVENT_HOST,ValuesUtility.hosts[generator.nextInt(3)]);
		be.getContext().put(ValuesUtility.EVENT_PORT,ValuesUtility.ports[generator.nextInt(7)]);
	
		be.setEventTime(getNextEventTime(false));
		be.getContext().put(ValuesUtility.EVENT_TIME,Helper.getEventTimeStrUTC(be.getEventTime()));
		be.setOperation(flowName+ValuesUtility.NGIS_SERVICE);
		be.getContext().put(ValuesUtility.EVENT_OPERATION_NAME, flowName+ValuesUtility.NGIS_SERVICE);
		be.getContext().put(ValuesUtility.EVENT_OPERATION_TYPE, ValuesUtility.operationTypes[0]);
		be.setOperationStatus(ValuesUtility.operationStatuses[0]);
		be.getContext().put(ValuesUtility.EVENT_OPERATION_STATUS,be.getOperationStatus());
		result.add(be);
		if (ValuesUtility.operationStatuses[1]==generateServiceStatus())
		{
			// create complete NGIS event
			BusinessEvent be1 = (BusinessEvent) be.clone();
			be1.setContext(new HashMap<String, String>(be.getContext()));
			be1.setOperationStatus(ValuesUtility.operationStatuses[1]);
			be1.getContext().put(ValuesUtility.EVENT_OPERATION_STATUS, be1.getOperationStatus());
			// set complete operation time
			be1.setEventTime(getNextEventTime(false));
			be1.getContext().put(ValuesUtility.EVENT_TIME, Helper.getEventTimeStrUTC(be1.getEventTime()));
			be1.setDuration(be1.getEventTime().getTime()-be.getEventTime().getTime());
			be1.getContext().put(ValuesUtility.ACTION_DURATION, be1.getDuration()+"");
			result.add(be1);
			logger.debug(ValuesUtility.EVENT_CORRELATION_ID+"="+correlationID+", log message 1");
			logger.debug(ValuesUtility.EVENT_CORRELATION_ID+"="+correlationID+", log message 2");
			logger.debug(ValuesUtility.EVENT_CORRELATION_ID+"="+correlationID+", log message 3");
		}
		else {
			logger.error(ValuesUtility.EVENT_CORRELATION_ID+"="+correlationID+", Error Exception");
		}
		

		return result;
	}
	private Date getNextEventTime(boolean isUIEvent)
	{
		if (isUIEvent==true)
		this.currentEventTime=new Date(this.currentEventTime.getTime()+ (generator.nextInt(5)+1) *60*1000);
		else 
			this.currentEventTime=new Date(this.currentEventTime.getTime()+ (generator.nextInt(2000) + 100));
		return this.currentEventTime;
		
	}
	private String generateCorrelationID()
	{
		return Helper.generateUniqueID();
	}

	public String getAdditionalInformationValue() {
		return additionalInformationValue;
	}

	public void setAdditionalInformationValue(String additionalInformationValue) {
		this.additionalInformationValue = additionalInformationValue;
	}
	
	private String generateServiceStatus()
	{
		String result=ValuesUtility.operationStatuses[1];
		if (9==generator.nextInt(10))
			result=ValuesUtility.operationStatuses[3];
		return result;
	}
}
