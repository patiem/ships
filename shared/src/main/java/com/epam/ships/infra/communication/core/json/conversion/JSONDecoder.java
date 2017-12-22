package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.core.message.BaseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * @author Sandor
 * @see Decoder
 * @see Message
 * @see com.epam.ships.infra.communication.api.Message
 *
 * It converts a JsonElement into a BaseMessage instance.
 * @since 2017-12-10
 */
public class JSONDecoder implements Decoder<JsonElement> {

    /**
     * It converts a JsonElement instance into a BaseMessage
     * instance.
     *
     * @param jsonElement a JsonElement instance representing
     *                   a BaseMessage.
     * @return BaseMessage a result of conversion of JsonElement
     * into BaseMessage
     */
    @Override
    public Message decode(JsonElement jsonElement) {
        Gson gson = new GsonBuilder().create();
        
        return gson.fromJson(jsonElement, BaseMessage.class);
    }
}
