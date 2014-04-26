package com.emc.iig.analytics;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emc.iig.analytics.config.Properties;
import com.emc.iig.analytics.utility.ApplicationModelHelper;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	//@Autowired
	//private RedisHandler redisHandler;
	
	@Autowired
	private Queue applicationEventsQueue;
	@Autowired
	private ApplicationModelHelper applicationModelHelper;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/sendMessage", method=RequestMethod.POST)
	public String publish(Model model) throws CloneNotSupportedException, JsonGenerationException, JsonMappingException, IOException, InterruptedException {
	//	logger.warn("========sending messages===============");
	 //   applicationModelHelper.createApplicationModel();
		applicationModelHelper.generateSystemEvents();
	//	logger.warn("========sending messages complete===============");
		return home(model);
	}
	
	@RequestMapping(value = "/applicationEventsQueue", method = RequestMethod.GET) 
	@ResponseBody
	public String queue() {
	//	logger.warn("========counting===============");
		return "Message count for " + Properties.APPLICATION_EVENTS + " = " + this.declareQueuePassive(this.applicationEventsQueue).getMessageCount();	
	}
	public DeclareOk declareQueuePassive(final Queue queue) {
		return  this.rabbitTemplate.execute(new ChannelCallback<DeclareOk>() {
			public DeclareOk doInRabbit(Channel channel) throws Exception {
				return channel.queueDeclarePassive(queue.getName());
			}
		});
	}
	@RequestMapping(value = "/cleanRedis", method = RequestMethod.GET)
	@ResponseBody
	public String cleanRedis(Locale locale, Model model) {

	//	redisHandler.cleanRedis();
		logger.warn("Redis is Clean");
		model.addAttribute("serverMessage", "Redis is Clean");
		return "Redis is Clean";
	}
	
}
