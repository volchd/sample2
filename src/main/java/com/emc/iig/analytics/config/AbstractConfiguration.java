package com.emc.iig.analytics.config;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.service.keyvalue.RedisServiceCreator;
import org.cloudfoundry.runtime.service.messaging.RabbitServiceCreator;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public abstract class AbstractConfiguration {
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CloudEnvironment environment = new CloudEnvironment();
		if (environment.getInstanceInfo() != null) {
			return new RabbitServiceCreator(new CloudEnvironment()).createSingletonService().service;
		} else {
			CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
			connectionFactory.setUsername("guest");
			connectionFactory.setPassword("guest");
			return connectionFactory;
		}
	}
	
/*
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		CloudEnvironment environment = new CloudEnvironment();
		if (environment.getInstanceInfo() != null) {
			return new RedisServiceCreator(new CloudEnvironment()).createSingletonService().service;
		} else {

			return  new JedisConnectionFactory();
		}
	}
	*/
	
	
}
