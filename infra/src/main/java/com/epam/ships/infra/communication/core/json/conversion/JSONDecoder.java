package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.core.message.BaseMessage;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;

/**
 * @author Sandor
 * @see Decoder
 * @see BaseMessage
 * @see Message
 * <p>
 * It converts a JSONObject into a BaseMessage instance.
 * @since 2017-12-10
 */
public class JSONDecoder implements Decoder<JsonElement> {

    private Target logger = new SharedLogger(JSONDecoder.class);

    /**
     * It converts a JSONObject instance into a BaseMessage
     * instance.
     *
     * @param jsonElement a JsonElement instance representing
     *                   a BaseMessage.
     * @return Message a result of conversion of JSONObject
     * into BaseMessage
     */
    @Override
    public Message decode(JsonElement jsonElement) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonElement, BaseMessage.class);
    }
}
