package com.infotech.messagereceiver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.infotech.messagereceiver.enums.ExchangeType;
import com.infotech.messagereceiver.exception.ErrorMessageHandler;
import com.infotech.messagereceiver.model.QueueParams;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        Dotenv dotenv = Dotenv.configure().load();
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(dotenv.get("IF_RABBITMQ_HOST"));
        connectionFactory.setPort(Integer.parseInt(dotenv.get("IF_RABBITMQ_PORT")));
        connectionFactory.setUsername(dotenv.get("IF_RABBITMQ_USERNAME"));
        connectionFactory.setPassword(dotenv.get("IF_RABBITMQ_PASSWORD"));
        connectionFactory.setVirtualHost(dotenv.get("IF_RABBITMQ_VHOST"));
        connectionFactory.setConnectionTimeout(30000);
        connectionFactory.setRequestedHeartBeat(30);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ErrorMessageHandler();
    }

    @Bean
    public RabbitListenerContainerFactory<DirectMessageListenerContainer> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setErrorHandler(errorHandler());
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public String buildMultipleQueues() {
        QueueParams params1 = QueueParams.builder()
                .queueName("q-auth-register-request")
                .exchangeName("x-auth-register-request")
                .exchangeType(ExchangeType.FANOUT)
                .deadLetterExchangeName("dlx-auth-register-request")
                .routingKey("")
                .headers(new HashMap<>())
                .build();
        QueueParams params2 = QueueParams.builder()
                .queueName("q-auth-register-response")
                .exchangeName("x-auth-register-response")
                .exchangeType(ExchangeType.FANOUT)
                .deadLetterExchangeName("dlx-auth-register-response")
                .routingKey("")
                .headers(new HashMap<>())
                .build();
        List<QueueParams> queueParamsList = new ArrayList<>(List.of(params1, params2));

        for (QueueParams queueParams : queueParamsList) {
            if (queueNotExists(queueParams.getQueueName())) {
                buildQueueSetup(queueParams.getQueueName(), queueParams.getExchangeName(), queueParams.getDeadLetterExchangeName(),
                        queueParams.getExchangeType(), queueParams.getRoutingKey(), queueParams.getHeaders());
            }
        }

        return "";
    }

    public void buildQueueSetup(String queueName, String exchangeName, String deadLetterExchangeName, ExchangeType exchangeType, String routingKey, Map<String, Object> headers) {
        Queue queue = new Queue(queueName, true);
        queue.addArgument("x-dead-letter-exchange", deadLetterExchangeName);
        amqpAdmin().declareQueue(queue);

        createDLQAndDLXAndBindings(queueName, exchangeName, exchangeType, routingKey, headers);

    }

    private void createDLQAndDLXAndBindings(String queueName, String exchangeName, ExchangeType exchangeType, String routingKey, Map<String, Object> headers) {
        Exchange exchange = switch (exchangeType) {
            case FANOUT -> new FanoutExchange(exchangeName);
            case DIRECT -> new DirectExchange(exchangeName);
            case TOPIC -> new TopicExchange(exchangeName);
            case HEADERS -> new HeadersExchange(exchangeName);
            default -> throw new IllegalArgumentException("Exchange type not supported.");
        };

        amqpAdmin().declareExchange(exchange);

        if (exchange instanceof FanoutExchange) {
            bindQueueToFanoutExchange(queueName, exchangeName);
        } else if (exchange instanceof DirectExchange) {
            bindQueueToDirectExchange(queueName, exchangeName, routingKey);
        } else if (exchange instanceof TopicExchange) {
            bindQueueToTopicExchange(queueName, exchangeName, routingKey);
        } else {
            bindQueueToHeadersExchange(queueName, exchangeName, headers);
        }
    }

    public void bindQueueToFanoutExchange(String queueName, String exchangeName) {
        amqpAdmin().declareBinding(BindingBuilder.bind(new Queue(queueName)).to(new FanoutExchange(exchangeName)));
    }

    public void bindQueueToDirectExchange(String queueName, String exchangeName, String routingKey) {
        amqpAdmin().declareBinding(BindingBuilder.bind(new Queue(queueName)).to(new DirectExchange(exchangeName)).with(routingKey));
    }

    public void bindQueueToTopicExchange(String queueName, String exchangeName, String routingKey) {
        amqpAdmin().declareBinding(BindingBuilder.bind(new Queue(queueName)).to(new TopicExchange(exchangeName)).with(routingKey));
    }

    public void bindQueueToHeadersExchange(String queueName, String exchangeName, Map<String, Object> headers) {
        amqpAdmin().declareBinding(BindingBuilder.bind(new Queue(queueName)).to(new HeadersExchange(exchangeName)).whereAll(headers).match());
    }

    private boolean queueNotExists(String queueName) {
        return amqpAdmin().getQueueProperties(queueName) == null;
    }

}