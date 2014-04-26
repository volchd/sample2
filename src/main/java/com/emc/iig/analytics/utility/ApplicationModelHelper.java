package com.emc.iig.analytics.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emc.iig.analytics.messaging.EventProducerHandler;
import com.emc.iig.analytics.simulation.model.BusinessEvent;
import com.emc.iig.analytics.simulation.model.application.Application;
import com.emc.iig.analytics.simulation.model.application.BasicEntity;
import com.emc.iig.analytics.simulation.model.application.CheckList;
import com.emc.iig.analytics.simulation.model.application.CheckListItem;
import com.emc.iig.analytics.simulation.model.application.Project;
import com.emc.iig.analytics.simulation.model.application.Submittal;
import com.emc.iig.analytics.simulation.model.application.Tenant;
import com.emc.iig.analytics.simulation.model.application.Transmittal;
import com.emc.iig.analytics.simulation.model.application.User;
import com.emc.iig.analytics.simulation.model.application.Workspace;
@Service
public class ApplicationModelHelper {
	@Autowired
	private EventProducerHandler applicationEventHandler;
	public void createApplicationModel() throws CloneNotSupportedException, JsonGenerationException, JsonMappingException, IOException, InterruptedException
	{
		List<BusinessEvent> events=new ArrayList<BusinessEvent>();
		   
    	List<Transmittal> listofTransmittals=new ArrayList<Transmittal>();
    	List<Submittal> listofSubmittals=new ArrayList<Submittal>();
    	List<BasicEntity> listOfEntities=new ArrayList<BasicEntity>();
    	//create Admin user

    	//create application
    	Application application=new Application("Beacon", new HashMap<String, String>());
    	events=(application.getEvents());
    	applicationEventHandler.handleApplicationMessages(events);
    //	Helper.sendToCetas(events);
    	//listOfEntities.add(application);
    	//create a new Tenants
    	int numberOfTenants=/*Helper.getRandomInt*/(ValuesUtility.tenantNames.length);
    	for (int i = 0; i < 40; i++) {
    		Helper.setLastActionDate(null);
    		Tenant tenant= application.addTenant(/*ValuesUtility.tenantNames[i]*/"Tenant "+i);
    		events=(tenant.getEvents());
    		applicationEventHandler.handleApplicationMessages(events);
    		//Helper.sendToCetas(events);
    		//create new Capital Project/ Workspace for each tenant
    		int numberOfWorkspaces=ValuesUtility.engagementSpaceNames.length;
    		for (int j = 0; j < numberOfWorkspaces; j++) {
				Workspace ws=tenant.addWorkSpace(tenant.getId()+ ValuesUtility.engagementSpaceNames[j]);
				events=(ws.getEvents());
				applicationEventHandler.handleApplicationMessages(events);
				//create new Contract/Project
				int numberOfProjects=Helper.getRandomInt(ValuesUtility.projectNames.length);
				for (int k = 0; k <= numberOfProjects; k++) {
					Project p=ws.addProject(ws.getId()+ValuesUtility.projectNames[k]);
					events=(p.getEvents());
					applicationEventHandler.handleApplicationMessages(events);
					//assign users to project
					int numberOfUsers=Helper.getRandomInt(ValuesUtility.performers.length);
					if (numberOfUsers<1) {
						numberOfUsers=1;
						
					}
					for (int l = 0; l <= numberOfUsers; l++) {
						User user = p.addUser(p.getId()+ValuesUtility.performers[l]);
						events=(user.getEvents());
						applicationEventHandler.handleApplicationMessages(events);
					}
					//create new deliverables list/ checklist
					int numberOfChecklists=Helper.getRandomInt(ValuesUtility.checklists.length);
					for (int l = 0; l <= numberOfChecklists; l++) {
						CheckList checkList=p.addCheckList(p.getId()+ ValuesUtility.checklists[l]);
						events=(checkList.getEvents());
						applicationEventHandler.handleApplicationMessages(events);
						//create new Deliverables/ checklist items
						int numberOfChecklistItems=Helper.getRandomInt(ValuesUtility.checklistItems.length);
						for (int m = 0; m <= numberOfChecklistItems; m++) {
							CheckListItem checkListItem=checkList.addCheckListItem(checkList.getId()+ ValuesUtility.checklistItems[m]);
							events=(checkListItem.getEvents());
							applicationEventHandler.handleApplicationMessages(events);
						}
					}
					//populate Project with content
					events=(Helper.populateProjectWithContent(p));
					applicationEventHandler.handleApplicationMessages(events);
				
					/*//create Transmittals
					listofTransmittals.addAll(Helper.createTransmittals(p));
					//create Submittals
					listofSubmittals.addAll(Helper.createSubmittals(p));*/
				}
			}
    		
		}
	}
	
	public void generateSystemEvents() throws JsonGenerationException, JsonMappingException, IOException, InterruptedException
	{
		JSONUtility jsonUtility=new JSONUtility();
		Date toDate=Helper.getNextActionDate();
		Helper.setLastActionDate(null);
		Date date=Helper.getNextActionDate();
		for (int k = 0; k < 600000; k++) {

		
		 for (int i=0;i<ValuesUtility.hosts.length;i++) {
			 for(int j=0;j<ValuesUtility.ports.length;j++)
			 {
				 Map<String, String> map=createSystemEvent(date, ValuesUtility.hosts[i],ValuesUtility.ports[j]);
				 String message=jsonUtility.getJSON(map);
				 applicationEventHandler.handleSystemMessage(message);
			 }
			
		}
		 Thread.sleep(10);
		 date.setTime(date.getTime()+ValuesUtility.TIME_FOR_NEXT_SYSTEM_EVENT);
		}
	}
	
	private Map<String, String> createSystemEvent(Date date, String host, String port)
	{
		Random random=new Random();
		Map<String, String> result=new HashMap<String, String>();
		result.put(ValuesUtility.EVENT_TIME, Helper.getEventTimeStrUTC(date));
		result.put(ValuesUtility.EVENT_TIME_MIL, date.getTime()+"");
		result.put(ValuesUtility.EVENT_HOST, host);
		result.put(ValuesUtility.APPLICATION_NAME, "NGIS");
		result.put(ValuesUtility.EVENT_PORT, port);
		result.put(ValuesUtility.EVENT_CPU,random.nextInt(100)+"");
		result.put(ValuesUtility.EVENT_MEMORY, random.nextInt(100)+"");
		
		return result;
	}
}
