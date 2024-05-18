package com.infotech.messagereceiver.consumer;

import com.infotech.messagereceiver.entities.ProductRequestEntity;
import com.infotech.messagereceiver.model.ProductRequest;
import com.infotech.messagereceiver.repositories.ProductRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.infotech.messagereceiver.utils.CustomBeanUtils.copyPropertiesExcludeNull;
import static com.infotech.messagereceiver.utils.JsonConverter.fromJson;

@Component
@RequiredArgsConstructor
public class ConsumerMessage {

    private final ProductRequestRepository repository;

    @RabbitListener(queues = "q-auth-register-request", ackMode = "AUTO", errorHandler =
            "com.infotech.messagereceiver.exception.ErrorMessageHandler")
    public void startConsumer(Message message) throws IOException {
        ProductRequest productRequest = fromJson(message, ProductRequest.class);
        ProductRequestEntity productRequestEntity = new ProductRequestEntity();
        copyPropertiesExcludeNull(productRequest, productRequestEntity);
        repository.save(productRequestEntity);
    }

}
