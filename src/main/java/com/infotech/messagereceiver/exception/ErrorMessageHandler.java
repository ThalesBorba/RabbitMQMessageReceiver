package com.infotech.messagereceiver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
public class ErrorMessageHandler implements ErrorHandler {

    /**
     * Tratamento para o caso de erro no envio, gera uma mensagem com os detalhes da exceção original e a encapsula em
     * uma exceção personalizada. Obs.: Essa exceção deve ser tratada de acordo com o seu código
     * @param t exceção/erro original
     */

    @Override
    public void handleError(Throwable t) {

        String consumerQueue = ((ListenerExecutionFailedException) t).getFailedMessage().getMessageProperties().getConsumerQueue();
        Message failedMessage = ((ListenerExecutionFailedException) t).getFailedMessage();
        String failedExchange = failedMessage.getMessageProperties().getReceivedExchange();
        String messageBody = new String(((ListenerExecutionFailedException) t).getFailedMessage().getBody());

        String message = String.format("Falha no envio da mensagem na fila %s com a exchange %s com o seguinte body %s"
                + "%n%s", consumerQueue, failedExchange, messageBody, t.getLocalizedMessage());
        throw new MessageFailedException(message);
    }
}