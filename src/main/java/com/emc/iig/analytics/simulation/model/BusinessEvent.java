package com.emc.iig.analytics.simulation.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class BusinessEvent implements Cloneable{

	@Override
	public String toString() {
		return "BusinessEvent [tenantName=" + tenantName + ", tenantID="
				+ tenantID + ", applicationName=" + applicationName
				+ ", applicationID=" + applicationID + ", applicationSpecificContext="+getApplicationSpecificContext()+
				", enagementSpaceName="
				+ enagementSpaceName + ", engagementSpaceID="
				+ engagementSpaceID + ", projectName=" + projectName
				+ ", projectID=" + projectID + ", performerName="
				+ performerName + ", performerID=" + performerID
				+ ", deviceType=" + deviceType + ", location=" + location
				+ ", URL=" + URL + ", eventTime=" + eventTime
				+ ", correlationID=" + correlationID + ", parentCorrelationID="
				+ parentCorrelationID + ", operation=" + operation
				+ ", operationStatus=" + operationStatus
				+ ", additionalInformation=" + additionalInformation
				+ ", additionalInformationValue=" + additionalInformationValue
				+ "]";
	}
	public static String toCVSTitle() {
		return "tenantName" + ",tenantID"
				+ ",applicationName"
				+ ",applicationID" 
				+ ",applicationSpecificContext" 
//				+ ",enagementSpaceName"
//				+  ",engagementSpaceID"
//				+ ",projectName" 
//				+ ",projectID" 
				+",performerName"
				+ ",performerID" 
				+ ",deviceType" + ",location" 
				+ ",URL" + ",eventTime" 
				+ ",correlationID" + ",parentCorrelationID"
				+ ",operation" 
				+ ",operationStatus"
				+ ",additionalInformation" 
				+ ",additionalInformationValue" 
				+ "\n";
	}
	
	public String toCSV() {
		
		return tenantName + ","
				+ tenantID + "," + applicationName
				+ "," + applicationID + ","
				+ getApplicationSpecificContext() + ","
//				+ enagementSpaceName + ","
//				+ engagementSpaceID + "," + projectName
//				+ "," + projectID + ","
				+ performerName + "," + performerID
				+ "," + deviceType + "," + location
				+ "," + URL + "," + eventTime.getTime()
				+ "," + correlationID + ","
				+ parentCorrelationID + "," + operation
				+ "," + operationStatus
				+ "," + additionalInformation
				+ "," + additionalInformationValue
				+ "\n";
	}
	public Map<String, String> toCetas() {
		Map<String, String> result=new HashMap<String,String>();
		result.put("tenantName", tenantName);
		result.put("applicationName", applicationName);
		result.put("applicationID", applicationID);
		result.put("performerName", performerName);
		result.put("performerID", performerID);
		result.put("deviceType", deviceType);
		result.put("location", location);
		result.put("URL", URL);
		result.put("correlationID", correlationID);
		result.put("parentCorrelationID", parentCorrelationID);
		result.put("operation", operation);
		result.put("operationStatus", operationStatus);
		result.put("additionalInformation", additionalInformation);
		result.put("additionalInformationValue", additionalInformationValue);

		return result;
	}
	private String applicationSpecificContext;

	private String tenantName; 
	private String tenantID;
	private String applicationName;
	private String applicationID;
	private String enagementSpaceName;
	private String engagementSpaceID;
	private String projectName;
	private String projectID;
	private String performerName;
	private String performerID;
	private String deviceType;
	private String location;
	private String URL;
	private Date eventTime;
	private String correlationID;
	private String parentCorrelationID;
	private String operation;
	private String operationStatus;
	private String additionalInformation;
	private String additionalInformationValue;
	private String source;
	private long duration;
	private Map<String, String> context;
	//used to simulate only object and parent object information
	private Map<String, String> secondContext;
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantID() {
		return tenantID;
	}
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getApplicationID() {
		return applicationID;
	}
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	public String getEnagementSpaceName() {
		return enagementSpaceName;
	}
	public void setEnagementSpaceName(String enagementSpaceName) {
		this.enagementSpaceName = enagementSpaceName;
	}
	public String getEngagementSpaceID() {
		return engagementSpaceID;
	}
	public void setEngagementSpaceID(String engagementSpaceID) {
		this.engagementSpaceID = engagementSpaceID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getPerformerName() {
		return performerName;
	}
	public void setPerformerName(String performerName) {
		this.performerName = performerName;
	}
	public String getPerformerID() {
		return performerID;
	}
	public void setPerformerID(String performerID) {
		this.performerID = performerID;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public String getEventTimeStrUTC() {
		DateFormat formatter = DateFormat.getDateTimeInstance();
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(this.getEventTime());
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getParentCorrelationID() {
		return parentCorrelationID;
	}
	public void setParentCorrelationID(String parentCorrelationID) {
		this.parentCorrelationID = parentCorrelationID;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public String getAdditionalInformationValue() {
		return additionalInformationValue;
	}
	public void setAdditionalInformationValue(String additionalInformationValue) {
		this.additionalInformationValue = additionalInformationValue;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
	
	return super.clone();
	
	}
	public String getApplicationSpecificContext() {
		return applicationSpecificContext;
	}
	public void setApplicationSpecificContext(String applicationSpecificContext) {
		this.applicationSpecificContext = applicationSpecificContext;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public Map<String, String> getContext() {
		return context;
	}
	public void setContext(Map<String, String> context) {
		this.context = context;
	}
	public Map<String, String> getSecondContext() {
		return secondContext;
	}
	public void setSecondContext(Map<String, String> secondContext) {
		this.secondContext = secondContext;
	}
	
}
