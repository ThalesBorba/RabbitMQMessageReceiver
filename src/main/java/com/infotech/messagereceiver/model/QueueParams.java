package com.infotech.messagereceiver.model;


import com.infotech.messagereceiver.enums.ExchangeType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class QueueParams {

    private String queueName;
    private String exchangeName;
    private String deadLetterExchangeName;
    private ExchangeType exchangeType;
    private String routingKey;
    private Map<String, Object> headers;

    public QueueParams(String queueName, String exchangeName, String deadLetterExchangeName) {
        this(queueName, exchangeName, deadLetterExchangeName, ExchangeType.FANOUT, "", null);
    }


    public QueueParams(String queueName, String exchangeName, String deadLetterExchangeName, ExchangeType exchangeType) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.deadLetterExchangeName = deadLetterExchangeName;
        this.exchangeType = exchangeType;

    }

    public QueueParams(String queueName, String exchangeName, String deadLetterExchangeName,
                       Map<String, Object> headers) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.deadLetterExchangeName = deadLetterExchangeName;
        this.headers = headers;

    }

    public QueueParams(String queueName, String exchangeName, String deadLetterExchangeName, ExchangeType exchangeType,
                       String routingKey) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.deadLetterExchangeName = deadLetterExchangeName;
        this.exchangeType = exchangeType;
        this.routingKey = routingKey;
    }

    public QueueParams(String queueName, String exchangeName, String deadLetterExchangeName, ExchangeType exchangeType,
                       String routingKey, Map<String, Object> headers) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.deadLetterExchangeName = deadLetterExchangeName;
        this.exchangeType = exchangeType;
        this.routingKey = routingKey;
        this.headers = headers;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getDeadLetterExchangeName() {
        return deadLetterExchangeName;
    }

    public void setDeadLetterExchangeName(String deadLetterExchangeName) {
        this.deadLetterExchangeName = deadLetterExchangeName;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }
}