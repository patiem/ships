package com.epam.ships.communication.api.json;

import com.epam.ships.communication.api.Message;
import org.json.JSONObject;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see Message
 *
 * An implementing class has to ensure that a class
 * instance implementing Message interface is converted
 * into JSONObject.
 */
public interface JSONEncoder {
    JSONObject encode(Message message);
}
