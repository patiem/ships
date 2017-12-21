package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.core.message.BaseMessage;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * @author Sandor
 * @see Encoder
 * @see Message
 * @see BaseMessage
 * <p>
 * It converts a BaseMessage instance into a JsonElement.
 * @since 2017-12-10
 */
public class JSONEncoder implements Encoder<JsonElement> {

    private final Target logger = new SharedLogger(JSONEncoder.class);

    /**
     * It converts an instance of a class implementing
     * Message interface into a JsonElement instance.
     *
     * @param message an instance of a class implementing
     *                Message interface.
     * @return a result of conversion of an instance of a class
     * implementing Message interface into a JsonElement instance.
     */
    @Override
    public JsonElement encode(Message message) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.toJsonTree(message, BaseMessage.class);
    }
}
