package com.emc.iig.analytics.simulation.model.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emc.iig.analytics.simulation.model.BusinessEvent;
import com.emc.iig.analytics.utility.Helper;
import com.emc.iig.analytics.utility.ValuesUtility;

public class BasicEntity {
	private String name;
	private String id;
	private Map<String, String> context = new HashMap<String, String>();

	private List<BasicEntity> children=new ArrayList<BasicEntity>();
	private List<BusinessEvent> events=new ArrayList<BusinessEvent>();

	public List<BusinessEvent> getEvents() {
		return events;
	}

	public void setEvents(List<BusinessEvent> events) {
		this.events = events;
	}

	public BasicEntity(String name, Map<String, String> context) {
		super();
		this.name = name;
		setId(Helper.generateUniqueID());
		this.context.putAll(context);
		this.context.put(ValuesUtility.USER_NAME, Helper.currentUser);
		this.context.put(ValuesUtility.USER_ID, Helper.currentUserID);
		
	}

	public String getName() {
		return name;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addToContext(String key, String value) {
		context.put(key, value);
	}

	public void removeFromContext(String key) {
		context.remove(key);

	}

	public List<BasicEntity> getChildren() {
		return children;
	}

	public void setChildren(List<BasicEntity> children) {
		this.children = children;
	}


}
