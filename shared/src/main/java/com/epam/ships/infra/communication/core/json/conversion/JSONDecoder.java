package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.core.message.BaseMessage;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * @author Sandor
 * @see Decoder
 * @see BaseMessage
 * @see Message
 * <p>
 * It converts a JsonElement into a BaseMessage instance.
 * @since 2017-12-10
 */
public class JSONDecoder implements Decoder<JsonElement> {
    
    public static final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().create();
    private Target logger = new SharedLogger(JSONDecoder.class);

    /**
     * It converts a JsonElement instance into a BaseMessage
     * instance.
     *
     * @param jsonElement a JsonElement instance representing
     *                   a BaseMessage.
     * @return Message a result of conversion of JsonElement
     * into BaseMessage
     */
    @Override
    public Message decode(JsonElement jsonElement) {
        return GSON.fromJson(jsonElement, BaseMessage.class);
    }
}
