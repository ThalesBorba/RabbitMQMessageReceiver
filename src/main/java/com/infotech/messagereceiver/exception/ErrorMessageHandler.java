package com.infotech.messagereceiver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
public class ErrorMessageHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {

        String consumerQueue = ((ListenerExecutionFailedException) t).getFailedMessage().getMessageProperties().getConsumerQueue();
        Message failedMessage = ((ListenerExecutionFailedException) t).getFailedMessage();
        String failedExchange = failedMessage.getMessageProperties().getReceivedExchange();
        String messageBody = new String(((ListenerExecutionFailedException) t).getFailedMessage().getBody());

        log.error("Falha no envio da mensagem na fila {} com a exchange {} com o seguinte body {}", consumerQueue, failedExchange, messageBody);

        log.error(t.getLocalizedMessage());

    }
}