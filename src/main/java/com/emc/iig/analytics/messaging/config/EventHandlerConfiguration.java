package com.emc.iig.analytics.messaging.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.emc.iig.analytics.config.AbstractConfiguration;
import com.emc.iig.analytics.config.Properties;

@Configuration
public class EventHandlerConfiguration extends AbstractConfiguration {

	
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setMessageConverter(jsonMessageConverter());
		template.setRoutingKey(Properties.APPLICATION_EVENTS);	
		return template;
	}
/*	
	@Bean
	public SimpleMessageListenerContainer workerListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(Properties.APPLICATION_EVENTS);
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new ApplicationEventHandler());
		messageListenerAdapter.setMessageConverter(jsonMessageConverter());
		container.setMessageListener(messageListenerAdapter);	
		container.setConcurrentConsumers(1);
		return container;
	}
	
*/
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }
	
	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}
	
	@Bean
	public Queue applicationEventsQueue() {
		// This queue will be declared due to the presence of the AmqpAdmin class in the context.
		// Every queue is bound to the default direct exchange		
		return new Queue(Properties.APPLICATION_EVENTS);
	}
	
	@Bean
	public Queue logEventsQueue() {
		// This queue will be declared due to the presence of the AmqpAdmin class in the context.
		// Every queue is bound to the default direct exchange		
		return new Queue(Properties.LOG_EVENTS);
	}
	
	@Bean 
	public DirectExchange eventsExchange() {
		return new DirectExchange("analyticsExchange");
	}
	
	@Bean
	public Binding applicationEventsBinding() {
		return BindingBuilder.bind(applicationEventsQueue()).to(eventsExchange()).with(Properties.APPLICATION_EVENTS);
	}
	
	
	@Bean
	public Binding logEventsBinding() {
		return BindingBuilder.bind(logEventsQueue()).to(eventsExchange()).with(Properties.LOG_EVENTS);
	}
	
}
