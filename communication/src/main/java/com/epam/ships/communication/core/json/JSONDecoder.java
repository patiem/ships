package com.epam.ships.communication.core.json;

import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.api.conversion.Decoder;
import com.epam.ships.communication.core.message.BaseMessage;
import com.epam.ships.communication.core.message.MessageBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Sandor
 * @see com.epam.ships.communication.api.conversion.Decoder
 * @see BaseMessage
 * @see Message
 * <p>
 * It converts a JSONObject into a BaseMessage instance.
 * @since 2017-12-10
 */
public class JSONDecoder implements Decoder<JSONObject> {

    private Logger logger = LogManager.getLogger(JSONEncoder.class);

    /**
     * It converts a JSONObject instance into a BaseMessage
     * instance.
     *
     * @param jsonObject a JSONObject instance representing
     *                   a BaseMessage.
     * @return Message a result of conversion of JSONObject
     * into BaseMessage
     */
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
