package com.custodio.resthouse.product.api.outcome.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.cloud.gcp.pubsub.support.converter.PubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@Configuration
class OutcomeTopicConfig {

    @Value("${spring.gcp.topic-id}")
    private String topic;

    @Bean(name = "outcomeTopicMessageHandler")
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    public MessageHandler messageSender(final PubSubTemplate pubsubTemplate) {
        final PubSubMessageConverter converter = new JacksonPubSubMessageConverter(new ObjectMapper());
        pubsubTemplate.setMessageConverter(converter);
        return new PubSubMessageHandler(pubsubTemplate, this.topic);
    }
}