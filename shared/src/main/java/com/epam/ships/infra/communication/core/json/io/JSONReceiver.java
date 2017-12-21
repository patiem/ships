package com.epam.ships.infra.communication.core.json.io;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.core.json.conversion.JSONDecoder;
import com.epam.ships.infra.communication.core.message.BaseMessage;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Piotr, Magda, Sandor
 * @see Receiver
 * @see JSONReceiver
 * @see Decoder
 * @see JSONDecoder
 * @see Message
 *
 * It receives a message from an input stream.
 * @since 2017-12-07
 */

public class JSONReceiver implements Receiver {

    private final InputStream inputStream;

    /**
     * @param inputStream it will read from this stream.
     */
    public JSONReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * It reads from input stream, coverts it first
     * to a JsonElement and the to a Message.
     *
     * If there is nothing to read from an input stream,
     * it returns an corresponding Message as well.
     *
     * @return Message input interpreted as a message.
     */
    @Override
    public Message receive() {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        if (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        } else {
            return new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("END")
                    .withAuthor("Auto")
                    .withStatement("End of a message")
                    .build();
        }
        Decoder<JsonElement> jsonDecoder = new JSONDecoder();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(stringBuilder.toString());
        return jsonDecoder.decode(jsonElement);
    }
}
