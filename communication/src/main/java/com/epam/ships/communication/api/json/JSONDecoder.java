package com.epam.ships.communication.api.json;

import com.epam.ships.communication.api.Message;
import org.json.JSONObject;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see Message
 *
 * An implementing class has to ensure that
 * a JSONObject is converted into a class
 * instance implementing Message interface.
 */
public interface JSONDecoder {
    /**
     *
     * @param jsonObject a JSONObject instance
     * @return Message an instance of a class
     * implementing Message interface.
     */
    Message decode(JSONObject jsonObject);
}
