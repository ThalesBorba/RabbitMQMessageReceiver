package com.infotech.messagereceiver.model;


import com.infotech.messagereceiver.enums.ExchangeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class QueueParams {

    private String queueName;
    private String exchangeName;
    private String deadLetterExchangeName;
    private ExchangeType exchangeType;
    private String routingKey;
    private Map<String, Object> headers;

}