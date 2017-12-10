package com.epam.ships.communication.core.json.conversion;

import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.api.conversion.Encoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.ships.communication.core.message.BaseMessage;
import org.json.JSONObject;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see com.epam.ships.communication.api.conversion.Encoder
 * @see Message
 * @see BaseMessage
 *
 * It converts a BaseMessage instance into a JSONObject.
 */
public class JSONEncoder implements Encoder<JSONObject> {

    private static Logger logger = LogManager.getLogger(JSONEncoder.class);

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
