package com.emc.iig.analytics.utility;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import com.emc.iig.analytics.simulation.model.BusinessEvent;
import com.emc.iig.analytics.simulation.model.application.CheckList;
import com.emc.iig.analytics.simulation.model.application.CheckListItem;
import com.emc.iig.analytics.simulation.model.application.Content;
import com.emc.iig.analytics.simulation.model.application.Project;
import com.emc.iig.analytics.simulation.model.application.Submittal;
import com.emc.iig.analytics.simulation.model.application.Transmittal;
import com.emc.iig.analytics.simulation.model.application.User;

public class Helper {
	public static String currentUser="Admin";
	public static String currentUserID="111111111";
	public static int printBuffer=1000;
	public static List<String> toPrint=new ArrayList<String>();

	public static final String DEFAULT_URL =
			"http://webfeeds.cetas.net:8080/rest/agent/";
	public final static String url=
			"http://webfeeds.cetas.net:8080/rest/agent/";
	public final static String key="GcbcoWTXue8IIVGIP7id+MASztBMmtnHN8TaJ9NJ2jPUz4Dt3ftB2uu6g/YWtW1q4UKCIzdgQ6xcHP87tO0nuZo1vRHtcmEHV+VpY13G269bHgxmfvAFyqeUPeKwa1cGBhYNAAk3RrLhQNjdx/TKT1S+IRNlCTrWi2lN8Tsv5AE=";
	//public final static String completeKey="CDADMDZ5R6814972QRl9OyuvfidtTRUN88nA+TRYWU9fQZ59yaiPQM388plC+/bv/q6NSMze4CRFSyXJXrKW5hsIyldRAtlc2195UljBqtS3fQpb1Q1PYFHMdfKxVfVJJWq+MSH1bvQaKlnsUATQZ7zHkDMZEVtbS7hqz7xeNYAnrY6+08OzFF9XqI4OvsH9o+iSI3T4uc29+xUgNg388Zd3ONQHMZxa";
//	private static Config config=null;
//
//	private static CetasAgent agent=null;
//	private static CetasAgent completeAgent=null;
	public static String currentTransmittalName;
	public static String currentTransmittalID;
	public static String currentSubmittalName;
	public static String currentSubmittalID;
	public static Date lastActionDate=null;

	public static Date getLastActionDate() {
		return lastActionDate;
	}


	public static void setLastActionDate(Date lastActionDate) {
		Helper.lastActionDate = lastActionDate;
	}


	public static String generateUniqueID()
	{

		Random generator = new Random();
		return "ID"+generator.nextInt(200000000)+new Date().getTime() +generator.nextInt(200000000)+ "";

	}
//	public static void printApplicationModel(BasicEntity be) throws IOException
//	{
//		printBasicEntity(be, 0);
//	}

	
//	private static void printBasicEntity(BasicEntity basicEntity,int counter) throws IOException
//	{
//		
//		String st1="";
//		for (int i = 0; i <counter; i++) {
//			st1+="\t";
//		}
//		toPrint.add(st1+basicEntity.getClass().getName()+" number of children: "+basicEntity.getChildren().size());
//		toPrint.add(st1+basicEntity.getName() +" "+basicEntity.getId());
//		st1="";
//		//toPrint.add("=========================================================================================================================");
//		for (BusinessEvent be : basicEntity.getEvents()) {
//			for (int i = 0; i <counter+1; i++) {
//				st1+="\t";
//			}
//
//			//toPrint.add(st1+be.toString());
//			st1="";
//			
//		}
//	//	toPrint.add("=========================================================================================================================");
//		printToFile();
//		counter++;
//		for (BasicEntity be : basicEntity.getChildren()) {
//			printBasicEntity(be, counter);
//			
//		}
//	}


