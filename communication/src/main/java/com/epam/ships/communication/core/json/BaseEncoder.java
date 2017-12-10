package com.epam.ships.communication.core.json;

import com.epam.ships.communication.api.json.JSONEncoder;
import com.epam.ships.communication.api.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import com.epam.ships.communication.core.message.BaseMessage;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see JSONEncoder
 * @see Message
 * @see BaseMessage
 *
 * It converts a BaseMessage instance into a JSONObject.
 */
public class BaseEncoder implements JSONEncoder {

    private static Logger logger = LogManager.getLogger(BaseEncoder.class);

    @Override
    public JSONObject encode(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writeValueAsString(message);
            return new JSONObject(result);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return new JSONObject();
    }
}
