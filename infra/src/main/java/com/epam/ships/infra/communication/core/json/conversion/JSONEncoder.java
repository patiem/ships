package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.core.message.BaseMessage;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see Encoder
 * @see Message
 * @see BaseMessage
 *
 * It converts a BaseMessage instance into a JSONObject.
 */
public class JSONEncoder implements Encoder<JSONObject> {

    private final Target logger = new SharedLogger(JSONEncoder.class);

    /**
     *
     * It converts an instance of a class implementing
     * Message interface into a JSONObject instance.
     *
     * @param message an instance of a class implementing
     *                Message interface.
     * @return a result of conversion of an instance of a class
     * implementing Message interface into a JSONObject instance.
     */
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
