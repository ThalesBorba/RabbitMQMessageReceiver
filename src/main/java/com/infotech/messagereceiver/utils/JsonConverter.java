package com.infotech.messagereceiver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.amqp.core.Message;

import java.io.IOException;

@UtilityClass
public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(Message message, Class<T> valueType) throws IOException {
        String trimmedJson = new String(message.getBody()).replace("\\", "");
        String trimmedString = formatString(trimmedJson);
        return objectMapper.readValue(trimmedString, valueType);
    }

    public static String formatString(String input) {
        int jsonStart = input.indexOf('{');
        int jsonEnd = input.lastIndexOf('}');
        return input.substring(jsonStart, jsonEnd + 1);
    }
}


