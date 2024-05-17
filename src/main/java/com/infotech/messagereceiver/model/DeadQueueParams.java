package com.infotech.messagereceiver.model;

import com.infotech.messagereceiver.enums.ExchangeType;
import java.util.Map;

public class DeadQueueParams {

    private String deadQueueName;
    private String exchangeName;
    private ExchangeType exchangeType;
    private String routingKey;
    private Map<String, Object> headers;

    //CONSTRUCTORS DEAD LETTERS

    public DeadQueueParams(String deadQueueName, String exchangeName, ExchangeType exchangeType) {
        this.deadQueueName = deadQueueName;
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
    }

    public DeadQueueParams(String deadQueueName, String exchangeName,
                           Map<String, Object> headers) {
        this.deadQueueName = deadQueueName;
        this.exchangeName = exchangeName;
        this.headers = headers;

    }

    public DeadQueueParams(String deadQueueName, String exchangeName, ExchangeType exchangeType,
                           String routingKey) {
        this.deadQueueName = deadQueueName;
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
        this.routingKey = routingKey;
    }

    public DeadQueueParams(String deadQueueName, String exchangeName, ExchangeType exchangeType,
                           String routingKey, Map<String, Object> headers) {
        this.deadQueueName = deadQueueName;
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
        this.routingKey = routingKey;
        this.headers = headers;
    }

    public String getDeadQueueName() {
        return deadQueueName;
    }

    public void setDeadQueueName(String deadQueueName) {
        this.deadQueueName = deadQueueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
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