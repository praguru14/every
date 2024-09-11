package com.epps.framework.application.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	public TopicExchange direct() {
		return new TopicExchange("emailExchange");
	}
	
	@Bean("emailNotificationQueueImpl")
	Queue emailNotificationQueue() {
		return new Queue("emailNotificationQueueImpl",true);
    }
	
	@Bean
	Binding emailNotificationCommunication(@Qualifier("emailNotificationQueueImpl")Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(emailNotificationQueue()).to(exchange).with("emailNotificationQueueRK");
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}
}
