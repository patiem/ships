package com.epam.ships.communication.core.json;

import com.epam.ships.communication.api.json.JSONDecoder;
import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.core.message.BaseMessage;
import com.epam.ships.communication.core.message.MessageBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see JSONDecoder
 * @see BaseMessage
 * @see Message
 *
 * It converts a JSONObject into a BaseMessage instance.
 */
public class BaseDecoder implements JSONDecoder {

    Logger logger = LogManager.getLogger(BaseEncoder.class);

    @Override
    public Message decode(JSONObject jsonObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(String.valueOf(jsonObject), BaseMessage.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new MessageBuilder().build();
    }
}
