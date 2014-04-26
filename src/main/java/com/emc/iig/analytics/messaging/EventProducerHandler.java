package com.emc.iig.analytics.messaging;

import java.io.IOException;
import java.util.List;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emc.iig.analytics.config.Properties;
import com.emc.iig.analytics.simulation.model.BusinessEvent;
import com.emc.iig.analytics.utility.JSONUtility;
import com.emc.iig.analytics.utility.ValuesUtility;
@Service
public class EventProducerHandler {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(EventProducerHandler.class);
	private int instanceIndex = -1;
	public EventProducerHandler() {
		CloudEnvironment environment = new CloudEnvironment();
		if (environment.getInstanceInfo() != null) {
			instanceIndex = environment.getInstanceInfo().getInstanceIndex();
		}
		
	}
	

	public void handleApplicationMessages(List<BusinessEvent> events) throws JsonGenerationException, JsonMappingException, IOException, InterruptedException
	{
		JSONUtility utility=new  JSONUtility();
	for (BusinessEvent businessEvent : events) {
		businessEvent.getContext().put(ValuesUtility.EVENT_TIME_MIL, businessEvent.getEventTime().getTime()+"");
		handleApplciationMessage(utility.getJSON(businessEvent.getContext()));
	}
	events=null;
	Thread.sleep(10);
	}

	public void handleApplciationMessage(String message) {

		rabbitTemplate.convertAndSend(Properties.APPLICATION_EVENTS, message);
	}
	
	public void handleSystemMessage(String message) {

		rabbitTemplate.convertAndSend(Properties.SYSTEM_EVENTS, message);
	}
	
	

}