	public static int getRandomInt(int i)
	{
		int result=0;
		Random random=new Random();
		
		if (i<=0)
		{
			return 1;
		}
		result=random.nextInt(i);
		if (result==0)
			result=1;
		return result;
		//return 1;
	}
	public static List<BusinessEvent> populateProjectWithContent(Project project) throws CloneNotSupportedException
	{
		List<BusinessEvent> events=new ArrayList<BusinessEvent>();
		List<User> users=project.getListOfUsers();
		List<CheckListItem> checkListItems=new ArrayList<CheckListItem>();
		for (CheckList checkList : project.getListOfCheckLists()) {
			checkListItems.addAll(checkList.getListOfCheckListItems());			
		}
		for (CheckListItem checkListItem : checkListItems) {
			//choose performer
			User user=users.get(getRandomInt(users.size()));
			Helper.currentUser=user.getName();
			Helper.currentUserID=user.getId();
			//choose number of documents to upload
			int numberOfDocumentsToUpload=getRandomInt(ValuesUtility.documents.length);
			for (int i = 0; i < numberOfDocumentsToUpload; i++) {
				Content content=checkListItem.addContent(checkListItem.getId()+ ValuesUtility.documents[i]);
				content.excuteAction();
				events.addAll(content.getEvents());
			}
		}
		return events;
	}
	public static List<Transmittal> createTransmittals(Project p) throws CloneNotSupportedException {
		List<Transmittal> result=new ArrayList<Transmittal>();
		//select users to create transmittals
		for (User user : p.getListOfUsers()) {
			if(getTrueFalse())
			{
				Helper.currentUser=user.getName();
				Helper.currentUserID=user.getId();
				//for each user create a list of checklist items
				List<CheckListItem> listofCheckListItems=getListOfChecklistItems(p);
				List<CheckListItem> transmittalsList=new ArrayList<CheckListItem>();
				for (CheckListItem checkListItem : listofCheckListItems) {
					if(getTrueFalse())
					{
						transmittalsList.add(checkListItem);
					}
					
				}
				List<Content> listOfContents=new ArrayList<Content>();
				for (CheckListItem checkListItem : transmittalsList) {
					for (Content content : checkListItem.getListOfContents()) {
						listOfContents.add(content);
					}
					
				}
				Transmittal transmittal=new Transmittal(Helper.currentUser+Helper.currentUserID, p.getContext(), listOfContents);
				result.add(transmittal);
			}
			
		}
		
		return result;
	}
	public static List<Submittal> createSubmittals(Project p) throws CloneNotSupportedException {
		List<Submittal> result=new ArrayList<Submittal>();
		for (User user : p.getListOfUsers()) {
			if(getTrueFalse())
			{
				Helper.currentUser=user.getName();
				Helper.currentUserID=user.getId();

				
				Submittal submittal=new Submittal(Helper.currentUser+Helper.currentUserID, p.getContext());
				result.add(submittal);
			}
			
		}
		return result;
	}
	public static List<CheckListItem> getListOfChecklistItems(Project p)
	{
		List<CheckListItem> result=new ArrayList<CheckListItem>();
		for (CheckList checkList : p.getListOfCheckLists()) {
			result.addAll(checkList.getListOfCheckListItems());
		}
		return result;
	}
	public static boolean getTrueFalse()
	{
		Random r=new Random();
		int result=r.nextInt(2);
		return (result==1) ? true:false;
	}

//	public static void printToFile() throws IOException {
//		//toPrint.addAll(data);
//		if (toPrint.size()>1) {
//			FileUtils.writeLines(new File("/temp/generatedData.txt"), toPrint, "\n",
//					true);
//			toPrint=new ArrayList<String>();
//		}
//		
//
//	}
//	public static Event createCetasEvent(BusinessEvent be) {
//		Event result=new Event();
//		
//		result.set(be.getEventTime());
//		result.set(be.getContext());
//		return result;
//	}

//	public static Config getCetasConfig()
//	{
//		if (config==null) {
//		 config = new Config(); // Create an empty configuration object
//
//		// Set buffer capacity to 1,250 objects, session timeout to 30 minutes
//		// and the minimum update interval to 2 minutes (time in seconds).
//		config.setCapacity(2000).setTimeout(1800).setUpdateInterval(20);
//
//		// Set user information.
//		config.setUserID("someone").setUserName("Some One").  // User ID, name
//			   setUserDetails(22, Config.GENDER_MALE, 		  // Age, gender...
//			   " A note on this user, up to 100 characters"). // ... and a note
//			   setUserLocation(12345678910L, 11121314151617L);// long/latitude
//
//		System.out.println("\nConnecting to web service at " + url + "...");
//		}
//		return config;
//	}
//	public static void initCetasAgents()
//	{
//		if (agent==null)
//		agent = new CetasAgent(url,Helper.key , config);
//	//	if(completeAgent==null)
//	//	completeAgent=new CetasAgent(url, Helper.completeKey, config);
//	}
	public static void sendToCetas(List<BusinessEvent> events)
	{
		//getCetasConfig();
		//initCetasAgents();
		for (BusinessEvent businessEvent : events) {

			//agent.log(createCetasEvent(businessEvent));
			//toPrint.add(createCetasEvent(businessEvent).toJSON());
			
		}
		//printToFile();
	 //agent.update();
	}
	public static Date getNextActionDate() {
		Date result=new Date(); 
		Calendar calendar=new  GregorianCalendar();
		calendar.set(2011, 1, 1);
		result=calendar.getTime();
		//result.setTime(result.getTime()-ValuesUtility.TIME_BACK);//
		Random random=new Random();
		if (lastActionDate!=null){
			result.setTime(lastActionDate.getTime()+random.nextInt(ValuesUtility.MAX_TIME_FOR_NEXT_ACTION));
		}
		
				
		lastActionDate=result;
		return result;
	}
	
	public static String getEventTimeStrUTC(Date date) {
		DateFormat formatter = DateFormat.getDateTimeInstance();
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(date);
	}

	}

