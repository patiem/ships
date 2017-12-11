package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.core.message.BaseMessage;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Sandor
 * @see Decoder
 * @see BaseMessage
 * @see Message
 *
 * It converts a JSONObject into a BaseMessage instance.
 * @since 2017-12-10
 */
public class JSONDecoder implements Decoder<JSONObject> {

    private Target logger = new SharedLogger(JSONDecoder.class);

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
