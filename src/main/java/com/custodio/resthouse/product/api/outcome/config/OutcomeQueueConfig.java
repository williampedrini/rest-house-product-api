package com.custodio.resthouse.product.api.outcome.config;

import com.custodio.resthouse.product.api.outcome.model.Outcome;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class OutcomeQueueConfig {
    @Value("${spring.activemq.queue.outcome:outcome.product}")
    private String queue;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(this.queue);
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        final Map<String, Class<?>> typeMapping = new HashMap<>();
        typeMapping.put("outcome", Outcome.class);
        converter.setTypeIdMappings(typeMapping);
        return converter;
    }
}